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

    private lateinit var MatList : ArrayList<Int>
    private lateinit var coursesList : ArrayList<Courses>
    lateinit var list: RecyclerView
    lateinit var adapter : RecyclerView_Adapter_Courses
    internal var dbHelper = DatabaseHelper(this)
    lateinit var course:Courses
    var position: Int = 0
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.matricula_list)
        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = "Matriculas del Estudiante"

        val navView: NavigationView = findViewById(R.id.nav_view)

        list = findViewById(R.id.matricula_list)
        list.layoutManager = LinearLayoutManager(list.context)
        list.setHasFixedSize(true)

        val bundle = intent.extras

        var l =  bundle!!.getSerializable("student") as Int

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
                    adapter = RecyclerView_Adapter_Courses(coursesList)
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

                RecyclerViewSwipeDecorator.Builder(this@GetMatricula, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@GetMatricula, R.color.red))
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


    fun listCourses(student:Int){

        db = DatabaseHelper(this)

        var CoursesCursor : Cursor? = db!!.getQuery("SELECT ID_COURSE, DESCRIPTION, CREDITS "+
                "FROM TABLE_ENROLLMENT, TABLE_COURSE "+
                "WHERE FK_ID_STUDENT ="+ student +" AND FK_ID_COURSE = ID_COURSE")

        var MatriculaCursor : Cursor? = db!!.getQuery("SELECT ID_ENROLLMENT "+
                "FROM TABLE_ENROLLMENT, TABLE_COURSE "+
                "WHERE FK_ID_STUDENT ="+ student +" AND FK_ID_COURSE = ID_COURSE")

        var studentsSize : Int = CoursesCursor!!.count
        var qlist : Int = MatriculaCursor!!.count

        if(qlist>0){
            coursesList = ArrayList<Courses>()
            do {
                val id = CoursesCursor.getInt(0)
                val desc = CoursesCursor.getString(1)
                val cred = CoursesCursor.getInt(2)

                coursesList.add(Courses(id,desc,cred))
            }while(CoursesCursor.moveToNext())

            MatList = ArrayList<Int>()
            do {
                val id = MatriculaCursor.getInt(0)
                MatList.add(id)
            }while(MatriculaCursor.moveToNext())
        }else{
            coursesList = ArrayList<Courses>()
            MatList = ArrayList<Int>()
        }



        adapter = RecyclerView_Adapter_Courses(coursesList)
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
}