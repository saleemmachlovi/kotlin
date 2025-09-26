package com.example.calculator

import java.lang.Exception
import java.util.Stack

class Calculator {

    fun calculate(expression: String): String {
        return try {
            val tokens = expression.split(" ")
            val numbers = Stack<Double>()
            val operators = Stack<String>()

            for (token in tokens) {
                when {
                    token.isNumber() -> numbers.push(token.toDouble())
                    token in listOf("+", "-", "×", "÷") -> operators.push(token)
                }
            }

            while (operators.isNotEmpty()) {
                val num2 = numbers.pop()
                val num1 = numbers.pop()
                val op = operators.pop()

                val result = when (op) {
                    "+" -> num1 + num2
                    "-" -> num1 - num2
                    "×" -> num1 * num2
                    "÷" -> if (num2 != 0.0) num1 / num2 else return "Error"
                    else -> 0.0
                }
                numbers.push(result)
            }

            if (numbers.isNotEmpty()) numbers.pop().toString() else "0"
        } catch (e: Exception) {
            "Error"
        }
    }

    private fun String.isNumber(): Boolean {
        return this.toDoubleOrNull() != null
    }
}
