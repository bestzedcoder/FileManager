package com.example.a0810

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val chkCream: CheckBox = findViewById(R.id.chkCream)
        val chkSugar: CheckBox = findViewById(R.id.chkSugar)
        val radCoffeeType: RadioGroup = findViewById(R.id.radioGroupCoffeeType)
        val radDecaf: RadioButton = findViewById(R.id.radDecaf)
        val radExpresso: RadioButton = findViewById(R.id.radExpresso)
        val radColombian: RadioButton = findViewById(R.id.radColombian)
        val buttonLogin: Button = findViewById(R.id.btnPay)
        buttonLogin.setOnClickListener {
            var msg = "Coffee "
            if (chkCream.isChecked)
                msg = "$msg & cream "
            if (chkSugar.isChecked)
                msg = "$msg & sugar "
            val radioId = radCoffeeType.checkedRadioButtonId
            if (radColombian.id == radioId) msg = "Colombian $msg"
            if (radExpresso.isChecked) msg = "Expresso $msg"
            if (radDecaf.isChecked) msg = "Decaf $msg"
            // Alternative
            when (radioId) {
                R.id.radColombian -> msg = "Colombian $msg"
                R.id.radExpresso -> msg = "Expresso $msg"
                R.id.radDecaf -> msg = "Decaf $msg"
            }
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }
}