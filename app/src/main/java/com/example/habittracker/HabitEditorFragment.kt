package com.example.habittracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.habittracker.data.*
import kotlinx.android.synthetic.main.habit_editor.*

class HabitEditorFragment : Fragment() {
    private val prioritiesSpinnerListener = PrioritiesSpinnerListener()

    interface OnFormFilledListener {
        fun onFormFilled(habit: Habit, position: Int?, oldHabit: Habit?)
    }

    private var onFormFilledListener: OnFormFilledListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onFormFilledListener = context as? OnFormFilledListener
        if (onFormFilledListener == null) {
            throw ClassCastException("$context must implement OnFormFilledListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.habit_editor, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeSpinner()

        val oldHabit = arguments?.getSerializable(MainActivity.HABIT) as? Habit?
        val position = arguments?.getSerializable(MainActivity.POSITION) as? Int?

        if (oldHabit != null) {
            fillFormWithCurrentHabit(oldHabit)
        }

        addButton.setOnClickListener {
            if (!formIsFilled())
                return@setOnClickListener
            val habit = getHabitFromForm()
            onFormFilledListener?.onFormFilled(habit, position, oldHabit)
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
            this.context!!,
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
        typeRadioGroupLabel.error = getString(R.string.type_is_not_chosen)
        return false
    }

    private fun inputIsFilled(input: EditText): Boolean {
        val text = input.text
        if (text != null && text.isNotEmpty())
            return true
        input.error = getString(R.string.field_is_empty)
        return false
    }
}