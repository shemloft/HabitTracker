package com.example.habittracker

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
import androidx.room.Room
import com.example.habittracker.cloud.CloudRepository
import com.example.habittracker.data.Habit
import com.example.habittracker.db.HabitsDatabase
import com.example.habittracker.infrastructure.hideKeyboard
import com.example.habittracker.model.Model
import com.example.habittracker.ui.editor.EditorFragment
import com.example.habittracker.ui.habits.HabitsViewFragment
import com.example.habittracker.ui.habits.RecyclerViewFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :
    AppCompatActivity(),
    HabitsViewFragment.OnAddClickedListener,
    RecyclerViewFragment.OnItemClickedListener,
    EditorFragment.OnFormFilledListener {

    companion object {
        const val HABIT = "habit"
    }

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.habitsViewFragment, R.id.cloudFragment, R.id.appInfoFragment),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (savedInstanceState == null) {
            val db =
                Room.databaseBuilder(
                    applicationContext,
                    HabitsDatabase::class.java,
                    "HabitsDatabase"
                )
                    .allowMainThreadQueries()
                    .build()
            Model.addDatabase(db)
        }

        CloudRepository.addContext(this)
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

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        navController.navigate(R.id.habitsViewFragment)
    }

    override fun onAddClicked() {
        navController.navigate(R.id.habitEditorFragment)
    }

    override fun onItemClicked(habit: Habit) {
        val bundle = Bundle()
        bundle.putSerializable(HABIT, habit)
        navController.navigate(R.id.habitEditorFragment, bundle)
    }

    override fun onFormFilled() {
        this.hideKeyboard()
        val options = NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
        navController.navigate(R.id.habitsViewFragment, null, options)
    }
}