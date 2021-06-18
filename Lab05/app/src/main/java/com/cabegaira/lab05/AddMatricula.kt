package com.cabegaira.lab05

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddMatricula : AppCompatActivity() {

    internal var dbHelper = DatabaseHelper(this)
    var et_id : EditText ? = null
    var et_desc : EditText ? = null
    var et_cred : EditText ? = null
    private lateinit var spinnerStudents: AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_matricula_activity)

        val bundle = intent.extras

        val l =  bundle!!.getSerializable("Students") as ArrayList<String>

        spinnerStudents = findViewById<AutoCompleteTextView>(R.id.spinnerStudents)
        et_id = findViewById(R.id.idTxt) as EditText
        et_desc = findViewById(R.id.desctxt) as EditText
        et_cred = findViewById(R.id.credTxt) as EditText
        var insertBtn = findViewById(R.id.insertBtn) as Button


        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, l)
        spinnerStudents.setAdapter(adapter)




        insertBtn.setOnClickListener {
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