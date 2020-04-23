package com.example.habittracker.ui.cloud

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittracker.R
import com.example.habittracker.viewmodel.CloudHandlerViewModel
import kotlinx.android.synthetic.main.cloud_handler.*

class CloudHandlerFragment : Fragment() {

    private lateinit var viewModel: CloudHandlerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =  ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CloudHandlerViewModel() as T
            }
        }).get(CloudHandlerViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cloud_handler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveButton.setOnClickListener {
            viewModel.onSaveClicked()
        }

        loadButton.setOnClickListener {
            viewModel.onLoadClicked()
        }
    }
}
