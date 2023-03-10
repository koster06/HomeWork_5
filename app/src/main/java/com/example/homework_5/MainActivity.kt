package com.example.homework_5

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework_5.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    lateinit var sqlDb: SQLiteDatabase
    private val databaseHelper = DatabaseHelper(this)
    private lateinit var cursor: Cursor
    lateinit var binding: ActivityMainBinding
    private val cv = ContentValues()

    var userListApp = mutableListOf<User>()
    lateinit var adapter: UserAdapter
    private val imageIdList = listOf(
        R.drawable.bird1,
        R.drawable.bird2,
        R.drawable.bird3,
        R.drawable.bird4,
        R.drawable.bird5,
        R.drawable.bird6,
        R.drawable.bird7,
        R.drawable.bird8,
        R.drawable.bird9
    )
    private val saveLauncher =
        registerForActivityResult(ActivityResultContracts.CreateDocument("text/plain")) { uri ->
            try {
                uri?.let { saveFile1(it) }
            } catch (e: Exception) {
                showError(R.string.cant_save_file)
            }
        } //it makes dialog for saving file
    private val externalState = Environment.getExternalStorageState()!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("test", "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        cursor = sqlDb.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)
//        cursor.moveToLast()
//        println("SQL: ${cursor.getString((1))}")

        setSupportActionBar(binding.toolbar)
        binding.navView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MessageFragment.newInstance())
                binding.navView.setCheckedItem(R.id.nav_message)
            }

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                writeLabel(myCalendar)
            }
        binding.etDate.setOnClickListener {
                DatePickerDialog(
                    this,
                    datePicker,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        with(binding) {
                etDate.addTextChangedListener(textWatcher)
                editTextPersonName.addTextChangedListener(textWatcher)
                editTextPersonName2.addTextChangedListener(textWatcher)
                editTextPhone.addTextChangedListener(textWatcher)
                editTextNumber.addTextChangedListener(textWatcher)
            } //textWatcher

        adapter = UserAdapter(object : AdapterListener {
                override fun removeUser(user: User) {
                    val indexToDelete = adapter.userList
                        .indexOfFirst { it.id == user.id }
                    userListApp.removeAt(indexToDelete)
                    saveFile()
                    adapter.userList.removeAt(indexToDelete)
                    adapter.notifyDataSetChanged()
                    if (userListApp.isEmpty()) {
                        Log.i("test", "list empty")
                        File(filesDir, FILE_NAME).delete()
                    }
                }
            }) // override fun into interface

        saveToInternalStorage(BitmapFactory.decodeResource(resources, R.drawable.bird1))
        saveToExternalStorage(BitmapFactory.decodeResource(resources, R.drawable.bird2))

        if (isFileExists(File(filesDir, FILE_NAME)) && !isFileEmpty(File(filesDir, FILE_NAME))) {
                Log.i("test", "second if")
                createSimpleDialog()
            } else init() // if file isn't exist & file isn't empty
    }

// functions
//--------------------------------------------------------------------------------------------------

    private fun ActivityMainBinding.bindSaveButtonListener() {
            button.setOnClickListener {
                try {
                    saveFile()
                } catch (e: Exception) {
                    showError(R.string.cant_save_file)
                }
            }
        }

        //Open: example without wrappers
    private fun openFile2() {
            val file = File(filesDir, FILE_NAME)
            val input = FileInputStream(file)
            val data = ObjectInputStream(input)
            val userList = data.readObject() as ArrayList<User>
            userList.forEach {
                userListApp.add(it)
            }
            data.close()
            input.close()
        }

    private fun saveFile() {
            val file = File(filesDir, FILE_NAME)
            val output = FileOutputStream(file)
            ObjectOutputStream(output).use {
                it.writeObject(userListApp)
            }
        }

    private fun showError(@StringRes res: Int) {
            Toast.makeText(this, res, Toast.LENGTH_SHORT).show()
        }

    private companion object {
        const val FILE_NAME = "my-file"
        }
    override fun onBackPressed() {
            with(binding) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START)
                else super.onBackPressed()
            }
        }

    private fun View.hideKeyboard() {
            val inputManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(windowToken, 0)
        }

    private val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val nameFilled = binding.editTextPersonName.text.toString()
                val surnameFilled = binding.editTextPersonName2.text.toString()
                val phoneFilled = binding.editTextPhone.text.toString()
                val ageFilled = binding.editTextNumber.text.toString()
                val birthday = binding.etDate.text.toString()
                with(binding) {
                    button.setEnabled(
                        !nameFilled.isEmpty()
                                && !surnameFilled.isEmpty()
                                && !phoneFilled.isEmpty()
                                && !ageFilled.isEmpty()
                                && !birthday.isEmpty()
                    )
                }
            }

            override fun afterTextChanged(s: Editable) {}
        }

    private fun writeLabel(myCalendar: Calendar) {
            val myFormat = "dd-MMMM-yyyy"
            val simpleDF = SimpleDateFormat(myFormat)
            binding.etDate.setText((simpleDF.format(myCalendar.time)))
        }

    private fun init() {
        Log.i("test", "init")
        binding.apply {
            recyclerConteiner.layoutManager = GridLayoutManager(this@MainActivity, 1)
            recyclerConteiner.adapter = adapter
            if (isFileCacheExist()) {
                openFromCache()
                userListApp.forEach {
                    adapter.addUser(it)
                }
            } else {
                if (isFileExists(File(filesDir, FILE_NAME)) && !isFileEmpty(File(filesDir, FILE_NAME))) {
                    openFile2()
                    userListApp.forEach {
                        adapter.addUser(it)
                    }
                } else {
                    File(filesDir, FILE_NAME).createNewFile()
                }
            }
            button.setOnClickListener {
                val user = User(imageIdList[choosingPicture(editTextNumber.text.toString())],
                    editTextPersonName.text.toString(),
                    editTextPersonName2.text.toString(),
                    editTextPhone.text.toString(),
                    editTextNumber.text.toString(),
                    etDate.text.toString()
                    )
                    sqlFactory(user)
                    adapter.addUser(user)
                    userListApp.add(user)
                    saveFile()
                    it.hideKeyboard()
                    editTextPersonName.text = null
                    editTextPersonName2.text = null
                    editTextPhone.text = null
                    editTextNumber.text = null
                    etDate.text = null
                }
            button2.setOnClickListener {
                if (externalState == Environment.MEDIA_MOUNTED) {
                    val file = File (this@MainActivity.getExternalFilesDir(null), FILE_NAME)
                    if (!isFileExists(file)) {
                        Toast.makeText(
                            this@MainActivity,
                            "The file will be written in External storage",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    if (isFileExists(file)){
                        createSimpleDialog2()
                    }
                    FileOutputStream(file).use {
                        ObjectOutputStream(it).writeObject(userListApp)
                    }
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
            if (item.itemId == R.id.nav_message) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, MessageFragment.newInstance())
                    .commit()
                Toast.makeText(this, "To Message Fragment", Toast.LENGTH_SHORT).show()
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            return true
        }

    private fun isFileExists(file: File): Boolean {
            return file.exists() && !file.isDirectory
        }

    private fun isFileEmpty(file: File): Boolean {
            return (file.length() == 0L)
        }

    private fun choosingPicture(age:String): Int {
        return when(age.toInt()) {
            in 0..9 -> 1
            in 10 .. 19 -> 2
            in 20 .. 29 -> 4
            in 30 .. 39 -> 6
            in 40 .. 49 -> 8
            in 50 .. 59 -> 0
            in 60 .. 79 -> 3
            in 80 .. 89 -> 5
            in 90 .. 99 -> 7
            else -> {7}
        }
    }

    private fun createSimpleDialog() {
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("Attention!")
                setMessage("You have saved users. Do you want continue or delete list?")
                setNegativeButton("Delete") { dialogInterface, i ->
                    File(filesDir, FILE_NAME).delete()
                    Log.i("test", "removing file")
                    init()
                }
                setPositiveButton("Continue") { dialogInterface, i ->
                    setCancelable(true)
                    Log.i("test", "continue with file")
                    init()
                }
                show()
            }
    }

    private fun createSimpleDialog2() {
        Log.i("test", "dialog 2")
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Attention!")
            setMessage("You have saved users. Do you want continue or delete list?")
            setNegativeButton("Delete") { dialogInterface, i ->
                File (this@MainActivity.getExternalFilesDir(null), FILE_NAME).delete()
                Log.i("test", "remove file in External")
            }
            setPositiveButton("Continue") { dialogInterface, i ->
                setCancelable(true)
                Log.i("test", "continue with file")
            }
            show()
        }
    }

    private fun saveFile1(uri: Uri) = contentResolver.openOutputStream(uri)?.use {
        contentResolver.openOutputStream(uri)?.use {
            ObjectOutputStream(it).writeObject(userListApp)
        } ?: throw IllegalStateException("Can't open output stream")
    }

    private fun openFile(uri: Uri) {
        val data = contentResolver.openInputStream(uri)?.use {
            String(it.readBytes())
        } ?: throw IllegalStateException("Can't open input stream")
        //binding.contentEditText.setText(data)
    }

    override fun onPause() {
        super.onPause()
        //saveInCache()
        Log.i("test", "----------------------")
        Log.i("test", "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.i("test", "onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.i("test", "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("test", "onRestart")
    }

    private fun saveInCache(){ // writing cache file
        Log.i("test", "save in cache")
        val file = File.createTempFile(FILE_NAME, null, this.cacheDir)
        val output = FileOutputStream(file.absoluteFile)
        ObjectOutputStream(output).use {
            it.writeObject(userListApp)
        }
    }

    private fun openFromCache(){
        Log.i("test", "open from cache")
        val tempFiles = this.cacheDir.listFiles()
        val file = File(this.cacheDir, tempFiles.last().name)
        Log.i("test", tempFiles.last().name)
        val input = FileInputStream(file)
        val data = ObjectInputStream(input)
        val userList = data.readObject() as ArrayList<User>
        userList.forEach {
            userListApp.add(it)
            Log.i("test", "userListApp ${userListApp.size}")
        }
        data.close()
        input.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("test", "on destroy")
        deleteCache(cacheDir)
    }

    private fun deleteCache(file : File){
        file.deleteRecursively()
    }

    private fun sqlFactory (user: User) {
        sqlDb = databaseHelper.readableDatabase
        cv.put(DatabaseHelper.COLUMN_PERSON, user.name)
        cv.put(DatabaseHelper.COLUMN_SURNAME, user.secName)
        cv.put(DatabaseHelper.COLUMN_PHONE, user.phone.toInt())
        cv.put(DatabaseHelper.COLUMN_AGE, user.age.toInt())
        cv.put(DatabaseHelper.COLUMN_DATE, user.birthday)
        sqlDb.insert(DatabaseHelper.TABLE_NAME, null, cv)
    }

    private fun isFileCacheExist(): Boolean {
        val tempFiles = this.cacheDir.listFiles()
        return if (tempFiles.isNotEmpty()) {
            val file = File(this.cacheDir, tempFiles[0].name)
            file.exists() && !file.isDirectory && file.length() != 0L
        } else false
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap){
        val cw = ContextWrapper(applicationContext)
        val directory = cw.getDir("imageDir", MODE_PRIVATE)
        val myPath = File(directory, "profile.jpg")
        val out = FileOutputStream(myPath)
            out.use {
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
    } //save image from res to internal storage
    private fun saveToExternalStorage(bitmapImage: Bitmap) {
        if (externalState == Environment.MEDIA_MOUNTED) {
            val file = File (this@MainActivity.getExternalFilesDir(null), "profile.jpg")
            val out = FileOutputStream(file)
            out.use {
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
        }
    } //save image from res to external storage

}
/*
DESCRIPTION
--------------------------------------------------------------------------------------------------
Задание 7.4
Сохранить изображение из ресурсов во внешнюю память
Сохранить изображение из ресурсов во внутреннюю память

Задание 8.1. SQLiteOpenHelper
1) При вводе данных в поле и нажатии на кнопку, запишите введенный данные
в базу данных используя SQLiteOpenHelper. ++
2) Данные в список должны считываться из бд.
3) По нажатию на кнопку “Удалить” элемент должен удаляться из бд и из recyclerView
4) Перед списком добавить две кнопки, по нажатию на которые происходит сортировка списка
(по какому принципу сортировать решайте сами)
5) Добавить кнопку “отобразить первые 5 элементов”, по нажатию на которую
происходит отображение первых 5 элементов. (в списке на этот момент должно быть минимум 6 элементов)
*/