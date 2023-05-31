package uz.farxod.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DatabaseHelper(context: Context):
SQLiteOpenHelper(context, DatabaseConfig.DATABASE_NAME, null, DatabaseConfig.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        //DB -ni peremennaga yozib chiqamiz
        val createTableQuery =
            "CREATE TABLE ${DatabaseConfig.TABLE_NAME} " +
                    "(" +
                        "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "${DatabaseConfig.FIRSTNAME_COL} TEXT," +
                        "${DatabaseConfig.LASTNAME_COL} TEXT," +
                        "${DatabaseConfig.AGE_COL} INTEGER" +
                    ")"

        db!!.execSQL(createTableQuery)  // DB - ni execSQL() orqali yaratamiz
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) { //обновление dan kiyin
        //agar db- mavjud bolsa uni o'chirib qayta yaratish
        db?.execSQL("DROP TABLE IF EXISTS ${DatabaseConfig.TABLE_NAME}")
    }

    fun incertUser(firstname: String, lastname: String, age: Int): Boolean{
        val db: SQLiteDatabase = this.writableDatabase  // yozish, o'zgartirish uchun
        val contentValues = ContentValues() //obyekt oldik

        //Bundle kabi ma'lumotlarni bir joyga yig'amiz
        contentValues.put(DatabaseConfig.FIRSTNAME_COL, firstname)
        contentValues.put(DatabaseConfig.LASTNAME_COL, lastname)
        contentValues.put(DatabaseConfig.AGE_COL, age)

        // bu raqam qaytaradi, agar saqlansa 1- qaytadi, saqlanmasa -1
        val isInserted = db.insert(DatabaseConfig.TABLE_NAME, null, contentValues)
        //agar isInserted -1 ga teng bo'lmasa
        return !isInserted.equals(-1)
    }

    // ma'l o'qish uchun
    fun readUser(): Cursor{
        val db: SQLiteDatabase = this.readableDatabase // o'qish uchun
        // cursor - o'qish jarayonida keyingi qator bor-yo'qligini tekshiradi
        val cursor = db.rawQuery("SELECT * FROM ${DatabaseConfig.TABLE_NAME}/* pastdagi (ORDER BY \${BaseColumns._ID} DESC)- bu oxirgi qo'shilgan ma'l yuqorida paydo bo'lishi un*/ " +
                "ORDER BY ${BaseColumns._ID} DESC", null)

        return cursor
    }

    // ma'l o'chirish uchun
    fun deleteUser(id: String): Boolean{
        val db: SQLiteDatabase = this.writableDatabase
        val deleteData = db.delete(DatabaseConfig.TABLE_NAME, "${BaseColumns._ID} = ?", arrayOf(id))
        return deleteData != -1
    }

    // ma'l o'zgartirish uchun
    fun updateUser(id: String, firstname: String, lastname: String, age: Int): Boolean{
        val db: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(DatabaseConfig.FIRSTNAME_COL, firstname)
        contentValues.put(DatabaseConfig.LASTNAME_COL, lastname)
        contentValues.put(DatabaseConfig.AGE_COL, age)

        val update = db.update(DatabaseConfig.TABLE_NAME, contentValues,"${BaseColumns._ID} =?", arrayOf(id))
        return update != -1
    }
}