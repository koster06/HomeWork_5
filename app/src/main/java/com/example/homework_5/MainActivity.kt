package com.example.homework_5

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework_5.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var userList = kotlin.collections.ArrayList<UserNext>()
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
    private var index = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        adapter = UserAdapter(object : AdapterListener{
            override fun removeUser(user: User) {      //this function more safe, cos I didn't have any issues with this
                val indexToDelete = adapter.userList.indexOfFirst { it.id == user.id }
                adapter.userList.removeAt(indexToDelete)
                adapter.notifyDataSetChanged()
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
            button.setOnClickListener {
                if (index>8) index = 0
                val user = User(imageIdList[index],
                    editTextPersonName.text.toString(),
                    editTextPersonName2.text.toString(),
                    editTextPhone.text.toString(),
                    editTextNumber.text.toString(),
                    etDate.text.toString()
                    )
                val userNext = UserNext(
                    imageIdList[index],
                    editTextPersonName.text.toString(),
                )
                userList?.add(userNext)
                adapter.addUser(user)
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
                intent.putParcelableArrayListExtra("user", userList)
                startActivity(intent)
            }
        }
    }
}
// DESCRIPTION
//--------------------------------------------------------------------------------------------------

/*

*/