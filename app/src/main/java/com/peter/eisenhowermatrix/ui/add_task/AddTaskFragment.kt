package com.peter.eisenhowermatrix.ui.add_task

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.peter.eisenhowermatrix.R
import com.peter.eisenhowermatrix.databinding.FragmentAddTaskBinding
import com.peter.eisenhowermatrix.pojo.Task
import com.peter.eisenhowermatrix.pojo.TaskType
import com.peter.eisenhowermatrix.ui.main.MainViewModel

class AddTaskFragment : Fragment() {

    lateinit var binding: FragmentAddTaskBinding
    lateinit var viewModel: AddTaskViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this).get(AddTaskViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.type.observe(viewLifecycleOwner, Observer {
            if (it != null)
                changeColor(it)
            else Log.e("TASK_CHANGE_COLOR", "NULL")
        })

        viewModel.message.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.onMessageDisplayCompleted()
            }
        })

        viewModel.navToTask.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                findNavController().navigate(
                    AddTaskFragmentDirections.actionAddTaskFragmentToTaskProfileFragment(it)
                )
                viewModel.onNavToTaskCompleted()
            }
        })
        return binding.root
    }

    private fun changeColor(data: TaskType) {
        when (data) {
            TaskType.IMPORTANT_URGENT -> {
                Log.i("TASK", "CHANGED")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.cards.setBackgroundColor(requireContext().getColor(R.color.type1))
                }
            }

            TaskType.IMPORTANT_NOT_URGENT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.cards.setBackgroundColor(requireContext().getColor(R.color.type2))
                }
            }

            TaskType.NOT_IMPORTANT_URGENT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.cards.setBackgroundColor(requireContext().getColor(R.color.type3))
                }
            }

            TaskType.NOT_IMPORTANT_NOT_URGENT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.cards.setBackgroundColor(requireContext().getColor(R.color.type4))
                }
            }


            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.cards.setBackgroundColor(requireContext().getColor(R.color.type_undefined))
                }
            }
        }


    }

}