package com.example.habittracker

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.habittracker.data.BundleKeys
import com.example.habittracker.data.Habit
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :
    AppCompatActivity(),
    HabitsViewFragment.OnAddClickedListener,
    HabitsViewFragment.OnItemClickedListener,
    HabitsViewFragment.HabitRepository,
    HabitEditorFragment.OnFormFilledListener,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController
    private var habits = arrayListOf<Habit>()

    override fun getHabits(): ArrayList<Habit> = habits

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, drawerLayout)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(BundleKeys.HABITS_TO_SAVE, habits)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedHabits =
            savedInstanceState.getSerializable(BundleKeys.HABITS_TO_SAVE) as? ArrayList<Habit>
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
        bundle.putInt(BundleKeys.POSITION, position)
        bundle.putSerializable(BundleKeys.HABIT, habit)
        navController.navigate(R.id.habitEditorFragment, bundle)
    }

    override fun onFormFilled(habit: Habit, position: Int?) {
        Utils.hideKeyboard(this)

        if (position == null)
            habits.add(habit)
        else
            habits[position] = habit
        val actualPosition = position ?: habits.size - 1
        val bundle = Bundle()
        bundle.putInt(BundleKeys.CHANGED_POSITION, actualPosition)
        navController.navigate(R.id.habitsViewFragment, bundle)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                val options = NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
                navController.navigate(R.id.habitsViewFragment, null, options)
            }

            R.id.nav_info -> {
                if (isValidDestination(R.id.appInfoFragment))
                    navController.navigate(R.id.appInfoFragment)
            }
        }
        tryToCloseDrawer()
        return true
    }

    private fun isValidDestination(destination: Int): Boolean {
        return destination != navController.currentDestination?.id
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                tryToCloseDrawer()
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    private fun tryToCloseDrawer(): Boolean {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
            return true
        }
        return false
    }
}