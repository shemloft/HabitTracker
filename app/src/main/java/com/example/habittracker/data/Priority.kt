package com.example.habittracker.data

import com.example.habittracker.R

enum class Priority(val iconResource: Int, val stringId: Int) {
    High(R.drawable.ic_priority_high, R.string.high_priority),
    Medium(R.drawable.ic_priority_medium, R.string.medium_priority),
    Low(R.drawable.ic_priority_low, R.string.low_priority)
}