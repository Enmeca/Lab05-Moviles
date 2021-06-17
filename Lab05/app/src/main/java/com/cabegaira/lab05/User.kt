package com.cabegaira.lab05

import java.io.Serializable

class User : Serializable {

    var user:String = ""
    var password:String = ""
    var name:String = ""
    var admin: Int = 0

    internal constructor(user:String, password:String, name:String, admin:Int){
        this.user = user
        this.password = password
        this.name = name
        this.admin = admin
    }

}