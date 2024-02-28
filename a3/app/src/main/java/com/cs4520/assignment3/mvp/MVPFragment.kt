package com.cs4520.assignment3.mvp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cs4520.assignment3.R
import com.cs4520.assignment3.common.ErrorType
import com.cs4520.assignment3.databinding.FragmentCalculatorBinding


class MVPFragment : Fragment(), CalculatorView {
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    private var _presenter: CalculatorPresenter? = null
    private val presenter get() = _presenter!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        _presenter = DefaultCalculatorPresenter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calculatorLayout.setBackgroundColor(Color.parseColor("#C4B8E1"))

        initButtonListeners()
    }

    private fun initButtonListeners() {
        binding.addButton.setOnClickListener { presenter.onAddButtonClick() }
        binding.subtractButton.setOnClickListener { presenter.onSubtractButtonClick() }
        binding.multiplyButton.setOnClickListener { presenter.onMultiplyButtonClick() }
        binding.divideButton.setOnClickListener { presenter.onDivideButtonClick() }
    }

    private fun clearInputs() {
        binding.number1EditText.text.clear()
        binding.number2EditText.text.clear()
    }

    override fun getNumber1Text(): String {
        return binding.number1EditText.text.toString()
    }

    override fun getNumber2Text(): String {
        return binding.number2EditText.text.toString()
    }

    override fun setResult(result: String) {
        binding.resultTextView.text = getString(R.string.result, result)
        clearInputs()
    }

    override fun setError(type: ErrorType) {
        val resId = when (type) {
            ErrorType.MISSING_VALUES -> R.string.missing_values_error
            ErrorType.DIVISION_BY_ZERO -> R.string.division_by_zero_error
            else -> R.string.internal_server_error
        }
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _presenter = null
    }
}