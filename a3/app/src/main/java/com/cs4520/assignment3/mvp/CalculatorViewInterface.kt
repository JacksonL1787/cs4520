package com.cs4520.assignment3.mvp

interface CalculatorViewInterface {
    fun getN1Text(): String
    fun getN2Text(): String
    fun setResult(result: String)
    fun setError(resId: Int)
}