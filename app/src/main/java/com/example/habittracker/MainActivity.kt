package com.example.habittracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.habittracker.data.BundleKeys
import com.example.habittracker.data.Habit

class MainActivity :
    AppCompatActivity(),
    HabitsViewFragment.OnAddClickedListener,
    HabitsViewFragment.OnItemClickedListener,
    HabitsViewFragment.HabitRepository,
    HabitEditorFragment.OnFormFilledListener {

    private var navController: NavController? = null
    private var habits = arrayListOf<Habit>()

    override fun getHabits(): ArrayList<Habit> = habits

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onAddClicked() {
        navController?.navigate(R.id.habitEditorFragment)
    }

    override fun onItemClicked(habit: Habit, position: Int) {
        val bundle = Bundle()
        bundle.putInt(BundleKeys.POSITION, position)
        bundle.putSerializable(BundleKeys.HABIT, habit)
        navController?.navigate(R.id.habitEditorFragment, bundle)
    }

    override fun onFormFilled(habit: Habit, position: Int?) {
        val bundle = Bundle()
        bundle.putBoolean(BundleKeys.HAS_POSITION, position != null)
        if (position != null)
            bundle.putInt(BundleKeys.POSITION, position)
        bundle.putSerializable(BundleKeys.HABIT, habit)
        navController?.navigate(R.id.habitsViewFragment, bundle)
    }
}