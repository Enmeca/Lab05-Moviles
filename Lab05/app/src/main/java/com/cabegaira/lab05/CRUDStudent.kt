package com.cabegaira.lab05

import android.database.Cursor
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class CRUDStudent : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    var db: DatabaseHelper? = null
    private lateinit var studentsList : ArrayList<Students>
    lateinit var list: RecyclerView
    lateinit var adapter : RecyclerView_Adapter_Students


    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.students_list)
        val navView: NavigationView = findViewById(R.id.nav_view)

        list = findViewById(R.id.students_list)
        list.layoutManager = LinearLayoutManager(list.context)
        list.setHasFixedSize(true)

        navView.setNavigationItemSelectedListener(this)


        listStudents()
    }
    fun listStudents(){

        db = DatabaseHelper(this)

        var studentsCursor : Cursor? = db!!.getQuery("SELECT * from TABLE_STUDENT")
        var studentsSize : Int = studentsCursor!!.count


        studentsList = ArrayList<Students>()
        while (studentsCursor.moveToNext()){
            val id = studentsCursor.getInt(0)
            val name = studentsCursor.getString(1)
            val lastName = studentsCursor.getString(2)
            val age = studentsCursor.getInt(3)

            studentsList.add(Students(id,name,lastName,age))
        }

        adapter = RecyclerView_Adapter_Students(studentsList)
        list.adapter = adapter



    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}