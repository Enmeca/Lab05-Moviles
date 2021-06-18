package com.cabegaira.lab05

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudent : AppCompatActivity() {
    //private var btn : Button? = null
    internal var dbHelper = DatabaseHelper(this)

    var et_id : EditText ? = null
    var et_name : EditText ? = null
    var et_apellidos : EditText ? = null
    var et_edad : EditText ? = null
     var btn : Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_student_activity)
         et_id = findViewById(R.id.idTxt) as EditText
         et_name = findViewById(R.id.nameTxt) as EditText
         et_apellidos = findViewById(R.id.apellidosTxt) as EditText
         et_edad = findViewById(R.id.edadTxt) as EditText
         btn = findViewById(R.id.insertBtn) as Button


        btn!!.setOnClickListener{
            insertFunction()
        }

    }

    fun insertFunction(){
        try {
            dbHelper.insertStudent(et_name!!.text.toString(),
                et_apellidos!!.text.toString(), et_edad!!.text.toString().toInt())
            val i = Intent(this, CRUDStudent::class.java)
            Toast.makeText(this, "Usuario insertado", Toast.LENGTH_SHORT).show()
            startActivity(i)
        }catch (e: Exception){
            e.printStackTrace()
            // showToast(e.message.toString())
        }
    }

    fun clearEditTexts(){
        et_edad!!.setText("")
        et_apellidos!!.setText("")
        et_name!!.setText("")
        et_id!!.setText("")
    }
}