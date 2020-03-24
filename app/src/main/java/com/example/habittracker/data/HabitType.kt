package com.example.habittracker.data

import com.example.habittracker.R

enum class HabitType {
    Good {
        override fun iconResource() = R.drawable.ic_good_habit
        override fun buttonId() = R.id.typeGoodRadioButton
    },
    Bad {
        override fun iconResource() = R.drawable.ic_bad_habit
        override fun buttonId() = R.id.typeBadRadioButton
    };

    abstract fun iconResource(): Int
    abstract fun buttonId(): Int
}
