package com.example.habittracker.ui.habits

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.habittracker.R
import com.example.habittracker.data.HabitType
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.habits_view.*

class HabitsViewFragment : Fragment() {

    private lateinit var onAddClickedListener: OnAddClickedListener

    interface OnAddClickedListener {
        fun onAddClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val contextAsOnAddClickedListener = context as? OnAddClickedListener
            ?: throw ClassCastException("$context must implement OnAddClickedListener")
        onAddClickedListener = contextAsOnAddClickedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.habits_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = resources.getString(HabitType.values()[position].stringId)
        }.attach()

        floatingActionButton.setOnClickListener {
            onAddClickedListener.onAddClicked()
        }
    }
}