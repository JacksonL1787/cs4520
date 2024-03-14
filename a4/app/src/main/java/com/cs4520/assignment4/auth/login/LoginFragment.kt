package com.cs4520.assignment4.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cs4520.assignment4.R
import com.cs4520.assignment4.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEditTextListeners()
        initButtonListeners()
        initObservers()
    }

    private fun initEditTextListeners() {
        binding.usernameEditText.addTextChangedListener {
            viewModel.setUsername(it.toString())
        }

        binding.passwordEditText.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.successEvent.collect { success ->
                println(success)
                if(success) {
                    clearEditTexts()
                    findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.errorEvent.collect{ errorType ->
                val resId = getErrorMessageResId(errorType)
                Toast.makeText(activity, resId, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun initButtonListeners() {
        binding.loginButton.setOnClickListener {
            viewModel.login()
        }
    }

    private fun getErrorMessageResId(errorType: LoginErrorType): Int {
        return when(errorType) {
            LoginErrorType.USERNAME_MISSING -> R.string.username_missing_error
            LoginErrorType.PASSWORD_MISSING -> R.string.password_missing_error
            LoginErrorType.INVALID_CREDENTIALS -> R.string.invalid_credentials_error
        }
    }

    private fun clearEditTexts() {
        binding.usernameEditText.text.clear()
        binding.passwordEditText.text.clear()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}