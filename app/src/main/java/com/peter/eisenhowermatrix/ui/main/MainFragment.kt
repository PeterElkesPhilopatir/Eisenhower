package com.peter.eisenhowermatrix.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.peter.eisenhowermatrix.R
import com.peter.eisenhowermatrix.databinding.FragmentLoginRegisterBinding
import com.peter.eisenhowermatrix.databinding.FragmentMainBinding
import com.peter.eisenhowermatrix.ui.auth.LoginRegisterFragmentDirections
import com.peter.eisenhowermatrix.ui.auth.LoginRegisterViewModel

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        viewModel.loggedOutLiveData.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(
                    MainFragmentDirections.actionLoggedInFragmentToLoginRegisterFragment()
                )
            }
        })
        viewModel.navToAdd.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToAddTaskFragment()
                )
                viewModel.onAddClickedCompleted()
            }
        })
        viewModel.selectedType.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToTasksTypeFragment(it)
                )
                viewModel.onTypeNavigationCompleted()
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_menu -> viewModel.logOut()
        }
        return true
    }
}