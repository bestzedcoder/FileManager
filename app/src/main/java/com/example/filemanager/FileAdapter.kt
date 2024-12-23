package com.example.filemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class FileAdapter(
    private var fileList: List<File>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(file: File)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = fileList[position]
        holder.bind(file)
        holder.itemView.setOnClickListener {
            listener.onItemClick(file)
        }
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewFileName: TextView = itemView.findViewById(R.id.textViewFileName)

        fun bind(file: File) {
            textViewFileName.text = file.name
        }
    }
}
