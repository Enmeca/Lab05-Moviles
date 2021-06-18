package com.cabegaira.lab05

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_student_activity)

        var et_id = findViewById(R.id.idTxt) as EditText
        var et_name = findViewById(R.id.nameTxt) as EditText
        var et_apellidos = findViewById(R.id.apellidosTxt) as EditText
        var et_edad = findViewById(R.id.edadTxt) as EditText

        //btn_create.setOnClickListener{
            /*var name = et_name.text;
            val password = et_password.text
            val user = et_user.text
            users.addUser(User(user.toString().lowercase(), password.toString(), name.toString(),0))
            Toast.makeText(this, "El usuario se ha registrado", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Login::class.java)
            intent.putExtra("MESSAGE", "msg")
            startActivity(intent)*/
        }

    }
