package com.example.habittracker.ui.habits

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.R
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType
import com.example.habittracker.model.Model
import com.example.habittracker.viewmodel.EditorViewModel
import com.example.habittracker.viewmodel.HabitsViewModel
import kotlinx.android.synthetic.main.recycler_view.*

class RecyclerViewFragment() : Fragment() {
    private lateinit var habitsRecyclerViewAdapter: HabitsRecyclerViewAdapter
    private lateinit var habitsRecyclerViewLayoutManager: RecyclerView.LayoutManager
    private lateinit var onItemClickedListener: OnItemClickedListener

    private lateinit var viewModel: HabitsViewModel

    private var habits = listOf<Habit>()

    interface OnItemClickedListener {
        fun onItemClicked(habit: Habit, position: Int)
    }

    companion object {
        const val HABIT_TYPE = "habitType"

        fun newInstance(habitType: HabitType): RecyclerViewFragment {
            val bundle = Bundle()
            bundle.putSerializable(HABIT_TYPE, habitType)
            return RecyclerViewFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HabitsViewModel() as T
            }
        }).get(HabitsViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val contextAsOnItemClickedListener = context as? OnItemClickedListener
            ?: throw ClassCastException("$context must implement OnItemClickedListener")
        onItemClickedListener = contextAsOnItemClickedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.recycler_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val habitType = arguments?.getSerializable(HABIT_TYPE) as? HabitType
            ?: throw IllegalArgumentException("Should have habitType argument")
        viewModel.onHabitTypeChanged(habitType)
        viewModel.habits.observe(
            viewLifecycleOwner,
            Observer { habits -> habitsRecyclerViewAdapter.updateHabits(habits) })
        initializeHabitsRecyclerViewAdapter()
    }


    private fun initializeHabitsRecyclerViewAdapter() {
        habitsRecyclerViewAdapter =
            HabitsRecyclerViewAdapter(habits) { habit, position ->
                onItemClickedListener.onItemClicked(habit, position)
            }
        habitsRecyclerViewLayoutManager = LinearLayoutManager(this.context)

        habitsRecyclerView.adapter = habitsRecyclerViewAdapter

        habitsRecyclerView.apply {
            adapter = habitsRecyclerViewAdapter
            layoutManager = habitsRecyclerViewLayoutManager
        }
    }

}