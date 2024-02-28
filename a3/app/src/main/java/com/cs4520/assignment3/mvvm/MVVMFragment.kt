package com.cs4520.assignment3.mvvm

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.cs4520.assignment3.R
import com.cs4520.assignment3.common.ErrorType
import com.cs4520.assignment3.databinding.FragmentCalculatorBinding
import kotlinx.coroutines.launch


class MVVMFragment : Fragment() {
    private val viewModel: CalculatorViewModel by viewModels()
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calculatorLayout.setBackgroundColor(Color.parseColor("#ffb3ba"))
        initObservers()
        initEditTextListeners()
        initButtonListeners()
    }

    private fun initObservers() {
        viewModel.result.observe(viewLifecycleOwner) { res ->
            binding.resultTextView.text = getString(R.string.result, res)
        }

        lifecycleScope.launch {
            viewModel.errorType.collect { type ->
                val resId = getErrorTypeResId(type)
                Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getErrorTypeResId(type: ErrorType): Int {
        return when (type) {
            ErrorType.MISSING_VALUES -> R.string.missing_values_error
            ErrorType.DIVISION_BY_ZERO -> R.string.division_by_zero_error
            else -> R.string.internal_server_error
        }
    }

    private fun initEditTextListeners() {
        binding.number1EditText.addTextChangedListener {
            viewModel.setNumber1FromText(it.toString())
        }

        binding.number2EditText.addTextChangedListener {
            viewModel.setNumber2FromText(it.toString())
        }
    }

    private fun initButtonListeners() {
        val buttons: List<Pair<Button, () -> Boolean>> = listOf(
            binding.addButton to viewModel::add,
            binding.subtractButton to viewModel::subtract,
            binding.multiplyButton to viewModel::multiply,
            binding.divideButton to viewModel::divide
        )

        buttons.forEach { (button, operation) ->
            button.setOnClickListener {
                val ok = operation()
                if (ok) clearInputs()
            }
        }
    }

    private fun clearInputs() {
        binding.number1EditText.text.clear()
        binding.number2EditText.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}