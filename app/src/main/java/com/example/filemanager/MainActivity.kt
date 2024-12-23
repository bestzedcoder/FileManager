package com.example.filemanager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewFiles: RecyclerView
    private lateinit var fileAdapter: FileAdapter

    private val STORAGE_PERMISSION_CODE = 100

    // Lưu đường dẫn hiện tại để có thể xử lý quay lại thư mục cha nếu muốn
    private var currentDirectory: File? = null
    private val directoryStack = ArrayDeque<File>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewFiles = findViewById(R.id.recyclerViewFiles)
        recyclerViewFiles.layoutManager = LinearLayoutManager(this)

        // Kiểm tra và xin quyền đọc file
        checkPermissionAndLoadFiles()
    }

    private fun checkPermissionAndLoadFiles() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Chưa có quyền -> Yêu cầu quyền
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
        } else {
            // Quyền đã được cấp -> load file ở bộ nhớ ngoài
            val externalDir = Environment.getExternalStorageDirectory()
            loadFiles(externalDir)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Người dùng đã cấp quyền
                val externalDir = Environment.getExternalStorageDirectory()
                loadFiles(externalDir)
            } else {
                // Người dùng từ chối quyền
                Toast.makeText(this, "Bạn chưa cấp quyền đọc bộ nhớ!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadFiles(directory: File) {
        if (!directory.exists() || !directory.isDirectory) {
            Toast.makeText(this, "Không thể đọc thư mục này!", Toast.LENGTH_SHORT).show()
            return
        }

        currentDirectory = directory
        val files = directory.listFiles()?.toList() ?: emptyList()

        // Lọc lại hoặc sắp xếp nếu muốn
        // Ví dụ: sắp xếp thư mục lên trên, file xuống dưới
        val sortedList = files.sortedWith(compareBy({ !it.isDirectory }, { it.name }))

        fileAdapter = FileAdapter(sortedList, object : FileAdapter.OnItemClickListener {
            override fun onItemClick(file: File) {
                if (file.isDirectory) {
                    directoryStack.addLast(directory) // Đẩy directory hiện tại vào stack
                    loadFiles(file)
                } else {
                    // Nếu là file -> kiểm tra có phải file .txt hay không
                    if (file.extension.equals("txt", ignoreCase = true)) {
                        // Mở TextViewerActivity hiển thị nội dung
                        val intent = Intent(this@MainActivity, TextViewerActivity::class.java)
                        intent.putExtra("filePath", file.absolutePath)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Không hỗ trợ mở file này!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
        recyclerViewFiles.adapter = fileAdapter
    }

    // Xử lý nút Back để quay lại thư mục cha (nếu có)
    override fun onBackPressed() {
        if (directoryStack.isNotEmpty()) {
            val parentDirectory = directoryStack.removeLast()
            loadFiles(parentDirectory)
        } else {
            super.onBackPressed()
        }
    }
}
