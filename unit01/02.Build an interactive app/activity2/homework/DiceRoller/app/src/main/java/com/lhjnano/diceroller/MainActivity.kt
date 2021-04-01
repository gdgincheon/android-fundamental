package com.lhjnano.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var diceImage: ImageView? = null
    var diceImage2 : ImageView ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diceImage = findViewById(R.id.dice_image)
        //diceImage2 = findViewById(R.id.dice_image2)

        val rollButton: Button = findViewById(R.id.roll_button)
        rollButton.setOnClickListener { rollDice() }

        val clearButton :  Button = findViewById(R.id.clear_button)
        clearButton.setOnClickListener { diceImage?.setImageResource(R.drawable.empty_dice) }
    }

    private fun rollDice() {
        val randomInt = (1..6).random()
        val drawableResource = when (randomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        diceImage?.setImageResource(drawableResource)
        //Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()
    }

    private fun rollDoubleDice()
    {
        diceImage?.setImageResource(getRandomDiceImage())
        diceImage2?.setImageResource(getRandomDiceImage())
    }


    private fun getRandomDiceImage() : Int {
        val randomInt = (1..6).random()
        val drawableResource = when (randomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        return drawableResource
    }


}