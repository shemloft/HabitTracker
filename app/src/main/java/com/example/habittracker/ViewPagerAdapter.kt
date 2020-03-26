package com.example.habittracker

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habittracker.data.HabitType

class ViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment)  {
    override fun getItemCount(): Int = HabitType.values().size

    override fun createFragment(position: Int): Fragment {
        val habitType = HabitType.values()[position]
        return RecyclerViewFragment.newInstance(habitType)
    }

}