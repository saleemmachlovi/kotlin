package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.databinding.FunctionButtonsBinding
import com.example.calculator.databinding.NumberButtonsBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var numberBinding: NumberButtonsBinding
    private lateinit var functionBinding: FunctionButtonsBinding

    private val calculator = Calculator()
    private var expression = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Correct way: bind to the included views using their IDs
        numberBinding = NumberButtonsBinding.bind(binding.includeNumberButtons)
        functionBinding = FunctionButtonsBinding.bind(binding.includeFunctionButtons)

        setupNumberButtons()
        setupOperatorButtons()
        setupSpecialButtons()
    }

    private fun setupNumberButtons() {
        val numberButtons = listOf(
            numberBinding.btn0, numberBinding.btn1, numberBinding.btn2, numberBinding.btn3,
            numberBinding.btn4, numberBinding.btn5, numberBinding.btn6, numberBinding.btn7,
            numberBinding.btn8, numberBinding.btn9, numberBinding.btnDot, numberBinding.btn00
        )

        for (btn in numberButtons) {
            btn.setOnClickListener {
                expression.append(btn.text)
                updateExpression()
            }
        }
    }

    private fun setupOperatorButtons() {
        val operatorButtons = listOf(
            numberBinding.btnPlus, numberBinding.btnMinus, numberBinding.btnMultiply, functionBinding.btnDivide
        )

        for (btn in operatorButtons) {
            btn.setOnClickListener {
                if (expression.isNotEmpty() && !isLastCharOperator()) {
                    expression.append(" ${btn.text} ")
                    updateExpression()
                }
            }
        }
    }

    private fun setupSpecialButtons() {
        functionBinding.btnClear.setOnClickListener {
            expression.clear()
            binding.tvResult.text = "0"
            updateExpression()
        }

        functionBinding.btnDelete.setOnClickListener {
            if (expression.isNotEmpty()) {
                expression.deleteCharAt(expression.length - 1)
                updateExpression()
            }
        }

        numberBinding.btnEqual.setOnClickListener {
            if (expression.isNotEmpty()) {
                val result = calculator.calculate(expression.toString())
                binding.tvResult.text = result
            }
        }
    }

    private fun updateExpression() {
        binding.tvExpression.text = expression.toString()
    }

    private fun isLastCharOperator(): Boolean {
        return expression.lastOrNull()?.let { it in "+-×÷" } ?: false
    }
}
