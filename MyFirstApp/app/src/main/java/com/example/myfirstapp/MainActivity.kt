package com.example.myfirstapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myfirstapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var counter = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        counter = savedInstanceState?.getInt("counter_value") ?: 0
        updateCounter()

        binding.Increment.setOnClickListener { increment() }
        binding.Decrement.setOnClickListener { decrement() }
        binding.Reset.setOnClickListener { reset() }
    }
    private fun updateCounter(){
        binding.CounterNumber.text = counter.toString()
    }

    private fun increment(){
        counter++
        updateCounter()
    }

    private fun decrement( ){
        if (counter > 0) {
            counter--
        }
        updateCounter()
    }

    private fun reset(){
        counter = 0
        updateCounter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("counter_value", counter)
    }
}
