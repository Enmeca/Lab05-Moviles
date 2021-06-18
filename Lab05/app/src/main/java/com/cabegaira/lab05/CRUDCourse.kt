package com.cabegaira.lab05

import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView


import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.view.View

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*
import kotlin.collections.ArrayList

class CRUDCourse : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    var db: DatabaseHelper? = null
    private lateinit var coursesList : ArrayList<Courses>
    lateinit var list: RecyclerView
    lateinit var adapter : RecyclerView_Adapter_Courses
    lateinit var fab: View

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.courses_list)
        val navView: NavigationView = findViewById(R.id.nav_view)

        list = findViewById(R.id.courses_list)
        list.layoutManager = LinearLayoutManager(list.context)
        list.setHasFixedSize(true)

        navView.setNavigationItemSelectedListener(this)



        fab = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val i = Intent(this@CRUDCourse, AddCourse::class.java)
            startActivity(i)
        }

        listCourses()

    }
    fun listCourses(){

        db = DatabaseHelper(this@CRUDCourse)

        var studentsCursor : Cursor? = db!!.getQuery("SELECT * from TABLE_COURSE")
        var studentsSize : Int = studentsCursor!!.count


        coursesList = ArrayList<Courses>()
        while (studentsCursor.moveToNext()){
            val id = studentsCursor.getInt(0)
            val desc = studentsCursor.getString(1)
            val cred = studentsCursor.getInt(2)

            coursesList.add(Courses(id,desc,cred))
        }

        adapter = RecyclerView_Adapter_Courses(coursesList)
        list.adapter = adapter



    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_students -> {
                Toast.makeText(this, "teste", Toast.LENGTH_SHORT).show()
                val i = Intent(this, CRUDStudent::class.java)
                startActivity(i)
            }
            R.id.nav_courses -> {
                Toast.makeText(this, "teste", Toast.LENGTH_SHORT).show()
                val i = Intent(this, CRUDStudent::class.java)
                startActivity(i)
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show()
                val i = Intent(this, Login::class.java)
                startActivity(i)
                finish()
            }
        }
        return true
    }

}