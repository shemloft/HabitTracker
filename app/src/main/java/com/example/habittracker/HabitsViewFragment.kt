package com.example.habittracker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.data.BundleKeys
import com.example.habittracker.data.Habit
import kotlinx.android.synthetic.main.habits_view.*

class HabitsViewFragment : Fragment() {
    private lateinit var habitsRecyclerViewAdapter: RecyclerView.Adapter<*>
    private lateinit var habitsRecyclerViewLayoutManager: RecyclerView.LayoutManager

    private var onAddClickedListener: OnAddClickedListener? = null
    private var onItemClickedListener: OnItemClickedListener? = null
    private var habits = arrayListOf<Habit>()

    interface OnAddClickedListener {
        fun onAddClicked()
    }

    interface OnItemClickedListener {
        fun onItemClicked(habit: Habit, position: Int)
    }

    interface HabitRepository {
        fun getHabits(): ArrayList<Habit>
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onAddClickedListener = context as? OnAddClickedListener
        if (onAddClickedListener == null) {
            throw ClassCastException("$context must implement OnAddClickedListener")
        }
        onItemClickedListener = context as? OnItemClickedListener
        if (onItemClickedListener == null) {
            throw ClassCastException("$context must implement OnItemClickedListener")
        }
        val habitRepository = context as? HabitRepository
            ?: throw ClassCastException("$context must implement HabitRepository")
        habits = habitRepository.getHabits()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.habits_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeHabitsRecyclerViewAdapter()
        processChangedPosition()
        floatingActionButton.setOnClickListener {
            onAddClickedListener?.onAddClicked()
        }
    }

    private fun processChangedPosition() {
        val insertPosition = arguments?.getInt(BundleKeys.CHANGED_POSITION)
        if (insertPosition != null) {
            habitsRecyclerViewAdapter.notifyItemChanged(insertPosition)
        }
        arguments = null
    }

    private fun initializeHabitsRecyclerViewAdapter() {
        habitsRecyclerViewAdapter = HabitsRecyclerViewAdapter(habits) { habit, position ->
            onItemClickedListener?.onItemClicked(habit, position)
        }
        habitsRecyclerViewLayoutManager = LinearLayoutManager(this.context)

        habitsRecyclerView.adapter = habitsRecyclerViewAdapter

        habitsRecyclerView.apply {
            adapter = habitsRecyclerViewAdapter
            layoutManager = habitsRecyclerViewLayoutManager
        }
    }
}