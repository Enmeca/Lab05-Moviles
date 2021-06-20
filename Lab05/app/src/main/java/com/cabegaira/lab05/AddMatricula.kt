package com.cabegaira.lab05

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddMatricula : AppCompatActivity() {

    internal var dbHelper = DatabaseHelper(this)
    var et_id : Int ? = 0
    var et_desc : EditText ? = null
    var et_cred : EditText ? = null
    var insertBtn: Button ? = null
    private lateinit var spinnerStudents: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_matricula_activity)

        val bundle = intent.extras

        var l =  bundle!!.getSerializable("Students") as ArrayList<String>
        var idC =  bundle!!.getSerializable("course") as Int

        spinnerStudents = findViewById<AutoCompleteTextView>(R.id.spinnerStudents)
        et_id = idC

        insertBtn = findViewById<Button>(R.id.BtnInsert) as Button?


        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, l)
        spinnerStudents.setAdapter(adapter)




        insertBtn!!.setOnClickListener {
            insertFunction()
        }
    }


    fun insertFunction(){
        try {
            var id = et_id
            var desc = spinnerStudents!!.text.toString()

            dbHelper.insertMatricula(
                desc.toInt(),
                id.toString().toInt()
                )
           // val i = Intent(this, Matricula::class.java)
            Toast.makeText(this, "Matricula Agregada", Toast.LENGTH_SHORT).show()
            //startActivity(i)
            finish()
        }catch (e: Exception){
            e.printStackTrace()
            // showToast(e.message.toString())
        }
    }


}