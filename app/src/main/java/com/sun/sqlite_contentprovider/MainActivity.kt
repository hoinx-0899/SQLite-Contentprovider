package com.sun.sqlite_contentprovider

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val managerSqlite = ManagerSqlite(this)
        managerSqlite.getAllQuestion()
        //managerSqlite.get15QuestionByLevel()
        //managerSqlite.updateQuestion()
        //managerSqlite.addNewQuestion()
        //managerSqlite.deleteById()

        if (isStoragePermissionGranted()) {
            readSms()
            return
        }
        PermissionUtil.requestStoragePermission(this)
    }

    private fun readSms() {
        val uri = Uri.parse("content://sms/sent")
        val cursor = contentResolver.query(uri, null, null, null, null)
        val vtPhone = cursor!!.getColumnIndex("address")
        val vtTime = cursor.getColumnIndex("date")
        val vtBody = cursor.getColumnIndex("body")
        while (cursor.moveToNext()) {
            val phone = cursor.getString(vtPhone)
            val time = cursor.getString(vtTime)
            val body = cursor.getString(vtBody)
            Log.d("READ_SMS", phone + "\n" + time + "\n" + body)
        }
        cursor.close()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            RequestCode.PERMISSION_SMS -> {
                if (PermissionUtil.isGrantResultsGranted(grantResults)) {
                    readSms()
                } else {
                    Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun isStoragePermissionGranted(): Boolean {
        return PermissionUtil.isPermissionsGranted(this, PermissionUtil.storagePermissions)
    }
}
