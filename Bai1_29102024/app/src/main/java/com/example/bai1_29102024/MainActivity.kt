package com.example.bai1_29102024

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khai báo các view
        val numberInput = findViewById<EditText>(R.id.numberInput)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val showButton = findViewById<Button>(R.id.showButton)
        val listView = findViewById<ListView>(R.id.listView)
        val errorText = findViewById<TextView>(R.id.errorText)

        showButton.setOnClickListener {
            val inputText = numberInput.text.toString()
            if (inputText.isEmpty()) {
                errorText.text = "Khong co du lieu"
                errorText.visibility = TextView.VISIBLE
                return@setOnClickListener
            }

            val number = inputText.toIntOrNull()
            if (number == null || number < 0) {
                errorText.text = "Du lieu khong hop le"
                errorText.visibility = TextView.VISIBLE
                return@setOnClickListener
            }

            errorText.visibility = TextView.GONE
            val selectedOption = radioGroup.checkedRadioButtonId
            val numbersList = when (selectedOption) {
                R.id.radio_even -> generateEvenNumbers(number)
                R.id.radio_odd -> generateOddNumbers(number)
                R.id.radio_square -> generatePerfectSquares(number)
                else -> emptyList()
            }

            // Chỉ định rõ kiểu Int cho ArrayAdapter
            val adapter = ArrayAdapter<Int>(
                this,
                android.R.layout.simple_list_item_1,
                numbersList
            )
            listView.adapter = adapter
        }
    }

    private fun generateEvenNumbers(n: Int): List<Int> {
        return (0..n).filter { it % 2 == 0 }
    }

    private fun generateOddNumbers(n: Int): List<Int> {
        return (1..n).filter { it % 2 != 0 }
    }

    private fun generatePerfectSquares(n: Int): List<Int> {
        val result = mutableListOf<Int>()
        var i = 0
        while (i * i <= n) {
            result.add(i * i)
            i++
        }
        return result
    }
}

