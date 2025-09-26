package com.example.hello_user

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hello_user.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding.btnGreet.setOnClickListener {
            val userName: String = binding.name.text.toString().trim()

            if (userName.isNotEmpty()) {
                binding.greeting.text = "Hello $userName!"
            } else {
                binding.greeting.text = ""
            }
        }
    }
}
