package com.cs4520.assignment3.mvvm

class CalculatorMVVM {
    private var n1: Double? = null
    private var n2: Double? = null

    fun setN1(n: Double?) {
        this.n1 = n
    }

    fun setN2(n: Double?) {
        this.n2 = n
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
            throw ArithmeticException("cannot divide by zero")
        }

        return n1 / n2
    }

    private fun validateNumbers(): Pair<Double, Double> {
        val n1 = this.n1 ?: throw NullPointerException("number 1 must not be empty")
        val n2 = this.n2 ?: throw NullPointerException("number 2 must not be empty")

        return Pair(n1, n2)
    }
}