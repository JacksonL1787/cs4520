package com.cs4520.assignment3.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs4520.assignment3.common.ErrorType
import com.cs4520.assignment3.lib.Calculator
import com.cs4520.assignment3.lib.DivisionByZeroException
import com.cs4520.assignment3.lib.NullNumbersException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class CalculatorViewModel : ViewModel() {
    private val calculator = Calculator()

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> = _result

    private val _errorType = MutableSharedFlow<ErrorType>(extraBufferCapacity = 1)
    val errorType = _errorType.asSharedFlow()

    fun setNumber1FromText(text: String) {
        val n = text.toDoubleOrNull()
        calculator.setNumber1(n)
    }

    fun setNumber2FromText(text: String) {
        val n = text.toDoubleOrNull()
        calculator.setNumber2(n)
    }

    fun add(): Boolean {
        return runCalculation(calculator::add)
    }

    fun subtract(): Boolean {
        return runCalculation(calculator::subtract)
    }

    fun multiply(): Boolean {
        return runCalculation(calculator::multiply)
    }

    fun divide(): Boolean {
        return runCalculation(calculator::divide)
    }

    private fun runCalculation(operation: () -> Double): Boolean {
        return try {
            _result.value = operation().toString()
            true
        } catch (e: Exception) {
            val type = when (e) {
                is NullNumbersException -> ErrorType.MISSING_VALUES
                is DivisionByZeroException -> ErrorType.DIVISION_BY_ZERO
                else -> ErrorType.INTERNAL_SERVER
            }
            _errorType.tryEmit(type)
            false
        }
    }
}