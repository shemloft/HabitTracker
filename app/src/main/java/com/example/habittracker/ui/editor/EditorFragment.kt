package com.example.habittracker.ui.editor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.MainActivity
import com.example.habittracker.R
import com.example.habittracker.data.Habit
import com.example.habittracker.data.HabitType
import com.example.habittracker.data.Priority
import com.example.habittracker.viewmodel.EditorViewModel
import kotlinx.android.synthetic.main.habit_editor.*

class EditorFragment : Fragment() {
    private val prioritiesSpinnerListener =
        PrioritiesSpinnerListener()

    private lateinit var viewModel: EditorViewModel

    interface OnFormFilledListener {
        fun onFormFilled()
    }

    private lateinit var onFormFilledListener: OnFormFilledListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val contextAsOnFormFilledListener = context as? OnFormFilledListener
            ?: throw ClassCastException("$context must implement OnFormFilledListener")
        onFormFilledListener = contextAsOnFormFilledListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =  ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return EditorViewModel() as T
            }
        }).get(EditorViewModel::class.java)
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

        if (oldHabit != null) {
            fillFormWithCurrentHabit(oldHabit)
        }

        addButton.setOnClickListener {
            if (!formIsFilled())
                return@setOnClickListener
            val habit = getHabitFromForm()
            viewModel.onFormFilled(habit, oldHabit)
            onFormFilledListener.onFormFilled()
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
        prioritiesSpinner.setSelection(habit.priority.ordinal)
        typeRadioGroup.check(habit.habitType.buttonId)
    }

    private fun getHabitFromForm() = Habit(
        name = nameTextField.text.toString(),
        description = descriptionTextField.text.toString(),
        habitCount = habitCountTextField.text.toString().toInt(),
        habitFrequency = habitFrequencyTextField.text.toString().toInt(),
        priority = Priority.values()[prioritiesSpinnerListener.currentPosition],
        habitType = HabitType.values()
            .first { habitType -> typeRadioGroup.checkedRadioButtonId == habitType.buttonId })

    private fun initializeSpinner() {
        ArrayAdapter(
            this.context as Context,
            android.R.layout.simple_spinner_item,
            Priority.values().map { priority -> getString(priority.stringId) }
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