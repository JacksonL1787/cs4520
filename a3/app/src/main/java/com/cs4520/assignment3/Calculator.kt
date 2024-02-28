package com.cs4520.assignment3

import com.cs4520.assignment3.common.DivisionByZeroException
import com.cs4520.assignment3.common.NullNumbersException

class Calculator {
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
            throw DivisionByZeroException()
        }

        return n1 / n2
    }

    private fun validateNumbers(): Pair<Double, Double> {
        if(this.n1 == null || this.n2 == null) {
            throw NullNumbersException()
        }

        return Pair(this.n1!!, this.n2!!)
    }
}