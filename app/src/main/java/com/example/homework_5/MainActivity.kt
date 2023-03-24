package com.example.homework_5

import adress.UserAddress
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.homework_5.databinding.ActivityMainBinding
import interfaces.AdapterListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import user.User
import user.UserAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var userList = kotlin.collections.ArrayList<User>()
    lateinit var adapter : UserAdapter
    private val imageIdList = listOf ( //и заполнения xml разметки recyclerView
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
    lateinit var db: AppDB
    lateinit var userAddress : UserAddress
    private val addressList = listOf(
        UserAddress(1, 33, "Мира пр-т"),
        UserAddress(2,5, "Невский пр-т"),
        UserAddress(3,7, "Петербургская ул.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Room.databaseBuilder(applicationContext, AppDB::class.java, "users").build()
        //val users: List<User> = db.userDao().getAll()
//        lifecycleScope.launch(Dispatchers.IO) {
//            db.addressDao().insertAllUsersAddresses(addressList)//(addressList)
//        }
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

        adapter = UserAdapter(object : AdapterListener {
            override fun removeUser(user: User) {      //this function more safe, cos I didn't have any issues with this
                val indexToDelete = adapter.userList.indexOfFirst { it.id == user.id }
                adapter.userList.removeAt(indexToDelete)
                adapter.notifyDataSetChanged()
                lifecycleScope.launch(Dispatchers.IO) {

                    }
                }
        })
        init()
    }

// functions
//--------------------------------------------------------------------------------------------------

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
        binding.apply {
            recyclerConteiner.layoutManager = GridLayoutManager(this@MainActivity, 1)
            recyclerConteiner.adapter = adapter
            lifecycleScope.launch(Dispatchers.IO) {
                userList = db.userDao().getAllUsers() as ArrayList<User>
                if (userList.isNotEmpty()) {
                    userList.forEach {
                        adapter.addUser(it)
                    }
                }
            }
            button.setOnClickListener {
                val user = User(0,
                    editTextPersonName.text.toString(),
                    editTextPersonName2.text.toString(),
                    editTextPhone.text.toString(),
                    editTextNumber.text.toString(),
                    etDate.text.toString()
                    )
//                val userNext = UserNext(
//                    imageIdList[index.toInt()],
//                    editTextPersonName.text.toString(),
//                )
//                userList.add(userNext)
                adapter.addUser(user)
                lifecycleScope.launch(Dispatchers.IO) {
                    db.userDao().insertUser(user)
                }
                it.hideKeyboard()
                clearFields()
            }
            button2.setOnClickListener {
//                val intent = Intent(it.context, MainActivity2::class.java)
//                intent.putParcelableArrayListExtra("user", userList)
//                startActivity(intent)
            }
        }
    }

    private fun clearFields(){
        with(binding) {
            editTextPersonName.text.clear()
            editTextPersonName2.text.clear()
            editTextPhone.text.clear()
            editTextNumber.text.clear()
            etDate.text = null
        }
    }


}
// DESCRIPTION
//--------------------------------------------------------------------------------------------------

/*
Задание 3 Room
1). Создать новую БД используя Room. ++
2). В Бд должна быть таблица для записи данных о пользователе ++
3). Добавить еще одну таблицу адрес пользователя, которая должна быть связана внешними ключами с таблицами адрес и пользователь ++
4). Написать select запросы для каждой таблицы (поиск всех элементов, поиск по условию,
поиск с промежутком, поиск определенного количества и т.д) Минимум 4 запроса на каждую таблицу
5). Написать запросы для удаления и изменения данных (для одного и для всех данных).
6). Написать запрос для вставки списка элементов и для вставки одного элемента.
7). Заполнить таблицу адрес программно
8). На экране реализовать UI для отображения списка адресов.
 Реализовать логику выбора адреса пользователем (По нажатию на элемент выбор можно записывать в
 SharedPreferences или в отдельную таблицу или в файл по вашему выбору).
Добавить кнопку сохранить, при нажатии на которую в таблицу адрес пользователя будет записываться
данные пользователя и адрес, который он выбрал.
 После сохранения данных перенаправьте пользователя на 3-й экран, где отобразить список пользователей и их адреса.
 Добавьте кнопку, при нажатии на которую, будут оставаться только пользователи, у которых есть адрес
 Добавьте поле ввода, при вводе должен осуществляться поиск по списку пользователей.
 Логику поиска придумайте сами (поиск после нажатия на кнопку или при вводе в поле или другое)

*/