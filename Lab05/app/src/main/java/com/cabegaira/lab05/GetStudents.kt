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

class GetStudents : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    var db: DatabaseHelper? = null
    private lateinit var MatList : ArrayList<Int>
    private lateinit var StudentsList : ArrayList<Students>
    lateinit var list: RecyclerView
    lateinit var adapter : RecyclerView_Adapter_Students
    internal var dbHelper = DatabaseHelper(this)
    lateinit var course:Courses
    var position: Int = 0
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.students_list)
        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = "Estudiantes Matriculados"


        list = findViewById(R.id.students_list)
        list.layoutManager = LinearLayoutManager(list.context)
        list.setHasFixedSize(true)

        val bundle = intent.extras

        val navView: NavigationView = findViewById(R.id.nav_view)

        /*if(l.admin==0){
            navView.menu.removeItem(R.id.nav_list)
        }
        if(l.admin==1){
           // navView.menu.removeItem(R.id.nav)
        }*/
        navView.setNavigationItemSelectedListener(this)

        var l =  bundle!!.getSerializable("course") as Int

        listCourses(l)

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
                    //course = Courses(coursesList[position].id, coursesList[position].description , coursesList[position].credits)
                    deleteMatricula(MatList[position])
                    listCourses(l)

                    list.adapter?.notifyItemRemoved(position)
                    adapter = RecyclerView_Adapter_Students(StudentsList)
                    list.adapter = adapter
                    adapter.notifyDataSetChanged()
                }else{
/*                    val intent = Intent(this@GetMatricula, EditCourse::class.java)
                    val item = coursesList[position]
                    intent.putExtra("dato", item )
                    startActivity(intent)

                    adapter.notifyDataSetChanged()
                    listCourses(l)*/
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                RecyclerViewSwipeDecorator.Builder(this@GetStudents, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@GetStudents, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
/*                    .addSwipeRightBackgroundColor(ContextCompat.getColor(this@GetMatricula, R.color.green))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)*/
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        }



        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(list)

    }


    fun listCourses(courseid:Int){

        db = DatabaseHelper(this)

        var CoursesCursor : Cursor? = db!!.getQuery("SELECT ID_STUDENT, NAME, LASTNAME, AGE "+
                "FROM TABLE_ENROLLMENT, TABLE_STUDENTS "+
                "WHERE FK_ID_COURSE ="+ courseid +" AND FK_ID_STUDENT = ID_STUDENT")

        var MatriculaCursor : Cursor? = db!!.getQuery("SELECT ID_ENROLLMENT "+
                "FROM TABLE_ENROLLMENT, TABLE_STUDENTS "+
                "WHERE FK_ID_COURSE ="+ courseid +" AND FK_ID_STUDENT = ID_STUDENT")
        var studentsSize : Int = MatriculaCursor!!.count
        var qlist : Int = CoursesCursor!!.count

        if(qlist>0){
            StudentsList = ArrayList<Students>()
            do {
                var id = CoursesCursor.getInt(0)
                var name = CoursesCursor.getString(1)
                var last = CoursesCursor.getString(2)
                var age = CoursesCursor.getInt(3)
                StudentsList.add(Students(id,name,last,age))
            }while(CoursesCursor.moveToNext())

            MatList = ArrayList<Int>()
            do{
                var id = MatriculaCursor.getInt(0)
                MatList.add(id)
            }while(MatriculaCursor.moveToNext())
        }else{
            StudentsList = ArrayList<Students>()
        }
        adapter = RecyclerView_Adapter_Students(StudentsList)
        list.adapter = adapter

    }

    fun deleteMatricula(id :Int){

        try {
            dbHelper.deleteMatricula(id.toString())
/*            listCourses(l)*/
        }catch (e: Exception){
            e.printStackTrace()
            //showToast(e.message.toString())
        }
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
                finish()
            }
        }
        return true
    }
}