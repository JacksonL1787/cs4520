package com.cs4520.assignment3.mvp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cs4520.assignment3.R
import com.cs4520.assignment3.databinding.FragmentCalculatorBinding


class MVPFragment : Fragment(), CalculatorViewInterface {
    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    private var _presenter: CalculatorPresenterInterface? = null
    private val presenter get() = _presenter!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        _presenter = CalculatorPresenter(this)
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
        binding.n1EditText.text.clear()
        binding.n2EditText.text.clear()
    }

    override fun getN1Text(): String {
        return binding.n1EditText.text.toString()
    }

    override fun getN2Text(): String {
        return binding.n2EditText.text.toString()
    }

    override fun setResult(result: String) {
        binding.resultTextView.text = getString(R.string.result, result)
        clearInputs()
    }

    override fun setError(resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _presenter = null
    }
}