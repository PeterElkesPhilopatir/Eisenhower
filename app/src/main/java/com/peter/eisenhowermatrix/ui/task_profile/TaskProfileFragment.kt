package com.peter.eisenhowermatrix.ui.task_profile

import android.content.ActivityNotFoundException
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.peter.eisenhowermatrix.R
import com.peter.eisenhowermatrix.databinding.FragmentTaskProfileBinding
import com.peter.eisenhowermatrix.pojo.Task
import com.peter.eisenhowermatrix.pojo.TaskType
import com.peter.eisenhowermatrix.ui.main.MainViewModel


class TaskProfileFragment : Fragment() {
    lateinit var binding: FragmentTaskProfileBinding
    lateinit var viewModel: TaskProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentTaskProfileBinding.inflate(inflater)
        val task = TaskProfileFragmentArgs.fromBundle(requireArguments()).task

        val viewModelFactory =
            TaskProfileViewModelFactory(task, requireNotNull(activity).application)
        viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(TaskProfileViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        binding.edtTitle.setText(task.title)
        binding.edtDesc.setText(task.description)

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

        viewModel.back.observe(viewLifecycleOwner,{
            if (it)
            {
                requireFragmentManager().popBackStack()
                viewModel.onDeleteClickedCompleted()
            }
        })

        viewModel.share.observe(viewLifecycleOwner,{
            if (it!=null) {
                onShare(it)
            }
        })
        viewModel.isDone.observe(viewLifecycleOwner, Observer {
            if(it)
                binding.btnFinish.visibility = View.GONE
            if (!it)
                binding.btnFinish.visibility = View.VISIBLE
        })
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.save_menu -> viewModel.onSaveClicked()
                R.id.delete_menu -> viewModel.onDeleteClicked()
                R.id.share_menu -> viewModel.onShareClicked()
            }
        return true
    }

    private fun onShare(message : String) {
        val shareIntent = activity?.let {
            ShareCompat.IntentBuilder.from(it)
                .setText(message)
                .setType("text/plain")
                .intent
        }
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                context, getString(R.string.sharing_not_available),
                Toast.LENGTH_SHORT
            ).show()
        }
        viewModel.onShareClickedCompleted()
    }

}