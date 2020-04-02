package com.example.habittracker.ui.editor

import android.view.View
import android.widget.AdapterView

class PrioritiesSpinnerListener : AdapterView.OnItemSelectedListener {
    var currentPosition = 0

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        currentPosition = position
    }
}