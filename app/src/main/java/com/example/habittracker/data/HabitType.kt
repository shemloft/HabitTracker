package com.example.habittracker.data

import com.example.habittracker.R

enum class HabitType {
    Good {
        override fun iconResource() = R.drawable.ic_good_habit
        override fun buttonId() = R.id.typeGoodRadioButton
        override fun stringId() = R.string.goods
    },
    Bad {
        override fun iconResource() = R.drawable.ic_bad_habit
        override fun buttonId() = R.id.typeBadRadioButton
        override fun stringId() = R.string.bads
    };

    abstract fun iconResource(): Int
    abstract fun buttonId(): Int
    abstract fun stringId(): Int
}
