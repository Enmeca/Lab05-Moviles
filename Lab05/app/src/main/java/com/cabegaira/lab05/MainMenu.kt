package com.cabegaira.lab05

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MainMenu : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val bundle = intent.extras
        val msg = bundle!!.getString("msg")
        val l =  bundle.getSerializable("Login") as User


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        navView.setupWithNavController(navController)
        /*if(l.admin==0){
            navView.menu.removeItem(R.id.nav_list)
        }
        if(l.admin==1){
           // navView.menu.removeItem(R.id.nav)
        }*/
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_students -> {
                Toast.makeText(this, "Estudiantes", Toast.LENGTH_SHORT).show()
                val i = Intent(this, CRUDStudent::class.java)
                startActivity(i)
            }
            R.id.nav_courses -> {
                Toast.makeText(this, "Cursos", Toast.LENGTH_SHORT).show()
                val i = Intent(this, CRUDCourse::class.java)
                startActivity(i)
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
            }
        }
        return true
    }
}