package com.cabegaira.lab05

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditCourse : AppCompatActivity() {
    internal var dbHelper = DatabaseHelper(this)
    var dato: Courses? = null
    var ed_id : EditText? = null
    var ed_desc : EditText? = null
    var ed_cred : EditText? = null

    var b : Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.course_edit_activity)

        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = "Editar Curso"

        dato = intent.getSerializableExtra("dato") as Courses?

        ed_id = findViewById(R.id.editIdC) as EditText
        ed_id!!.setText(dato!!.id.toString())
        ed_desc = findViewById(R.id.editDesc) as EditText
        ed_desc!!.setText(dato!!.description)
        ed_cred = findViewById(R.id.editCreditos) as EditText
        ed_cred!!.setText(dato!!.credits.toString())

        b = findViewById(R.id.updBtn) as Button


        b!!.setOnClickListener{
            updateFunction()
        }


    }
    fun updateFunction(){
        try {
            dbHelper.updateCourse(
                ed_id!!.text.toString().toInt(),
                ed_desc!!.text.toString(),
                ed_cred!!.text.toString().toInt()
            )
            Toast.makeText(this, "Usuario Actualizado", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        }catch (e: Exception){
            e.printStackTrace()
            // showToast(e.message.toString())
        }
    }
}