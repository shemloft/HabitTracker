package com.example.habittracker.data

import com.example.habittracker.R

enum class Priority {
    High {
        override fun iconResource() = R.drawable.ic_priority_high
        override fun visibleValue() = "высокий"
    },
    Medium {
        override fun iconResource() = R.drawable.ic_priority_medium
        override fun visibleValue() = "средний"
    },
    Low {
        override fun iconResource() = R.drawable.ic_priority_low
        override fun visibleValue() = "низкий"
    };

    abstract fun iconResource(): Int
    abstract fun visibleValue(): String
}