package com.example.homework_5

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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_5.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding
    private val adapter: UserAdapter = UserAdapter()
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
    private var index = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        init()
    }

// functions
//--------------------------------------------------------------------------------------------------

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
            // получаею текст со всех полей
            val nameFilled = binding.editTextPersonName.text.toString()
            val surnameFilled = binding.editTextPersonName2.text.toString()
            val phoneFilled = binding.editTextPhone.text.toString()
            val ageFilled = binding.editTextNumber.text.toString()
            val birthday = binding.etDate.text.toString()

            // проверяю пусты поля или нет
            with(binding){
                button.setEnabled(!nameFilled.isEmpty()
                        && !surnameFilled.isEmpty()
                        && !phoneFilled.isEmpty()
                        && !ageFilled.isEmpty()
                        && !birthday.isEmpty())
                button2.setEnabled(!nameFilled.isEmpty()
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
            recyclerConteiner.layoutManager = GridLayoutManager(this@MainActivity, 1) // изменение количества столбцов в ресайклере
            recyclerConteiner.adapter = adapter
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
                index++
                it.hideKeyboard()
                editTextPersonName.text = null
                editTextPersonName2.text = null
                editTextPhone.text = null
                editTextNumber.text = null
                etDate.text = null
            }
            button2.setOnClickListener { v ->

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
}
// DESCRIPTION
//--------------------------------------------------------------------------------------------------

/*
Задание 6.3
На одном из экранов реализуйте боковое меню. Пункты меню придумайте сами,
при нажатии на пункт либо открывайте соответствующий экран или диалог покажите.
Тут по вашему желанию.
*/