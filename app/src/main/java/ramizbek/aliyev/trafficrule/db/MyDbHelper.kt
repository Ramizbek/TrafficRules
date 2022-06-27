package ramizbek.aliyev.trafficrule.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    Rejalashtirihs {


    companion object {
        const val DB_NAME = "yolQoydalari"
        const val DB_VERSION = 1

        const val TABLE_QOYDALAR = "jadval"
        const val ID = "id"
        const val QOYDA_NOMI = "qoyda_nomi"
        const val QOYDA_TOLIQ_MALUMOT = "qoyda_toliq_malumot"
        const val LIKE = "yoqti"
        const val RASIM = "rasim"
        const val YOLANISHLAR = "yolanishlar"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val qoydalar =
            "create table $TABLE_QOYDALAR ($ID integer not null primary key autoincrement unique, $QOYDA_NOMI text not null, $QOYDA_TOLIQ_MALUMOT text not null, $RASIM text not null, $YOLANISHLAR text not null, $LIKE text not null)"

        db?.execSQL(qoydalar)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addQoyda(qoyda: Qoyda) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(QOYDA_NOMI, qoyda.qoydaNomi)
        contentValues.put(QOYDA_TOLIQ_MALUMOT, qoyda.qoydaToliqMalumot)

        contentValues.put(RASIM, qoyda.rasim)
        contentValues.put(YOLANISHLAR, qoyda.yolanishi)
        contentValues.put(LIKE, qoyda.like)

        database.insert(TABLE_QOYDALAR, null, contentValues)
        database.close()
    }

    override fun showQoydalar(): List<Qoyda> {
        val list = ArrayList<Qoyda>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_QOYDALAR"
        val cursor = database.rawQuery(query,null)
        if (cursor.moveToFirst()){
            do{

                val qoyda = Qoyda(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
                )

                list.add(qoyda)

            }while (cursor.moveToNext())
        }

        return list
    }

    override fun editQoydalar(qoyda: Qoyda): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(QOYDA_NOMI,qoyda.qoydaNomi)
        contentValues.put(QOYDA_TOLIQ_MALUMOT,qoyda.qoydaToliqMalumot)

        contentValues.put(RASIM,qoyda.rasim)
        contentValues.put(YOLANISHLAR,qoyda.yolanishi)
        contentValues.put(LIKE,qoyda.like)
        /**
        var qoydaNomi: String? = null
        var qoydaToliqMalumot: String? = null
        var like: String? = null
        var rasim: String? = null
        var yolanishi: String? = null
         */

        return database.update(
            TABLE_QOYDALAR,
            contentValues,
            "$ID = ?",
            arrayOf(qoyda.id.toString())
        )
    }

    override fun deleteQoydalar(qoyda: Qoyda) {
        val database = this.writableDatabase
        database.delete(TABLE_QOYDALAR,"$ID = ?", arrayOf(qoyda.id.toString()))
    }

    override fun getQoydalarById(id: Int): Qoyda {
        val database = this.readableDatabase
        val cursor = database.query(
            TABLE_QOYDALAR,
            arrayOf(
                ID,
                QOYDA_NOMI,
                QOYDA_TOLIQ_MALUMOT,

                RASIM,
                YOLANISHLAR,
                LIKE
            ),
            "$ID = ?",
            arrayOf(id.toString()), null, null, null
        )
        cursor.moveToFirst()
        return Qoyda(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getString(5)
        )
    }

}