package com.example.habittracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.habittracker.data.Constants
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType
import com.example.habittracker.data.Priority
import kotlinx.android.synthetic.main.activity_habit_editor.*

class HabitEditorActivity : AppCompatActivity() {

    private val fieldIsEmptyErrorText = "Поле должно быть заполнено"
    private val typeIsNotChosenErrorText = "Тип должен быть выбран"
    private val prioritiesSpinnerListener = PrioritiesSpinnerListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_editor)

        initializeSpinner()

        var toEdit = false

        if (intent.hasExtra(Constants.OLD_HABIT)) {
            toEdit = true
            val oldHabit = intent.getSerializableExtra(Constants.OLD_HABIT) as Habit
            fillFormWithCurrentHabit(oldHabit)
        }

        addButton.setOnClickListener {
            if (!formIsFilled())
                return@setOnClickListener
            val i = Intent()
            val habit = getHabitFromForm()
            if (toEdit) {
                i.putExtra(Constants.EDITED_HABIT, habit)
                i.putExtra(Constants.POSITION, intent.getIntExtra(Constants.POSITION, -1))
            } else {
                i.putExtra(Constants.NEW_HABIT, habit)
            }
            setResult(Activity.RESULT_OK, i)
            finish()
        }
    }

    private fun formIsFilled(): Boolean {
        return inputIsFilled(nameTextField)
                && inputIsFilled(descriptionTextField)
                && typeIsChosen()
                && inputIsFilled(habitCountTextField)
                && inputIsFilled(habitFrequencyTextField)
    }

    private fun fillFormWithCurrentHabit(habit: Habit) {
        nameTextField.setText(habit.name)
        descriptionTextField.setText(habit.description)
        habitCountTextField.setText(habit.habitCount.toString())
        habitFrequencyTextField.setText(habit.habitFrequency.toString())
        prioritiesSpinner.setSelection(
            getIndexInSpinner(habit.priority.visibleValue())
        )
        typeRadioGroup.check(habit.habitType.buttonId())
    }

    private fun getIndexInSpinner(valueToFind: String): Int {
        val arr = resources.getStringArray(R.array.prioritiesList)
        for ((index, value) in arr.withIndex()) {
            if (value.toString() == valueToFind)
                return index
        }
        throw java.lang.IllegalArgumentException("Unknown value: $valueToFind")
    }

    private fun getHabitFromForm(): Habit {
        val name = nameTextField.text.toString()
        val description = descriptionTextField.text.toString()
        val habitCount = habitCountTextField.text.toString().toInt()
        val habitFrequency = habitFrequencyTextField.text.toString().toInt()
        val priorityVisibleValue = resources
            .getStringArray(R.array.prioritiesList)[prioritiesSpinnerListener.currentPosition]
            .toString()
        val priority = Priority.values()
            .first { priority -> priority.visibleValue() == priorityVisibleValue }
        val habitType = HabitType.values()
            .first { habitType -> typeRadioGroup.checkedRadioButtonId == habitType.buttonId() }

        return Habit(
            name,
            description,
            priority,
            habitType,
            habitCount,
            habitFrequency
        )
    }

    private fun initializeSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.prioritiesList,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            prioritiesSpinner.adapter = adapter
        }

        prioritiesSpinner.onItemSelectedListener = prioritiesSpinnerListener
    }

    private fun typeIsChosen(): Boolean {
        val buttonId = typeRadioGroup.checkedRadioButtonId
        if (buttonId != -1) {
            typeRadioGroupLabel.error = null
            return true
        }
        typeRadioGroupLabel.error = typeIsNotChosenErrorText
        return false
    }

    private fun inputIsFilled(input: EditText): Boolean {
        val text = input.text
        if (text != null && text.isNotEmpty())
            return true
        input.error = fieldIsEmptyErrorText
        return false
    }

}