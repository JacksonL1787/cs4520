package com.cs4520.assignment3.mvp

import com.cs4520.assignment3.common.ErrorType
import com.cs4520.assignment3.lib.Calculator
import com.cs4520.assignment3.lib.DivisionByZeroException
import com.cs4520.assignment3.lib.NullNumbersException

class DefaultCalculatorPresenter(private val view: CalculatorView) :
    CalculatorPresenter {
    private val calculator = Calculator()

    override fun onAddButtonClick() {
        runCalculation(calculator::add)
    }

    override fun onSubtractButtonClick() {
        runCalculation(calculator::subtract)
    }

    override fun onMultiplyButtonClick() {
        runCalculation(calculator::multiply)
    }

    override fun onDivideButtonClick() {
        runCalculation(calculator::divide)
    }

    private fun runCalculation(operation: () -> Double) {
        // Set numbers in calculator model. Will always override previous numbers
        // whenever a calculation is made.
        setNumbersInCalculator()

        try {
            val res = operation()
            view.setResult(res.toString())
        } catch (e: Exception) {
            val errorCode = when (e) {
                is NullNumbersException -> ErrorType.MISSING_VALUES
                is DivisionByZeroException -> ErrorType.DIVISION_BY_ZERO
                else -> ErrorType.INTERNAL_SERVER
            }

            view.setError(errorCode)
        }
    }

    private fun setNumbersInCalculator() {
        calculator.setNumber1(view.getNumber1Text().toDoubleOrNull())
        calculator.setNumber2(view.getNumber2Text().toDoubleOrNull())
    }
}