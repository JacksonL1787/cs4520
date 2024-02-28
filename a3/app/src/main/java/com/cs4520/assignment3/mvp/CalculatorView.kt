package com.cs4520.assignment3.mvp

import com.cs4520.assignment3.common.ErrorType

interface CalculatorView {
    fun getNumber1Text(): String
    fun getNumber2Text(): String
    fun setResult(result: String)
    fun setError(type: ErrorType)
}