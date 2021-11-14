package com.peter.eisenhowermatrix.ui.tasks_by_type

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.peter.eisenhowermatrix.R
import com.peter.eisenhowermatrix.databinding.FragmentTaskProfileBinding
import com.peter.eisenhowermatrix.databinding.FragmentTasksTypeBinding
import com.peter.eisenhowermatrix.ui.add_task.AddTaskFragmentDirections
import com.peter.eisenhowermatrix.ui.task_profile.TaskProfileFragmentArgs
import com.peter.eisenhowermatrix.ui.task_profile.TaskProfileViewModel
import com.peter.eisenhowermatrix.ui.task_profile.TaskProfileViewModelFactory


class TasksTypeFragment : Fragment() {
    lateinit var binding: FragmentTasksTypeBinding
    lateinit var viewModel: TasksTypeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTasksTypeBinding.inflate(inflater)
        val type = TasksTypeFragmentArgs.fromBundle(requireArguments()).type
        val viewModelFactory =
            TasksTypeViewModelFactory(type, requireNotNull(activity).application)
        viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(TasksTypeViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        binding.recyclerView.adapter = TasksAdapter(OnClickListener {
            findNavController().navigate(
                TasksTypeFragmentDirections.actionTasksTypeFragmentToTaskProfileFragment(it)
            )
        })
        return binding.root
    }

}