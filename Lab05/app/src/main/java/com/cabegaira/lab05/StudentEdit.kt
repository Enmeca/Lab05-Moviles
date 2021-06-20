package com.cabegaira.lab05

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StudentEdit : AppCompatActivity() {

    //private var btn : Button? = null
    internal var dbHelper = DatabaseHelper(this)
    var dato: Students? = null
    var ed_id : EditText? = null
    var ed_name : EditText? = null
    var ed_apellidos : EditText? = null
    var ed_edad : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_edit_activity)
        dato = intent.getSerializableExtra("dato") as Students?
        ed_id = findViewById(R.id.editId) as EditText
        ed_id!!.setText(dato!!.id)
        ed_name = findViewById(R.id.editName) as EditText
        ed_name!!.setText(dato!!.name)
        ed_apellidos = findViewById(R.id.aeditApellidos) as EditText
        ed_apellidos!!.setText(dato!!.lastname)
        ed_edad = findViewById(R.id.editEdad) as EditText
        ed_edad!!.setText(dato!!.age)
        var updateBtn = findViewById(R.id.updateBtn) as Button


        updateBtn!!.setOnClickListener{
            updateFunction()
        }

    }

    fun updateFunction(){
        try {
            dbHelper.updateStudent(ed_id!!.text.toString(),ed_name!!.text.toString(),
                ed_apellidos!!.text.toString(), ed_edad!!.text.toString().toInt())
            //val i = Intent(this, CRUDStudent::class.java)
            Toast.makeText(this, "Usuario Actualizado", Toast.LENGTH_SHORT).show()
            //startActivity(i)
            finish()
        }catch (e: Exception){
            e.printStackTrace()
            // showToast(e.message.toString())
        }
    }

}