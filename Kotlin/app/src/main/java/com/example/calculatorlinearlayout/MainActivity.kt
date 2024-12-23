package com.example.calculatorlinearlayout

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var tvDisplay: TextView
    var expression = ""

    var state = 1
    var op = 0
    var op1 = 0
    var op2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)


        listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonAdd, R.id.buttonSub,
            R.id.buttonMul, R.id.buttonDiv, R.id.buttonEqual, R.id.buttonC
        ).forEach { id ->
            findViewById<Button>(id).setOnClickListener(this)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button0 -> addDigit(0)
            R.id.button1 -> addDigit(1)
            R.id.button2 -> addDigit(2)
            R.id.button3 -> addDigit(3)
            R.id.button4 -> addDigit(4)
            R.id.button5 -> addDigit(5)
            R.id.button6 -> addDigit(6)
            R.id.button7 -> addDigit(7)
            R.id.button8 -> addDigit(8)
            R.id.button9 -> addDigit(9)

            R.id.buttonAdd -> setOperator(1, "+")
            R.id.buttonSub -> setOperator(2, "-")
            R.id.buttonMul -> setOperator(3, "*")
            R.id.buttonDiv -> setOperator(4, "/")

            R.id.buttonEqual -> calculateResult()
            R.id.buttonC -> clearAll()
        }
    }

    private fun addDigit(digit: Int) {
        if (state == 1) {
            op1 = op1 * 10 + digit
        } else {
            op2 = op2 * 10 + digit
        }
        expression += digit.toString()
        tvDisplay.text = expression
    }

    private fun setOperator(operator: Int, symbol: String) {
        op = operator
        state = 2
        expression += " $symbol "
        tvDisplay.text = expression
    }

    private fun calculateResult() {
        var result = 0.0

        when (op) {
            1 -> result = (op1 + op2).toDouble()
            2 -> result = (op1 - op2).toDouble()
            3 -> result = (op1 * op2).toDouble()
            4 -> {
                if (op2 != 0) {
                    result = op1.toDouble() / op2.toDouble()
                } else {
                    tvDisplay.text = "Error"
                    return
                }
            }
        }

        expression += " = $result"
        tvDisplay.text = expression

        clearAfterResult()
    }

    private fun clearAfterResult() {
        state = 1
        op1 = 0
        op2 = 0
        op = 0
        expression = ""
    }

    private fun clearAll() {
        tvDisplay.text = "0"
        clearAfterResult()
    }
}
