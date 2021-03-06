package com.cabegaira.lab05

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditSt : AppCompatActivity() {
    internal var dbHelper = DatabaseHelper(this)
    var dato: Students? = null
    var ed_id : EditText? = null
    var ed_name : EditText? = null
    var ed_apellidos : EditText? = null
    var ed_edad : EditText? = null
    var b : Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_edit_activity)
        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = "Editar Estudiante"

       dato = intent.getSerializableExtra("dato") as Students?
//        Log.d("asd",dato!!.name)
        ed_id = findViewById(R.id.editId) as EditText
        ed_id!!.setText(dato!!.id.toString())
        ed_name = findViewById(R.id.editName) as EditText
        ed_name!!.setText(dato!!.name)
        ed_apellidos = findViewById(R.id.aeditApellidos) as EditText
        ed_apellidos!!.setText(dato!!.lastname)
        ed_edad = findViewById(R.id.editEdad) as EditText
        ed_edad!!.setText(dato!!.age.toString())

        b = findViewById(R.id.ubtn) as Button


       b!!.setOnClickListener{
            updateFunction()
        }


    }
    fun updateFunction(){
        try {
            dbHelper.updateStudent(ed_id!!.text.toString(),ed_name!!.text.toString(),
                ed_apellidos!!.text.toString(), ed_edad!!.text.toString().toInt())
           // val i = Intent(this, CRUDStudent::class.java)
            Toast.makeText(this, "Usuario Actualizado", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        }catch (e: Exception){
            e.printStackTrace()
            // showToast(e.message.toString())
        }
    }
}