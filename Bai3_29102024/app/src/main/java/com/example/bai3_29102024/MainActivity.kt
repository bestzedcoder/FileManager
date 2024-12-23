package com.example.bai3_29102024

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    // Data class trong cùng file
    data class Student(val name: String, val studentId: String)

    // Adapter trong cùng file
    private inner class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
        private var studentList = listOf<Student>()
        private var filteredList = listOf<Student>()

        init {
            filteredList = studentList
        }

        fun setData(list: List<Student>) {
            studentList = list
            filteredList = list
            notifyDataSetChanged()
        }

        fun filter(query: String) {
            filteredList = if (query.length > 2) {
                studentList.filter { student ->
                    student.name.contains(query, ignoreCase = true) ||
                            student.studentId.contains(query, ignoreCase = true)
                }
            } else {
                studentList
            }
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student, parent, false)
            return StudentViewHolder(view)
        }

        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
            val student = filteredList[position]
            holder.nameTextView.text = student.name
            holder.studentIdTextView.text = student.studentId
        }

        override fun getItemCount() = filteredList.size

        inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
            val studentIdTextView: TextView = itemView.findViewById(R.id.studentIdTextView)
        }
    }

    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Thiết lập edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Khởi tạo RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.studentRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter

        val sampleStudents = listOf(
            Student("Nguyễn Văn An", "20200001"),
            Student("Trần Thị Bình", "20200002"),
            Student("Lê Văn Cường", "20200003"),
            Student("Phạm Thị Dung", "20200004"),
            Student("Hoàng Văn Em", "20200005"),
            Student("Đỗ Thị Phương", "20200006"),
            Student("Vũ Văn Giàu", "20200007"),
            Student("Bùi Thị Hoa", "20200008"),
            Student("Ngô Văn Inh", "20200009"),
            Student("Mai Thị Kim", "20200010")
        )
        adapter.setData(sampleStudents)

        // Xử lý tìm kiếm
        val searchEditText: EditText = findViewById(R.id.searchEditText)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                adapter.filter(s.toString())
            }
        })
    }
}