package com.example.calculatorapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.calculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Button Click Listeners
        binding.btnAdd.setOnClickListener { calculate("add") }
        binding.btnMinus.setOnClickListener { calculate("subtract") }
        binding.btnMultiply.setOnClickListener { calculate("multiply") }
        binding.btnDivide.setOnClickListener { calculate("divide") }
        binding.btnClear.setOnClickListener { clearAll() }
    }

    private fun calculate(operation: String) {
        val num1Text = binding.etNumber1.text.toString()
        val num2Text = binding.etNumber2.text.toString()

        // Validate input
        if (num1Text.isEmpty() || num2Text.isEmpty()) {
            binding.tvResult.text = getString(R.string.inputError)
            return
        }

        // Convert to Double
        val num1 = num1Text.toDouble()
        val num2 = num2Text.toDouble()

        // Use CalculatorFactory
        val calculator = CalculatorFactory.create(num1, num2)

        // Perform operation
        val result = when (operation) {
            "add" -> calculator.add()
            "subtract" -> calculator.subtract()
            "multiply" -> calculator.multiply()
            "divide" -> calculator.divide()
            else -> 0.0
        }

        binding.tvResult.text = result.toString()
    }

    private fun clearAll() {
        binding.etNumber1.text?.clear()
        binding.etNumber2.text?.clear()
        binding.tvResult.text = getString(R.string.result)
    }
}
