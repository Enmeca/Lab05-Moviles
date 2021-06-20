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
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*
import kotlin.collections.ArrayList

class Matricula : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    var db: DatabaseHelper? = null
    private lateinit var coursesList : ArrayList<Courses>
    private lateinit var studentsList : ArrayList<String>

    lateinit var list: RecyclerView
    lateinit var adapter : RecyclerView_Adapter_Enrollment
    lateinit var fab: View

    lateinit var course:Courses
    var position: Int = 0
    internal var dbHelper = DatabaseHelper(this)


    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.courses_list)


        list = findViewById(R.id.courses_list)
        list.layoutManager = LinearLayoutManager(list.context)
        list.setHasFixedSize(true)



        val navView: NavigationView = findViewById(R.id.nav_view)

        /*if(l.admin==0){
            navView.menu.removeItem(R.id.nav_list)
        }
        if(l.admin==1){
           // navView.menu.removeItem(R.id.nav)
        }*/

        navView.setNavigationItemSelectedListener(this)

        fab = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val i = Intent(this, AddCourse::class.java)
            startActivity(i)
        }

        listCourses()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition: Int = viewHolder.adapterPosition
                val toPosition: Int = target.adapterPosition



                list.adapter?.notifyItemMoved(fromPosition, toPosition)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition


                if(direction == ItemTouchHelper.LEFT){
                    course = Courses(coursesList[position].id, coursesList[position].description , coursesList[position].credits)
                    deleteCourse(course.id)
                    list.adapter?.notifyItemRemoved(position)

                    adapter = RecyclerView_Adapter_Enrollment(coursesList)
                    list.adapter = adapter
                    adapter.notifyDataSetChanged()
                }else{
                    val intent = Intent(this@Matricula, AddMatricula::class.java)
                    val item = coursesList[position]
                    listStudents()
                    intent.putExtra("Students", studentsList )
                    intent.putExtra("course",item.id)
                    startActivity(intent)
                    adapter.notifyDataSetChanged()
                    //getListOfPersons()
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                RecyclerViewSwipeDecorator.Builder(this@Matricula, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@Matricula, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(this@Matricula, R.color.green))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        }



        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(list)


    }
    fun listCourses(){

        db = DatabaseHelper(this)

        var studentsCursor : Cursor? = db!!.getQuery("SELECT * from TABLE_COURSE")


        var studentsSize : Int = studentsCursor!!.count


        coursesList = ArrayList<Courses>()

        if(studentsSize>0){

            do {
                val id = studentsCursor.getInt(0)
                val desc = studentsCursor.getString(1)
                val cred = studentsCursor.getInt(2)

                coursesList.add(Courses(id,desc,cred))
           }while(studentsCursor.moveToNext())
        }



        adapter = RecyclerView_Adapter_Enrollment(coursesList)
        list.adapter = adapter

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_students -> {
                Toast.makeText(this, "Estudiantes", Toast.LENGTH_SHORT).show()
                val i = Intent(this, CRUDStudent::class.java)
                startActivity(i)
                finish()
            }
            R.id.nav_courses -> {
                Toast.makeText(this, "Cursos", Toast.LENGTH_SHORT).show()
                val i = Intent(this, CRUDCourse::class.java)
                startActivity(i)
                finish()
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show()
                val i = Intent(this, Login::class.java)
                startActivity(i)
                finish()
            }
            R.id.nav_mat -> {
                Toast.makeText(this, "Matricula", Toast.LENGTH_SHORT).show()
                val i = Intent(this, Matricula::class.java)
                startActivity(i)
                finish()
            }
        }
        return true
    }

    fun deleteCourse(id :Int){

        try {
            dbHelper.deleteCourse(id.toString())
            listCourses()
        }catch (e: Exception){
            e.printStackTrace()
            //showToast(e.message.toString())
        }

    }

    fun listStudents(){

        db = DatabaseHelper(this)

        var studentsCursor : Cursor? = db!!.getQuery("SELECT * from TABLE_STUDENTS")
        var studentsSize : Int = studentsCursor!!.count


        studentsList = ArrayList<String>()

        do{
            val id = studentsCursor.getInt(0)
            studentsList.add(id.toString())
        }while (studentsCursor.moveToNext())

    }

}

