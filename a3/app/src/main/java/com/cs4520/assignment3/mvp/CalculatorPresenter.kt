package com.cs4520.assignment3.mvp

import com.cs4520.assignment3.Calculator
import com.cs4520.assignment3.R
import com.cs4520.assignment3.common.DivisionByZeroException
import com.cs4520.assignment3.common.MathOperation
import com.cs4520.assignment3.common.NullNumbersException

class CalculatorPresenter(private val view: CalculatorViewInterface) : CalculatorPresenterInterface {

    private val calculator = Calculator()

    override fun onAddButtonClick() {
        runCalculation(MathOperation.ADD)
    }

    override fun onSubtractButtonClick() {
        runCalculation(MathOperation.SUBTRACT)
    }

    override fun onMultiplyButtonClick() {
        runCalculation(MathOperation.MULTIPLY)
    }

    override fun onDivideButtonClick() {
        runCalculation(MathOperation.DIVIDE)
    }

    private fun runCalculation(operation: MathOperation) {
        // Set numbers in calculator model. Will always override previous
        setNumbersInCalculator()

        try {
            val res = when (operation) {
                MathOperation.ADD -> calculator.add()
                MathOperation.SUBTRACT -> calculator.subtract()
                MathOperation.MULTIPLY -> calculator.multiply()
                MathOperation.DIVIDE -> calculator.divide()
            }
            this.view.setResult(res.toString())
        } catch (e: Exception) {
            val resId = when (e) {
                is NullNumbersException -> R.string.null_numbers_error
                is DivisionByZeroException -> R.string.division_by_zero_error
                else -> R.string.general_error
            }
            this.view.setError(resId)
        }
    }

    private fun setNumbersInCalculator() {
        this.calculator.setN1(this.view.getN1Text().toDoubleOrNull())
        this.calculator.setN2(this.view.getN2Text().toDoubleOrNull())
    }

}