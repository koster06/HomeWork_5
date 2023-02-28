package com.example.homework_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
Предусмотреть логику обновления списка, при добавлении экрана
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