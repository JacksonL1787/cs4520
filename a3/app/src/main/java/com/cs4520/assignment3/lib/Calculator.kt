package com.cs4520.assignment3.lib

class Calculator {
    private var n1: Double? = null
    private var n2: Double? = null

    fun setNumber1(n: Double?) {
        n1 = n
    }

    fun setNumber2(n: Double?) {
        n2 = n
    }

    fun add(): Double {
        val (n1, n2) = validateNumbers()
        return n1 + n2
    }

    fun subtract(): Double {
        val (n1, n2) = validateNumbers()
        return n1 - n2
    }

    fun multiply(): Double {
        val (n1, n2) = validateNumbers()
        return n1 * n2
    }

    fun divide(): Double {
        val (n1, n2) = validateNumbers()

        if (n2 == 0.0) {
            throw DivisionByZeroException()
        }

        return n1 / n2
    }

    private fun validateNumbers(): Pair<Double, Double> {
        if (n1 == null || n2 == null) {
            throw NullNumbersException()
        }

        return Pair(n1!!, n2!!)
    }
}