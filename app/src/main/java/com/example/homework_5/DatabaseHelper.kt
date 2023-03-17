package com.example.homework_5

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_PERSON TEXT,"+
                    "$COLUMN_SURNAME TEXT,"+
                    "$COLUMN_PHONE INTEGER,"+
                    "$COLUMN_AGE INTEGER,"+
                    "$COLUMN_DATE TEXT,"+
                    "$COLUMN_IMAGE INTEGER);"
        )

//        db?.execSQL(
//            "INSERT INTO $TABLE_NAME" +
//                    "($COLUMN_PERSON,$COLUMN_SURNAME,$COLUMN_PHONE,$COLUMN_AGE, $COLUMN_DATE)" +
//                    "VALUES('Kolder', 'Asfffg', 214324345, 43, '56.76.87')"
//        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
//        onCreate(db)
    }

    fun getUser(context: Context):ArrayList<User> {
        val qry = "Select * From $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val userList = ArrayList<User>()

        if (cursor.count == 0) {

        } else {
            while (cursor.moveToNext()) {
                val user = User(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PERSON)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AGE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
                )
                userList.add(user)
            }
        }
        return userList
    }

    companion object {
        const val DATABASE_NAME = "users.db"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "users"
        const val COLUMN_ID = "_id"
        const val COLUMN_PERSON = "name"
        const val COLUMN_SURNAME = "surname"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_DATE = "birthday"
        const val COLUMN_AGE = "age"
        const val COLUMN_IMAGE = "image"
    }

}