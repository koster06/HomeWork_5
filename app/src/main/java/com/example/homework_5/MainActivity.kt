package com.example.homework_5

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
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
import com.example.homework_5.DatabaseHelper.Companion.COLUMN_ID
import com.example.homework_5.DatabaseHelper.Companion.TABLE_NAME
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
                    Log.i("test", "$indexToDelete")
                    deleteUserFromSql(user.id)
                    saveFile()
                    adapter.userList.removeAt(indexToDelete)
                    adapter.notifyDataSetChanged()
                    if (userListApp.isEmpty()) {
                        Log.i("test", "list empty")
                        File(filesDir, FILE_NAME).delete()
                    }
                }
            }) // override fun into interface

        init()
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
        const val TEST = "test"
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
            @SuppressLint("SuspiciousIndentation")
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                with(binding) {
                val nameFilled = editTextPersonName.text.toString()
                val surnameFilled = editTextPersonName2.text.toString()
                val phoneFilled = editTextPhone.text.toString()
                val ageFilled = editTextNumber.text.toString()
                val birthday = etDate.text.toString()
                    button.setEnabled(
                        !nameFilled.isEmpty()
                        && !surnameFilled.isEmpty()
                        && !phoneFilled.isEmpty()
                        && !ageFilled.isEmpty()
                        && !birthday.isEmpty()
                    )
                    if (button.isEnabled)saveInCache()
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
            Log.i(TEST, "adapter")
            userListApp = getUser()
            Log.i(TEST, "userList")
            if (userListApp.isNotEmpty()) {
            userListApp.forEach {
                adapter.addUser(it)
            }
        }
//            if (isFileExists(File(filesDir, FILE_NAME)) && !isFileEmpty(File(filesDir, FILE_NAME))) {
//                    openFile2()
//                    userListApp.forEach {
//                        adapter.addUser(it)
//                    }
//                } else {
//                    File(filesDir, FILE_NAME).createNewFile()
//                }
            button.setOnClickListener {
                sqlFactory(User(
                    0,
                    editTextPersonName.text.toString(),
                    editTextPersonName2.text.toString(),
                    editTextPhone.text.toString(),
                    editTextNumber.text.toString(),
                    etDate.text.toString(),
                    imageIdList[choosingPicture(editTextNumber.text.toString())]
                ))
                Log.i("test", "sql made user")
                adapter.addUser(sqlUnFactory())
                Log.i("test", "image: ${choosingPicture(editTextNumber.text.toString())}")
                //saveFile()

                it.hideKeyboard()
                clearEditText()
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
    private fun clearEditText(){
        with(binding) {
            editTextPersonName.text = null
            editTextPersonName2.text = null
            editTextPhone.text = null
            editTextNumber.text = null
            etDate.text = null
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

    private fun saveInCache(){ // writing cache file
        Log.i("test", "save in cache")
        with(binding) {
            val unterUser = UnterUser(
            editTextPersonName.text.toString(),
            editTextPersonName2.text.toString(),
            editTextPhone.text.toString(),
            editTextNumber.text.toString(),
            etDate.text.toString(),
            )
            val file = File.createTempFile(FILE_NAME, null, this@MainActivity.cacheDir)
            val output = FileOutputStream(file.absoluteFile)
            ObjectOutputStream(output).use {
                it.writeObject(unterUser)
            }
        }
    }

    private fun openFromCache(){
        Log.i("test", "open from cache")
        val tempFiles = this.cacheDir.listFiles()
        val file = File(this.cacheDir, tempFiles.last().name)
        Log.i("test", tempFiles.last().name)
        val input = FileInputStream(file)
        ObjectInputStream(input).use {
            val unterUser = it.readObject() as UnterUser
            fromCacheToEdit(unterUser)
        }
        Log.i("test", "unterUser")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("test", "on destroy")
        deleteCache(cacheDir)
    }

    private fun fromCacheToEdit(unterUser: UnterUser) {
        with(binding){
            editTextPersonName.setText(unterUser.name)
            editTextPersonName2.setText(unterUser.secName)
            editTextPhone.setText(unterUser.phone)
            editTextNumber.setText(unterUser.age)
            etDate.text = unterUser.birthday
        }
    }

    private fun deleteCache(file : File){
        file.deleteRecursively()
    }

    private fun sqlFactory (user: User):User {
        sqlDb = databaseHelper.readableDatabase
        cv.put(DatabaseHelper.COLUMN_PERSON, user.name)
        cv.put(DatabaseHelper.COLUMN_SURNAME, user.secName)
        cv.put(DatabaseHelper.COLUMN_PHONE, user.phone.toInt())
        cv.put(DatabaseHelper.COLUMN_AGE, user.age.toInt())
        cv.put(DatabaseHelper.COLUMN_DATE, user.birthday)
        cv.put(DatabaseHelper.COLUMN_IMAGE, user.image)
        sqlDb.insert(TABLE_NAME, null, cv)
        return user
    }

    private fun sqlUnFactory(): User {
        lateinit var user : User
        val qry = "Select * From $TABLE_NAME"
        sqlDb = databaseHelper.readableDatabase
        cursor = sqlDb.rawQuery(qry, null)
        if ( cursor.count != 0) {
            cursor.use {
                while (cursor.moveToNext()) {
                    user = User(
                        it.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        it.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PERSON)),
                        it.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SURNAME)),
                        it.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE)),
                        it.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AGE)),
                        it.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE)),
                        it.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE))
                    )
                    Log.i(TEST, "${user.id}")
                }
            }
        }
        return user
    }

    fun deleteUserFromSql(userId: Int) {
        val qry = "Delete From $TABLE_NAME where $COLUMN_ID = $userId"
        sqlDb = databaseHelper.writableDatabase
        val cursor = sqlDb.execSQL(qry)
        sqlDb.close()
    }

    private fun getUser():ArrayList<User> {
        val qry = "Select * From $TABLE_NAME"
        val db = databaseHelper.readableDatabase
        cursor = db.rawQuery(qry, null)
        val userList = ArrayList<User>()
        if (cursor.count != 0) {
            Log.i(TEST, "getUser")
            while (cursor.moveToNext()) {
                Log.i(TEST, "cursor")
                val user = User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PERSON)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SURNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE))
                )
                userList.add(user)
            }
        }
        cursor.close()
        return userList
    }

    private fun sortSql () {

    }

    private fun isFileCacheExist(): Boolean {
        val tempFiles = this.cacheDir.listFiles()
        return if (tempFiles.isNotEmpty()) {
            val file = File(this.cacheDir, tempFiles[0].name)
            file.exists() && !file.isDirectory && file.length() != 0L
        } else false
    }

    @SuppressLint("SuspiciousIndentation")
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
Задание 8.1. SQLiteOpenHelper
1) При вводе данных в поле и нажатии на кнопку, запишите введенный данные
в базу данных используя SQLiteOpenHelper. ++
2) Данные в список должны считываться из бд. ++
3) По нажатию на кнопку “Удалить” элемент должен удаляться из бд и из recyclerView ++
4) Перед списком добавить две кнопки, по нажатию на которые происходит сортировка списка
(по какому принципу сортировать решайте сами)
5) Добавить кнопку “отобразить первые 5 элементов”, по нажатию на которую
происходит отображение первых 5 элементов. (в списке на этот момент должно быть минимум 6 элементов)
*/