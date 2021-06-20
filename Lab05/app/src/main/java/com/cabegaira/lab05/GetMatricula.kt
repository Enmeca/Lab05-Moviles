package com.cabegaira.lab05

import android.database.Cursor
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView


import android.content.Intent
import android.graphics.Canvas
import android.view.View

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*
import kotlin.collections.ArrayList

class GetMatricula : AppCompatActivity() {
    var db: DatabaseHelper? = null
    private lateinit var coursesList : ArrayList<Courses>
    lateinit var list: RecyclerView
    lateinit var adapter : RecyclerView_Adapter_Courses

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.matricula_list)
        val navView: NavigationView = findViewById(R.id.nav_view)

        list = findViewById(R.id.matricula_list)
        list.layoutManager = LinearLayoutManager(list.context)
        list.setHasFixedSize(true)

        val bundle = intent.extras

        var l =  bundle!!.getSerializable("student") as Int

        listCourses(l)
    }


    fun listCourses(student:Int){

        db = DatabaseHelper(this)

        var CoursesCursor : Cursor? = db!!.getQuery("SELECT ID_COURSE, DESCRIPTION, CREDITS "+
                "FROM TABLE_ENROLLMENT, TABLE_COURSE "+
                "WHERE FK_ID_STUDENT ="+ student +" AND FK_ID_COURSE = ID_COURSE")


        var studentsSize : Int = CoursesCursor!!.count


        coursesList = ArrayList<Courses>()
        do {
            val id = CoursesCursor.getInt(0)
            val desc = CoursesCursor.getString(1)
            val cred = CoursesCursor.getInt(2)

            coursesList.add(Courses(id,desc,cred))
        }while(CoursesCursor.moveToNext())

        adapter = RecyclerView_Adapter_Courses(coursesList)
        list.adapter = adapter

    }

}