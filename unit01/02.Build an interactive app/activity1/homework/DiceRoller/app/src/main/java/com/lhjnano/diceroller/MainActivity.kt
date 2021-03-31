package com.lhjnano.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton : Button = findViewById(R.id.roll_button)
        rollButton.setOnClickListener { rollDice() }

        val resetButton : Button = findViewById(R.id.reset_button)
        resetButton.setOnClickListener { reset() }

        val resultText : TextView = findViewById(R.id.result_text)
        resultText.text = "Dice Rolled!"
    }

    private fun rollDice() {
        val randomInt = (1..6).random()
        val resultText : TextView = findViewById(R.id.result_text)
        resultText.text = randomInt.toString()
        Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()
    }

    private fun reset() {
        val resultText : TextView = findViewById(R.id.result_text)
        val zero = 0
        resultText.text = zero.toString()
    }
}