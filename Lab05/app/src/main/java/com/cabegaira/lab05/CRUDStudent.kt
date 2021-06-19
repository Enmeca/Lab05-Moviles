package com.cabegaira.lab05

import android.content.Intent
import android.database.Cursor
import android.graphics.Canvas
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*
import kotlin.collections.ArrayList

class CRUDStudent : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    var db: DatabaseHelper? = null
    private lateinit var studentsList : ArrayList<Students>
    lateinit var list: RecyclerView
    lateinit var adapter : RecyclerView_Adapter_Students
    private var btn : Button? = null
    lateinit var student:Students
    internal var dbHelper = DatabaseHelper(this)
    var position: Int = 0
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.students_list)
        val navView: NavigationView = findViewById(R.id.nav_view)

        list = findViewById(R.id.students_list)
        list.layoutManager = LinearLayoutManager(list.context)
        list.setHasFixedSize(true)

        navView.setNavigationItemSelectedListener(this)

        findViewById<SearchView>(R.id.student_search).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        btn = findViewById(R.id.addBtn) as Button
        btn!!.setOnClickListener{
            val i = Intent(this, EditSt::class.java)
            startActivity(i)
        }
        listStudents()

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
                    student = Students(studentsList[position].id, studentsList[position].name, studentsList[position].lastname,studentsList[position].age)
                    deleteStudent(student.id)
                    list.adapter?.notifyItemRemoved(position)

                    adapter = RecyclerView_Adapter_Students(studentsList)
                    list.adapter = adapter
                    adapter.notifyDataSetChanged()
                }else{
                    val intent = Intent(this@CRUDStudent, EditSt::class.java)
                    val item = studentsList[position]
                    intent.putExtra("dato", item )
                    startActivity(intent)
                    adapter.notifyDataSetChanged()

                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                RecyclerViewSwipeDecorator.Builder(this@CRUDStudent, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@CRUDStudent, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(this@CRUDStudent, R.color.green))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        }



        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(list)


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
                val i = Intent(this, CRUDCourse::class.java)
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

    fun listStudents(){

        db = DatabaseHelper(this)

        var studentsCursor : Cursor? = db!!.getQuery("SELECT * from TABLE_STUDENTS")
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

    fun deleteStudent(id :Int){

        try {
            dbHelper.deleteStudent(id.toString())
            listStudents()
        }catch (e: Exception){
            e.printStackTrace()
            //showToast(e.message.toString())
        }
    }

}