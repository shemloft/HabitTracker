package com.example.habittracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType
import com.example.habittracker.model.Model
import kotlinx.android.synthetic.main.recycler_view.*

class RecyclerViewFragment() : Fragment() {
    private lateinit var habitsRecyclerViewAdapter: RecyclerView.Adapter<*>
    private lateinit var habitsRecyclerViewLayoutManager: RecyclerView.LayoutManager
    private lateinit var onItemClickedListener: OnItemClickedListener

    private var habits = arrayListOf<Habit>()

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
        habits = ArrayList(Model.getHabits(habitType))
        initializeHabitsRecyclerViewAdapter()
    }

    private fun initializeHabitsRecyclerViewAdapter() {
        habitsRecyclerViewAdapter = HabitsRecyclerViewAdapter(habits) { habit, position ->
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