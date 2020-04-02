package com.example.habittracker.data

import com.example.habittracker.R

enum class HabitType(val iconResource: Int, val buttonId: Int, val stringId: Int) {
    Good(R.drawable.ic_good_habit, R.id.typeGoodRadioButton, R.string.goods),
    Bad(R.drawable.ic_bad_habit, R.id.typeBadRadioButton, R.string.bads);
}
