package com.example.homework_5

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework_5.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding
    private var userList = kotlin.collections.ArrayList<User>()
    var userListApp = mutableListOf<User>()
    lateinit var adapter : UserAdapter
    private val imageIdList = listOf (
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
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//----------------------------------------------------------------------
        setSupportActionBar(binding.toolbar)
        binding.navView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MessageFragment.newInstance())
            binding.navView.setCheckedItem(R.id.nav_message)
        }
//---------------------------------------------------------------------
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            writeLabel(myCalendar)
        }
        binding.etDate.setOnClickListener{
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        with(binding) {
            etDate.addTextChangedListener(textWatcher)
            editTextPersonName.addTextChangedListener(textWatcher)
            editTextPersonName2.addTextChangedListener(textWatcher)
            editTextPhone.addTextChangedListener(textWatcher)
            editTextNumber.addTextChangedListener(textWatcher)
        }
//------------------------------------------------------------------------
        adapter = UserAdapter(object : AdapterListener{
            override fun removeUser(user: User) {
                val indexToDelete = adapter.userList
                    .indexOfFirst { it.id == user.id }
                userListApp.removeAt(indexToDelete)
                Log.i("test", "we are remove: ${userListApp.size} ")
                binding.saveFile()
                adapter.userList.removeAt(indexToDelete)
                adapter.notifyDataSetChanged()
            }
        })
//--------------------------------------------------------------------------
        createSimpleDialog()
        //init()

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
    private fun ActivityMainBinding.openFile2() {
        val file = File(filesDir, FILE_NAME)
        val input = FileInputStream(file)
        val data = ObjectInputStream(input)
        val userList =  data.readObject() as ArrayList<User>
        userList.forEach{
            userListApp.add(it)
            Log.i("test", "openFile2 ${userListApp.size}")
        }
        data.close()
        input.close()
    }

    private fun ActivityMainBinding.saveFile() {
        val file = File(filesDir, FILE_NAME)
        Log.i("test" ,"saveFile ${userListApp.size}")
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
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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
            with(binding){
                button.setEnabled(!nameFilled.isEmpty()
                        && !surnameFilled.isEmpty()
                        && !phoneFilled.isEmpty()
                        && !ageFilled.isEmpty()
                        && !birthday.isEmpty())
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
        Log.i("test", "We are into init()")
        binding.apply {
            Log.i("test", "userListApp: ${userListApp.size}")
            recyclerConteiner.layoutManager = GridLayoutManager(this@MainActivity, 1)
            recyclerConteiner.adapter = adapter
            if (isFileExists(File(filesDir, FILE_NAME)) && !isFileEmpty(File(filesDir, FILE_NAME))) {
                openFile2()
                userListApp.forEach {
                    Log.i("test", "userListApp: ${userListApp.size}")
                    adapter.addUser(it)
                }
            } else {
                File(filesDir, FILE_NAME).createNewFile()
            }
            button.setOnClickListener {
                if (index>8) index = 0
                val user = User(imageIdList[index],
                    editTextPersonName.text.toString(),
                    editTextPersonName2.text.toString(),
                    editTextPhone.text.toString(),
                    editTextNumber.text.toString(),
                    etDate.text.toString()
                    )
                adapter.addUser(user)
                userListApp.add(user)
                saveFile()
                index++
                it.hideKeyboard()
                editTextPersonName.text = null
                editTextPersonName2.text = null
                editTextPhone.text = null
                editTextNumber.text = null
                etDate.text = null
            }
            button2.setOnClickListener {
                val intent = Intent(it.context, MainActivity2::class.java)
                //intent.putParcelableArrayListExtra("user", userList)
                startActivity(intent)
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

    private fun createSimpleDialog() {
        val builder = AlertDialog.Builder(this)
                with(builder){
        setTitle("Attention!")
        setMessage("You have saved users. Do you want continue or delete list?")
        setNegativeButton("Delete"){
            dialogInterface, i -> File(filesDir, FILE_NAME).delete()
            Log.i("test", "removing file")
            init()
        }
        setPositiveButton("Continue") {
            dialogInterface, i -> setCancelable(true)
            Log.i("test", "continue with file")
            init()
        }
                    show()
                }
    }

    /*
    Open: example with wrappers
    private fun ActivityMainBinding.openFile1() {
    val file = File(filesDir, FILE_NAME)
    val inputStream = FileInputStream(file)
    val reader = InputStreamReader(inputStream)
    val bufferedReader = BufferedReader(reader)
    val data = bufferedReader.use {
    it.readLines().joinToString(separator = "\n")
    }
    .setText(data)
    }
    private fun ActivityMainBinding.bindOpenButtonListener() {
    openButton.setOnClickListener {
    try {
    openFile1()
    } catch (e: Exception) {
    showError(R.string.cant_open_file)
    }
    }
    }
    */

}
/*
DESCRIPTION
--------------------------------------------------------------------------------------------------
*/

/*
Задание 7.1
Доработайте проект из предыдущего ДЗ таким образом:
1) При вводе данных в поле и нажатии на кнопку, запишите введенный данные в файл во внутренней памяти. ++
2) Данные в список должны считываться из файла. ++
3) По нажатию на кнопку “Удалить” элемент должен удаляться из файла и из recyclerView ++
4) При открытии приложения реализовать логику проверки файла. ++
а) Если файл существует, показать диалог с выбором для пользователя “оставить данные или очистить их”.
б) Если пользователь выбирает “оставить файл”, пользователь должен видеть список с ранее введенными элементами.
3) Если выбирает “очистить”, очистите файл.
Задание 7.2
При нажатии на “Next Screen”, запишите данные из файла в новый файл во внешней памяти.
При  нажатии на “Next Screen” реализовать логику проверки файла во внешней памяти.
Если файл существует, показать диалог с выбором для пользователя “оставить данные или очистить их”.
Если пользователь выбирает “оставить данные” новые данные должны быть записаны после существующих.
Если выбирает “очистить”, очистите файл.
*/