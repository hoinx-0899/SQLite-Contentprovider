package com.sun.sqlite_contentprovider

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ManagerSqlite(val mContext: Context) {
    private val DATA_NAME = "trieuphu_games"
    private val TAG = ManagerSqlite::class.java.simpleName
    private var pathRoot: String = Environment.getDataDirectory().toString() + File.separator +
            "data" + File.separator +
            mContext.packageName +
            File.separator + "database"
    private lateinit var managerSql: SQLiteDatabase

    init {
        inits()
    }

    private fun openDatabase() {
        Log.d(TAG, "open")
        if (!::managerSql.isInitialized) {
            Log.d(TAG, "open")
            managerSql = SQLiteDatabase.openDatabase(
                    pathRoot
                            + File.separator +
                            DATA_NAME, null,
                    SQLiteDatabase.OPEN_READWRITE
            )


        }

    }

    private fun closeDatabase() {
        if (::managerSql.isInitialized) {
            if (managerSql.isOpen) {
                Log.d(TAG, "CLOSE")
                managerSql.close()
            }

        }
    }

    @SuppressLint("Recycle")
    fun get15QuestionByLevel() {
        openDatabase()
        val sql = "SELECT * FROM ( SELECT * FROM question ORDER BY RANDOM() ) GROUP BY level ORDER BY level ASC"
        val sle = arrayOf("level", "id", "ra")
        val cursor = managerSql.rawQuery(sql, null) ?: return
        val name = cursor.columnNames
        for (s in name) {
            Log.d("ManagerSqlite", "name: $s")
        }
        val indexAsk = cursor.getColumnIndex("ask") //0
        val indexRa = cursor.getColumnIndex("ra") //1
        val indexRb = cursor.getColumnIndex("rb") //2
        val indexRc = cursor.getColumnIndex("rc") //3
        val indexRd = cursor.getColumnIndex("rd")//4
        val indexlevel = cursor.getColumnIndex("level")//5
        while (cursor.moveToNext()) {
            val ask = cursor.getString(indexAsk)
            val ra = cursor.getString(indexRa)
            val rb = cursor.getString(indexRb)
            val rc = cursor.getString(indexRc)
            val rd = cursor.getString(indexRd)
            val level = cursor.getInt(indexlevel)
            Log.d(TAG, "ask: $ask")
            Log.d(TAG, "ra: $ra")
            Log.d(TAG, "rb: $rb")
            Log.d(TAG, "rc: $rc")
            Log.d(TAG, "rd: $rd")
            Log.d(TAG, "level: $level")
            Log.d(TAG, "=================")
        }
        closeDatabase()
    }

    @SuppressLint("Recycle")
    fun getAllQuestion() {
        openDatabase()
        val sql = "SELECT * FROM question"
        val cursor = managerSql.rawQuery(sql, null) ?: return
        //Lay cau hoi theo id
        // val cursor = managerSql.query("question", null, "id =? or id=?", arrayOf<String>("1", "4"), null, null, null)
        while (cursor.moveToNext()) {
            val ask = cursor.getString(0)
            val ra = cursor.getString(1)
            val rb = cursor.getString(2)
            val rc = cursor.getString(3)
            val rd = cursor.getString(4)
            val level = cursor.getInt(5)
            Log.d(TAG, "ask: $ask")
            Log.d(TAG, "ra: $ra")
            Log.d(TAG, "rb: $rb")
            Log.d(TAG, "rc: $rc")
            Log.d(TAG, "rd: $rd")
            Log.d(TAG, "level: $level")
            Log.d(TAG, "=================")
        }

        closeDatabase()
    }

    fun deleteById() {
        openDatabase()
        val kq = managerSql.delete("question", "id=? and id=?", arrayOf<String>("10", "15"))
        if (kq > 0) {
            Log.d(TAG, "delete success")
        } else {
            Log.d(TAG, "delete fail")
        }
        closeDatabase()
    }

    fun updateQuestion() {
        openDatabase()
        val contentValues = ContentValues()
        contentValues.put("ask", "Hom nay troi dep khong")
        contentValues.put("ra", "Dep")
        val kq = managerSql.update("question", contentValues, "id=?", arrayOf<String>("1"))
        if (kq > 0) {
            Log.d(TAG, "update success")
        } else {
            Log.d(TAG, "update fail")
        }
        closeDatabase()
    }

    fun addNewQuestion() {
        openDatabase()
        val contentValues = ContentValues()
        contentValues.put("ask", "Hom nay troi dep khong")
        contentValues.put("ra", "Dep")
        contentValues.put("rb", "Khong")
        contentValues.put("rc", "Gan Dep")
        contentValues.put("rd", "Rat dep")
        contentValues.put("level", "5")
        val kq = managerSql.insert("question", null, contentValues)
        if (kq > 0) {
            Log.d(TAG, "Insert success")
        } else {
            Log.d(TAG, "Insert fail")
        }
        closeDatabase()
    }

    private fun inits() {
        val file = File(
                pathRoot + File.separator
                        + DATA_NAME
        )
        if (file.exists()) {
            return
        }
        file.parentFile?.mkdir()
        try {
            val inputStream = mContext.assets.open(DATA_NAME)
            val out = FileOutputStream(file)
            val b = ByteArray(1024)
            var le = inputStream.read(b)
            while (le >= 0) {
                out.write(b, 0, le)
                le = inputStream.read(b)
            }
            inputStream.close()
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}