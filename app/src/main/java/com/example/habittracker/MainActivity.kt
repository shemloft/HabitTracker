package com.example.habittracker

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.habittracker.data.Habit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :
    AppCompatActivity(),
    HabitsViewFragment.OnAddClickedListener,
    RecyclerViewFragment.OnItemClickedListener,
    RecyclerViewFragment.HabitRepository,
    HabitEditorFragment.OnFormFilledListener {

    companion object {
        const val HABITS_TO_SAVE = "habitsToSave"
        const val POSITION = "position"
        const val HABIT = "habit"
    }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var habits = arrayListOf<Habit>()

    override fun getHabits(): ArrayList<Habit> = habits

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.habitsViewFragment, R.id.appInfoFragment),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(HABITS_TO_SAVE, habits)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedHabits =
            savedInstanceState.getSerializable(HABITS_TO_SAVE) as? ArrayList<Habit>
        if (savedHabits != null) {
            habits = savedHabits
            navController.navigate(R.id.habitsViewFragment)
        }
    }

    override fun onAddClicked() {
        navController.navigate(R.id.habitEditorFragment)
    }

    override fun onItemClicked(habit: Habit, position: Int) {
        val bundle = Bundle()
        bundle.putInt(POSITION, position)
        bundle.putSerializable(HABIT, habit)
        navController.navigate(R.id.habitEditorFragment, bundle)
    }

    override fun onFormFilled(habit: Habit, position: Int?, oldHabit: Habit?) {
        this.hideKeyboard()
        if (position == null)
            habits.add(habit)
        else
            habits[getPosition(position, oldHabit)] = habit
        val options = NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
        navController.navigate(R.id.habitsViewFragment, null, options)
    }

    private fun getPosition(position: Int, oldHabit: Habit?): Int {
        val indices =
            habits.mapIndexed { index, h -> if (h.habitType == oldHabit?.habitType) index else null }
                .filterNotNull()
        return indices[position]
    }
}