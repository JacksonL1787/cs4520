package com.cs4520.assignment3.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs4520.assignment3.Calculator
import com.cs4520.assignment3.R
import com.cs4520.assignment3.common.DivisionByZeroException
import com.cs4520.assignment3.common.MathOperation
import com.cs4520.assignment3.common.NullNumbersException
import java.lang.Exception



class CalculatorViewModel: ViewModel() {
    private val calculator = Calculator()
    val result = MutableLiveData<String>()
    val errorResId = MutableLiveData<Int>()

    fun setN1FromText(text: String) {
        val n = text.toDoubleOrNull()
        calculator.setN1(n)
    }

    fun setN2FromText(text: String) {
        val n = text.toDoubleOrNull()
        calculator.setN2(n)
    }
    
    fun calculate(operation: MathOperation): Boolean {
        return try {
            val res = when (operation) {
                MathOperation.ADD -> calculator.add()
                MathOperation.SUBTRACT -> calculator.subtract()
                MathOperation.MULTIPLY -> calculator.multiply()
                MathOperation.DIVIDE -> calculator.divide()
            }
            result.value = res.toString()
            true
        } catch (e: Exception) {
            errorResId.value = when (e) {
                is NullNumbersException -> R.string.null_numbers_error
                is DivisionByZeroException -> R.string.division_by_zero_error
                else -> R.string.general_error
            }
            false
        }
    }

}