package com.example.homework_5

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.homework_5.databinding.ActivityMain3Binding
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class MainActivity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            if (isFileExists(File(filesDir, FILE_NAME))){
                Toast.makeText(applicationContext, "File is exist", Toast.LENGTH_SHORT).show()
                openFile1()
            } else {Toast.makeText(applicationContext, "File isn't exist", Toast.LENGTH_SHORT).show()}
            bindOpenButtonListener()
            bindSaveButtonListener()
        }
    }

    private fun ActivityMain3Binding.bindOpenButtonListener() {
        openButton.setOnClickListener {
            try {
                openFile1()
            } catch (e: Exception) {
                showError(R.string.cant_open_file)
            }
        }
    }

    private fun ActivityMain3Binding.bindSaveButtonListener() {
        saveButton.setOnClickListener {
            try {
                saveFile()
            } catch (e: Exception) {
                showError(R.string.cant_save_file)
            }
            contentEditText.setText("")
        }
    }

    // Open: example with wrappers
    private fun ActivityMain3Binding.openFile1() {
        val file = File(filesDir, FILE_NAME)
        val inputStream = FileInputStream(file)
        val reader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(reader)
        val data = bufferedReader.use {
            it.readLines().joinToString(separator = "\n")
        }
        contentEditText.setText(data)
    }

    // Open: example without wrappers
    private fun ActivityMain3Binding.openFile2() {
        val file = File(filesDir, FILE_NAME)
        val data = FileInputStream(file).use {
            String(it.readBytes())
        }
        contentEditText.setText(data)
    }

    private fun ActivityMain3Binding.saveFile() {
        val file = File(filesDir, FILE_NAME)
        FileOutputStream(file, true).use {
            val bytes = contentEditText.text.toString().toByteArray()
            it.write(bytes)
        }
    }

    private fun showError(@StringRes res: Int) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show()
    }

    private companion object {
        const val FILE_NAME = "my-file2.txt"
    }

    private fun isFileExists(file: File): Boolean {
        return file.exists() && !file.isDirectory
    }

}