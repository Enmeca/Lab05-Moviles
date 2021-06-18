package com.cabegaira.lab05

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase


class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_STUDENT (ID_STUDENT INTEGER PRIMARY KEY " +
                "AUTOINCREMENT,NAME TEXT,LASTNAME TEXT,AGE NUMERIC)")

        db.execSQL("CREATE TABLE $TABLE_COURSE ($ID_COURSE INTEGER PRIMARY KEY " +
                "AUTOINCREMENT,$DESCRIPTION TEXT, $CREDITS NUMERIC)")

        db.execSQL("CREATE TABLE $TABLE_ENROLLMENT (ID_ENROLLMENT INTEGER PRIMARY KEY " +
                "AUTOINCREMENT,FK_ID_STUDENT INTEGER, FK_ID_COURSE INTEGER," +
                "FOREIGN KEY(FK_ID_STUDENT) REFERENCES $TABLE_STUDENT(ID_STUDENT)" +
                "FOREIGN KEY(FK_ID_COURSE) REFERENCES $TABLE_COURSE(ID_COURSE))")
    }



    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENROLLMENT)
        onCreate(db)
    }

    /**
     * Let's create our insertData() method.
     * It Will insert data to SQLIte database.
     */
    fun insertData(name: String, surname: String, marks: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, name)
        contentValues.put(LASTNAME, surname)
        contentValues.put(AGE, marks)
        db.insert(TABLE_STUDENT, null, contentValues)
    }

    fun insertStudent(name: String, lastname : String, age : Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, name)
        contentValues.put(LASTNAME, lastname)
        contentValues.put(AGE, age)
        db.insert(TABLE_STUDENT, null, contentValues)
    }


    /**
     * Let's create  a method to update a row with new field values.
     */
    fun updateData(id: String, name: String, surname: String, marks: String):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_STUDENTS, id)
        contentValues.put(NAME, name)
        contentValues.put(LASTNAME, surname)
        contentValues.put(AGE, marks)
        db.update(TABLE_STUDENT, contentValues, "ID = ?", arrayOf(id))
        return true
    }

    /**
     * Let's create a function to delete a given row based on the id.
     */
    fun deleteData(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_STUDENT,"ID = ?", arrayOf(id))
    }

    /**
     * The below getter property will return a Cursor containing our dataset.
     */
    val allData : Cursor
        get() {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM " + TABLE_STUDENT, null)
            return res
        }

    /**
     * The below getter property will return a Cursor containing our dataset.
     */
    fun findById(id : String) : Cursor
    {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM " + TABLE_STUDENT + " WHERE ID=?", arrayOf(id))
        return res
    }

    fun getQuery(query : String?): Cursor {
        val db = this.writableDatabase
        var mCursor : Cursor = db.rawQuery(query, null)
        mCursor?.moveToFirst()
        return mCursor
    }

    /**
     * Let's create a companion object to hold our static fields.
     * A Companion object is an object that is common to all instances of a given
     * class.
     */


    fun insertCourse(id: Int, desc: String, cred: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_COURSE, id)
        contentValues.put(DESCRIPTION, desc)
        contentValues.put(CREDITS, cred)
        db.insert(TABLE_COURSE, null, contentValues)
    }


    fun updateCourse(id: Int, desc: String, cred: Int):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_COURSE, id)
        contentValues.put(DESCRIPTION, desc)
        contentValues.put(CREDITS, cred)

        db.update(TABLE_COURSE, contentValues, "COURSE = ?", arrayOf(id.toString()))
        return true
    }


    fun deleteCourse(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_COURSE,"COURSE = ?", arrayOf(id))
    }

    fun deleteStudent(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_STUDENT,"ID_STUDENT = ?", arrayOf(id))
    }

    val allCourses : Cursor
        get() {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM " + TABLE_COURSE, null)
            return res
        }


    fun findByIdCourse(id : String) : Cursor
    {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM " + TABLE_COURSE + " WHERE COURSE = ?", arrayOf(id))
        return res
    }



    companion object {
        val DATABASE_NAME = "stars.db"
        val TABLE_STUDENT = "TABLE_STUDENTS"
        val ID_STUDENTS = "ID"
        val NAME = "NAME"
        val LASTNAME = "LASTNAME"
        val AGE = "AGE"
        val TABLE_COURSE= "TABLE_COURSE"
        val ID_COURSE = "COURSE"
        val DESCRIPTION = "DESCRIPTION"
        val CREDITS = "CREDITS"
        val TABLE_ENROLLMENT= "TABLE_ENROLLMENT"
        val ID_ENROLLMENT = "ID_ENROLLMENT"
        val FK_ID_STUDENT = "FK_ID_STUDENT"
        val FK_ID_COURSE = "FK_ID_COURSE"

    }
}
//end