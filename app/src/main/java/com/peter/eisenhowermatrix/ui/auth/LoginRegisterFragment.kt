package com.peter.eisenhowermatrix.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Button

import androidx.annotation.NonNull

import com.google.firebase.auth.FirebaseUser

import android.widget.EditText
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.peter.eisenhowermatrix.R
import com.peter.eisenhowermatrix.databinding.FragmentLoginRegisterBinding
import java.util.*


class LoginRegisterFragment : Fragment() {

    lateinit var binding: FragmentLoginRegisterBinding
    private lateinit var viewModel: LoginRegisterViewModel

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel::class.java)

        viewModel.userLiveData.observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                findNavController().navigate(
                    LoginRegisterFragmentDirections.actionLoginRegisterFragmentToLoggedInFragment()
                )
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginRegisterBinding.inflate(inflater)
        viewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel::class.java)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }
}