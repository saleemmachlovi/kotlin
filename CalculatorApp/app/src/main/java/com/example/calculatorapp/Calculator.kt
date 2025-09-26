package com.example.calculatorapp;

open class Calculator(private val num1: Double, private val num2: Double) {

    fun add():Double{
        return num1 + num2
    }
    fun subtract():Double{
        return num1 - num2
    }
    fun multiply():Double{
        return num1 * num2
    }

    fun divide():Double{
        return if(num2 != 0.0){
            num1 / num2
        }
        else{
            Double.NaN
        }
    }
}

//class AdvancedCalculator(num1: Double, num2:Double): Calculator(num1, num2){
//
//    fun power(): Double{
//        return Math.pow(num1, num2)
//    }
//}

object CalculatorFactory {
    fun create(num1: Double, num2: Double): Calculator {
        return Calculator(num1, num2)
    }
}