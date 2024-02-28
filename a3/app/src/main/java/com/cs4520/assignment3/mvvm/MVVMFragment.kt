package com.cs4520.assignment3.mvvm

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cs4520.assignment3.R
import com.cs4520.assignment3.common.MathOperation
import com.cs4520.assignment3.databinding.FragmentCalculatorBinding


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
        viewModel.result.observe(viewLifecycleOwner) {
            binding.resultTextView.text = getString(R.string.result, it)
        }

        viewModel.errorResId.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initEditTextListeners() {
        binding.n1EditText.addTextChangedListener {
            viewModel.setN1FromText(it.toString())
        }

        binding.n2EditText.addTextChangedListener {
            viewModel.setN2FromText(it.toString())
        }
    }

    private fun initButtonListeners() {
        // MathOperation enum is allowed in view because it's common knowledge for application
        binding.addButton.setOnClickListener {
            runCalculation(MathOperation.ADD)
        }

        binding.subtractButton.setOnClickListener {
            runCalculation(MathOperation.SUBTRACT)
        }

        binding.multiplyButton.setOnClickListener {
            runCalculation(MathOperation.MULTIPLY)
        }

        binding.divideButton.setOnClickListener {
            runCalculation(MathOperation.DIVIDE)
        }
    }

    private fun runCalculation(operation: MathOperation) {
        val ok = viewModel.calculate(operation)
        if (ok) clearInputs()
    }

    private fun clearInputs() {
        binding.n1EditText.text.clear()
        binding.n2EditText.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}