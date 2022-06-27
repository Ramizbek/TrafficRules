package ramizbek.aliyev.trafficrule

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_qoydalar.*
import ramizbek.aliyev.trafficrule.adapter.CustomAdapter
import ramizbek.aliyev.trafficrule.db.MyDbHelper
import ramizbek.aliyev.trafficrule.db.Qoyda
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime


class Qoydalar : AppCompatActivity() {
    lateinit var customAdapter: CustomAdapter
    var spiner1: Spinner? = null
    var spineText: String = ""
    lateinit var myDbHelper: MyDbHelper
    var joyi: String? = null
     var a:Int? = null
    var text:String? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qoydalar)




        orqagaBelgiQoshish.setOnClickListener {
            finish()
        }

onResume()
        val arrayListOf4 = arrayListOf("Warning", "Privileged", "Prohibition", "Commander")
        spiner1 = spiner
        customAdapter = CustomAdapter(
            this,
            arrayListOf("Warning", "Privileged", "Prohibition", "Commander")
        )
        spiner1!!.adapter = customAdapter

        saqlash.setOnClickListener {
            val yolNomi = yolNomi.text.toString()
            val toString = yolToliqMalumot.text.toString()


            val spinnerMentorlar23 = spiner

            spinnerMentorlar23.onItemSelectedListener = object : AdapterView.OnItemClickListener,
                AdapterView.OnItemSelectedListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    a = position




                    when (a) {
                        0 -> text = "Warning"
                        1 -> text = "Privileged"
                        2 -> text = "Prohibition"
                        3 -> text = "Commander"


                    }


                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    a = position




                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }



            when (a) {
                0 ->     text = "Warning"
                1 ->     text = "Privileged"

                2 ->     text = "Prohibition"

                3 ->     text = "Commander"


            }



            val filesDir = filesDir
            val listFiles1 = filesDir.listFiles()
            if (filesDir.isDirectory) {
                val listFiles = filesDir.listFiles()
                for (listFile1 in listFiles) {

//listFile1.delete()
                    joyi = listFile1.toString()
                }
            }
            myDbHelper = MyDbHelper(this)



            if (yolNomi.isNotEmpty() && toString.isNotEmpty() && text!!.isNotEmpty()
            ) {
                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
                myDbHelper.addQoyda(Qoyda(yolNomi, toString,  joyi, text,"0"))



                finish()

            } else
                Toast.makeText(this, "Data is not enough", Toast.LENGTH_SHORT).show()


        }



        rasim_qoy.setOnClickListener {
            startActivityForResult(
                Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"
                }, 1
            )
        }

    }

    override fun onResume() {
        super.onResume()
        val yolNomi = yolNomi.text.toString()
        val toString = yolToliqMalumot.text.toString()


        val spinnerMentorlar23 = spiner

        spinnerMentorlar23.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                a = position



                when (a) {
                    0 ->     text = "Warning"
                    1 ->     text = "Privileged"

                    2 ->     text = "Prohibition"

                    3 ->     text = "Commander"


                }

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                a = position




            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }



        when (a) {
            0 ->     text = "Warning"
            1 ->     text = "Privileged"

            2 ->     text = "Prohibition"

            3 ->     text = "Commander"


        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val uri = data?.data ?: return
            rasim_qoy.setImageURI(uri)


            val inputStream = contentResolver?.openInputStream(uri)
            val localDateTime = LocalDateTime.now()
            val file = File(filesDir, "$localDateTime images.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream?.close()

        }
    }
}