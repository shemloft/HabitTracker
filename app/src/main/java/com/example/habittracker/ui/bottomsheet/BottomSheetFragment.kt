package com.example.habittracker.ui.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.habittracker.R
import com.example.habittracker.data.SortStatus
import com.example.habittracker.viewmodel.HabitsViewModel
import kotlinx.android.synthetic.main.bottom_sheet.*

class BottomSheetFragment : Fragment() {
    private val viewModel: HabitsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonToggleGroup.addOnButtonCheckedListener { group, _, _ ->
            val sortStatus = when (group.checkedButtonId) {
                View.NO_ID -> SortStatus.NONE
                R.id.buttonSortUp -> SortStatus.UP
                R.id.buttonSortDown -> SortStatus.DOWN
                else -> SortStatus.NONE
            }
            viewModel.onSortStatusChange(sortStatus)
        }

        searchTextField.addTextChangedListener {
            viewModel.onTextChanged(it?.toString() ?: "")
        }
    }
}