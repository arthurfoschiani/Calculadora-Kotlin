package com.example.calculadorakotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.calculadorakotlin.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    private var valueOne: Double? = null
    private var valueTwo: Double? = null
    private var currentOperation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClear.setOnClickListener { clearValues() }
        binding.btnSign.setOnClickListener { invertSign() }
        binding.btnPercent.setOnClickListener { calculatePercentage() }
        binding.btnEquals.setOnClickListener { calculateResult() }

        listOf(
            binding.btnDivide, binding.btnMultiply, binding.btnSubtract, binding.btnAdd
        ).forEach { button ->
            button.setOnClickListener { selectOperation(button.text.toString()) }
        }

        listOf(
            binding.btnZero, binding.btnOne, binding.btnTwo, binding.btnThree,
            binding.btnFour, binding.btnFive, binding.btnSix, binding.btnSeven,
            binding.btnEight, binding.btnNine, binding.btnDecimal
        ).forEach { button ->
            button.setOnClickListener { appendNumber(button.text.toString()) }
        }
    }

    private fun appendNumber(number: String) {
        if (number == "." && binding.tvDisplay.text.toString().contains(".")) {
            return
        }
        if (binding.tvDisplay.text.toString() == "0" && number != ".") {
            binding.tvDisplay.text = number
        } else {
            binding.tvDisplay.text = binding.tvDisplay.text.toString() + number
        }
    }

    private fun selectOperation(operation: String) {
        if (valueOne == null) {
            valueOne = binding.tvDisplay.text.toString().toDouble()
            currentOperation = operation
            binding.tvDisplay.text = "0"
        }
    }

    private fun calculateResult() {
        if (valueOne != null && currentOperation != null) {
            valueTwo = binding.tvDisplay.text.toString().toDouble()

            val result = when (currentOperation) {
                "+" -> valueOne!! + valueTwo!!
                "-" -> valueOne!! - valueTwo!!
                "*" -> valueOne!! * valueTwo!!
                "/" -> if (valueTwo != 0.0) valueOne!! / valueTwo!! else Double.NaN
                else -> Double.NaN
            }

            binding.tvDisplay.text = DecimalFormat("#.########").format(result)

            valueOne = null
            currentOperation = null
            valueTwo = null
        }
    }

    private fun clearValues() {
        valueOne = null
        valueTwo = null
        currentOperation = null
        binding.tvDisplay.text = "0"
    }

    private fun invertSign() {
        val currentValue = binding.tvDisplay.text.toString().toDouble()
        binding.tvDisplay.text = (currentValue * -1).toString()
    }

    private fun calculatePercentage() {
        val currentValue = binding.tvDisplay.text.toString().toDouble()
        binding.tvDisplay.text = (currentValue / 100).toString()
    }
}