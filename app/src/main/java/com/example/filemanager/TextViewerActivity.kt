package com.example.filemanager

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class TextViewerActivity : AppCompatActivity() {

    private lateinit var textViewContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_viewer)

        textViewContent = findViewById(R.id.textViewContent)

        // Lấy path file từ Intent
        val filePath = intent.getStringExtra("filePath")
        if (filePath != null) {
            val file = File(filePath)
            if (file.exists() && file.isFile) {
                val content = readTextFile(file)
                textViewContent.text = content
            }
        }
    }

    private fun readTextFile(file: File): String {
        val fileInputStream = FileInputStream(file)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val text = inputStreamReader.readText()
        inputStreamReader.close()
        return text
    }
}
