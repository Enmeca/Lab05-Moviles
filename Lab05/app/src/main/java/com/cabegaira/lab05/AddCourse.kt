package com.cabegaira.lab05


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddCourse : AppCompatActivity() {
    internal var dbHelper = DatabaseHelper(this)
    var et_id : EditText ? = null
    var et_desc : EditText ? = null
    var et_cred : EditText ? = null
    var insertBtn: Button ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_course_activity)

        et_id = findViewById(R.id.idTxt) as EditText
        et_desc = findViewById(R.id.desctxt) as EditText
        et_cred = findViewById(R.id.credTxt) as EditText
        insertBtn = findViewById(R.id.insertBtn) as Button

        //(activity as? AppCompatActivity)?.supportActionBar?.title = "title"
        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = "Añadir Curso"

        insertBtn!!.setOnClickListener {
            insertFunction()
        }
    }


    fun insertFunction(){
        try {
            var id = et_id!!.text;
            val desc = et_desc!!.text
            val cred = et_cred!!.text
            dbHelper.insertCourse(
                id.toString().toInt(),
                desc.toString(),
                cred.toString().toInt())
            val i = Intent(this, CRUDCourse::class.java)
            Toast.makeText(this, "Curso Insertado", Toast.LENGTH_SHORT).show()
            startActivity(i)
            finish()
        }catch (e: Exception){
            e.printStackTrace()
            // showToast(e.message.toString())
        }
    }

    fun clearEditTexts(){
        et_desc!!.setText("")
        et_cred!!.setText("")
        et_id!!.setText("")
    }
}