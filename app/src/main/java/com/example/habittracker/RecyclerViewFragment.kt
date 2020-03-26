package com.example.habittracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.data.BundleKeys
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType
import kotlinx.android.synthetic.main.recycler_view.*

class RecyclerViewFragment() : Fragment() {
    private lateinit var habitsRecyclerViewAdapter: RecyclerView.Adapter<*>
    private lateinit var habitsRecyclerViewLayoutManager: RecyclerView.LayoutManager
    private lateinit var onItemClickedListener: OnItemClickedListener
    private lateinit var habitRepository: HabitRepository

    private var habits = arrayListOf<Habit>()

    interface HabitRepository {
        fun getHabits(): ArrayList<Habit>
    }

    interface OnItemClickedListener {
        fun onItemClicked(habit: Habit, position: Int)
    }

    companion object {
        fun newInstance(habitType: HabitType): RecyclerViewFragment {
            val bundle = Bundle()
            bundle.putSerializable(BundleKeys.HABIT_TYPE, habitType)
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

        habitRepository = context as? HabitRepository
            ?: throw ClassCastException("$context must implement HabitRepository")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.recycler_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val habitType = arguments?.getSerializable(BundleKeys.HABIT_TYPE) as? HabitType
            ?: throw IllegalArgumentException("Should have habitType argument")
        habits = ArrayList(habitRepository.getHabits().filter { habit -> habit.habitType == habitType })
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