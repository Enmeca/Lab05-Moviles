package com.cabegaira.lab05


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddCourse : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_course_activity)

        var et_id = findViewById(R.id.idTxt) as EditText
        var et_desc = findViewById(R.id.desctxt) as EditText
        var et_cred = findViewById(R.id.credTxt) as EditText
        var insertBtn = findViewById(R.id.insertBtn) as Button

        insertBtn.setOnClickListener {
            var id = et_id.text;
            val desc = et_desc.text
            val cred = et_cred.text
            //users.addUser(User(user.toString().lowercase(), password.toString(), name.toString(),0))
            Toast.makeText(this, "El usuario se ha registrado", Toast.LENGTH_LONG).show()

/*            val intent = Intent(this, Login::class.java)
            intent.putExtra("MESSAGE", "msg")
            startActivity(intent)*/
        }
    }
}