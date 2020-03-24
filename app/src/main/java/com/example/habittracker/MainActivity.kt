package com.example.habittracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.data.Constants
import com.example.habittracker.data.Habit
import kotlinx.android.synthetic.main.activity_main.*

const val ADD_HABIT_REQUEST = 1234
const val EDIT_HABIT_REQUEST = 12345

class MainActivity : AppCompatActivity() {

    private var habits = arrayListOf<Habit>()
    private lateinit var habitsRecyclerViewAdapter: RecyclerView.Adapter<*>
    private lateinit var habitsRecyclerViewLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeHabitsRecyclerViewAdapter()

        floatingActionButton.setOnClickListener {
            val i = Intent(this, HabitEditorActivity::class.java)
            startActivityForResult(i, ADD_HABIT_REQUEST)
        }
    }

    fun initializeHabitsRecyclerViewAdapter(){
        habitsRecyclerViewAdapter = HabitsRecyclerViewAdapter(habits) { habit, position ->
            val i = Intent(this, HabitEditorActivity::class.java)
            i.putExtra(Constants.OLD_HABIT, habit)
            i.putExtra(Constants.POSITION, position)
            startActivityForResult(i, EDIT_HABIT_REQUEST)
        }
        habitsRecyclerViewLayoutManager = LinearLayoutManager(this)

        habitsRecyclerView.adapter = habitsRecyclerViewAdapter

        habitsRecyclerView.apply {
            adapter = habitsRecyclerViewAdapter
            layoutManager = habitsRecyclerViewLayoutManager
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK)
            return
        if (requestCode == ADD_HABIT_REQUEST) {
            val habit = data?.getSerializableExtra(Constants.NEW_HABIT) as Habit
            habits.add(habit)
            habitsRecyclerViewAdapter.notifyItemInserted(habits.size - 1)
        }
        if (requestCode == EDIT_HABIT_REQUEST) {
            val habit = data?.getSerializableExtra(Constants.EDITED_HABIT) as Habit
            val position = data.getIntExtra(Constants.POSITION, -1)
            habits[position] = habit
            habitsRecyclerViewAdapter.notifyItemChanged(position)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(Constants.HABITS_TO_SERIALIZE, habits)
        Log.e("kekeke", "Saved: $habits")
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        habits =
            savedInstanceState.getSerializable(Constants.HABITS_TO_SERIALIZE) as ArrayList<Habit>
        initializeHabitsRecyclerViewAdapter()
        super.onRestoreInstanceState(savedInstanceState)
    }
}
