package com.cs4520.assignment3.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs4520.assignment3.common.Operation
import java.lang.Exception



class CalculatorViewModel: ViewModel() {
    private val calculator = CalculatorMVVM()
    val result = MutableLiveData<String>()
    val error = MutableLiveData<String>()

    fun setN1(input: String) {
        val n = input.toDoubleOrNull()
        println(n)
        calculator.setN1(n)
    }

    fun setN2(input: String) {
        val n = input.toDoubleOrNull()
        println(n)
        calculator.setN2(n)
    }

    fun calculate(operation: Operation): Boolean {
        return try {
            val res = when (operation) {
                Operation.ADD -> calculator.add()
                Operation.SUBTRACT -> calculator.subtract()
                Operation.MULTIPLY -> calculator.multiply()
                Operation.DIVIDE -> calculator.divide()
            }
            result.value = res.toString()
            true
        } catch (e: Exception) {
            error.value = e.message
            false
        }
    }

}