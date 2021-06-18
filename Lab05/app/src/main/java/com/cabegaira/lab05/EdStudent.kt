package com.cabegaira.lab05

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EdStudent : AppCompatActivity() {

    //private var btn : Button? = null
    internal var dbHelper = DatabaseHelper(this)
    var dato: Students? = null
    var et_id : EditText? = null
    var et_name : EditText? = null
    var et_apellidos : EditText? = null
    var et_edad : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_edit_activity)
        dato = intent.getSerializableExtra("dato") as Students?
        et_id = findViewById(R.id.editId) as EditText
        et_id!!.setText(dato!!.id)
        et_name = findViewById(R.id.editName) as EditText
        et_name!!.setText(dato!!.name)
        et_apellidos = findViewById(R.id.aeditApellidos) as EditText
        et_apellidos!!.setText(dato!!.lastname)
        et_edad = findViewById(R.id.editEdad) as EditText
        et_edad!!.setText(dato!!.age)
        var updateBtn = findViewById(R.id.updateBtn) as Button


        updateBtn!!.setOnClickListener{
            updateFunction()
        }

    }

    fun updateFunction(){
        try {
            dbHelper.updateStudent(et_id!!.text.toString().toInt(),et_name!!.text.toString(),
                et_apellidos!!.text.toString(), et_edad!!.text.toString().toInt())
            val i = Intent(this, CRUDStudent::class.java)
            Toast.makeText(this, "Usuario Actualizado", Toast.LENGTH_SHORT).show()
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