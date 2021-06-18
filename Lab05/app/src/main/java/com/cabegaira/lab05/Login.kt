package com.cabegaira.lab05


import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Login : AppCompatActivity() {

    var users: Users = Users.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        var et_user_name = findViewById(R.id.et_user_name) as EditText
        var et_password = findViewById(R.id.et_password) as EditText
        var btn_submit = findViewById(R.id.btn_submit) as Button

/*        val create_click = findViewById(R.id.textViewLinkRegister) as TextView
        create_click.setOnClickListener{
            val intent = Intent(this, CreateUser::class.java)
            intent.putExtra("MESSAGE", "msg")
            startActivity(intent)
        }
        val change_click = findViewById(R.id.textViewLinkChangePass) as TextView
        change_click.setOnClickListener{
            val intent = Intent(this, ChangePass::class.java)
            intent.putExtra("MESSAGE", "msg")
            startActivity(intent)
        }*/
        
        btn_submit.setOnClickListener {
            val user_name = et_user_name.text;
            val password = et_password.text;
            //Toast.makeText(this@LoginExample, user_name, Toast.LENGTH_LONG).show()
            if(users.login(user_name.toString().lowercase(), password.toString())){
                val login = users.loginP(user_name.toString().lowercase(), password.toString())
                val i = Intent(this, MainMenu::class.java)
                i.putExtra("msg", "MENSAJE DE Login al Menú")
                i.putExtra("Login", login)
           i.putExtra("passw", password.toString())
                // start your next activity
                startActivity(i)
                finish()
                // your code to validate the user_name and password combination
                // and verify the same
            }else{
                Toast.makeText(this, "El usuario no se encuentra registrado", Toast.LENGTH_SHORT).show()
            }



        }

    }

}