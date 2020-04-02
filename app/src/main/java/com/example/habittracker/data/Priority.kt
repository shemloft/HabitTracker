package com.example.habittracker.data

import com.example.habittracker.R

enum class Priority(val iconResource: Int, val visibleValue: String) {
    High(R.drawable.ic_priority_high, "высокий"),
    Medium(R.drawable.ic_priority_medium, "средний"),
    Low(R.drawable.ic_priority_low, "низкий")
}