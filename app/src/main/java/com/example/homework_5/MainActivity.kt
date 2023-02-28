package com.example.homework_5

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.homework_5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // получаею текст со всех полей
            val nameFilled = binding.editTextTextPersonName.text.toString()
            val surnameFilled = binding.editTextTextPersonName2.text.toString()
            val phoneFilled = binding.editTextPhone.text.toString()
            val ageFilled = binding.editTextNumber.text.toString()
            val birthday = binding.tvBirthday.text.toString()

            // проверяю пусты поля или нет
            binding.button.setEnabled(!nameFilled.isEmpty() && !surnameFilled.isEmpty() && !phoneFilled.isEmpty() && !ageFilled.isEmpty())
        }

        override fun afterTextChanged(s: Editable) {}
    }
}

/*
Задание 5.1
Используя логику из задания 1 ДЗ 3 реализовать логику записи
введенных данных в список по нажатию на кнопку.
Добавить поле “дата рождения”, выбор даты должен быть реализован через календарь.
Используйте календарь из material Design
Реализуйте дизайн по гайдлайнам Material Design ( по вашему вкусу).
В идеале реализовать дизайн систему со стилями, шрифтами, размерами элементов,
также использовать элементы material design (https://m3.material.io/))
Полученный список отобразить в виде списка RecyclerView снизу от полей.
Предусмотреть логику обновления списка, при добавлении экрана -------------------- какого экрана? куда добавлять?
Добавить кнопку “удалить” к элементам списка.
При нажатии на кнопку удалить элемент списка должен удалиться из RecyclerView.
Добавить к каждому элементу разное изображение (из ресурсов).
Можно реализовать через рандом или через when или через switch.
Логику придумайте сами (например если возраст от 0до10 то одна картинка,
от 11 до 20 другая и т.д. Или в зависимости от первой буквы имени).
Подберите картинки разных цветов
 */
//----------------------------------------------------------------------------------------
/*
Задание 3.1
Реализовать данный дизайн (картинка №1)
Дизайн должен быть реализован атрибутами в разметки (не использовать стили)
Каждое поле должно содержать подсказку, какую информацию необходимо вводить
Поле Name, Surname должны позволять вводить только буквы и текст должен занимать МАКСИМ 1 строку
Поле номер телефона должно позволять вводить только цифры и спецсимволы
Поле Age должно позволять вводить только цифры и не больше 2 символов
При открытии окна, кнопка должна быть не активна (по нажатию на нее ничего не происходит)
После заполнения ВСЕХ полей, кнопка должен стать активной (изменить свой цвет и стать кликабельно).
Если одно из полей очищается, кнопка снова должна стать неактивной (цвет и кликабельность)
При нажатии на кнопку, на экране под кнопкой должен отобразиться введенный текст ( в одном TextView)
 */