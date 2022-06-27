package ramizbek.aliyev.trafficrule

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.fragment_like.view.*
import kotlinx.android.synthetic.main.item_birinchi_vazifa.view.*
import kotlinx.android.synthetic.main.item_ikkinchi_vazifa.view.*
import ramizbek.aliyev.trafficrule.adapter.CustomAdapter
import ramizbek.aliyev.trafficrule.adapter.RvAdapterLike
import ramizbek.aliyev.trafficrule.db.MyDbHelper
import ramizbek.aliyev.trafficrule.db.Qoyda
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LikeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var root: View
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var rvAdapterLike: RvAdapterLike
    lateinit var customAdapter: CustomAdapter
    lateinit var dRasim: ImageView
    var uris: Uri? = null
    var a: Int? = null
    var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        root = inflater.inflate(R.layout.fragment_like, container, false)

        onResume()

        return root
    }

    private fun update() {
        myDbHelper = MyDbHelper(root.context)
        val showQoydalar = myDbHelper.showQoydalar() as ArrayList
        val newList = ArrayList<Qoyda>()

        for (qoyda in showQoydalar) {
            if (qoyda.like == "1")
                newList.add(qoyda)
            qoyda.rasim
        }

        if (newList.size == 0)
            Toast.makeText(root.context, "Bo'sh", Toast.LENGTH_SHORT).show()

        rvAdapterLike = RvAdapterLike(
            layoutInflater,
            requireActivity().filesDir,
            root,
            root.context,
            newList,
            object : RvAdapterLike.RvClick {
                override fun onClick(qoyda: Qoyda) {
                    showF(qoyda.rasim)
                    onResume()
                }


                override fun editQoyda(qoyda: Qoyda) {
                    edit(qoyda)
                    onResume()
                }

                override fun deleteQoyda(qoyda: Qoyda) {

                    onResume()
                }

                override fun likeQoyda(qoyda: Qoyda) {
                    onResume()
                }

            })

        root.rv_like.adapter = rvAdapterLike
    }

    override fun onResume() {
        super.onResume()
        myDbHelper = MyDbHelper(root.context)
        val showQoydalar = myDbHelper.showQoydalar() as ArrayList
        val newList = ArrayList<Qoyda>()

        for (qoyda in showQoydalar) {
            if (qoyda.like == "1")
                newList.add(qoyda)
            qoyda.rasim
        }

        if (newList.size == 0)
            Toast.makeText(root.context, "Bo'sh", Toast.LENGTH_SHORT).show()

        rvAdapterLike = RvAdapterLike(
            layoutInflater,
            requireActivity().filesDir,
            root,
            root.context,
            newList,
            object : RvAdapterLike.RvClick {
                override fun onClick(qoyda: Qoyda) {
                    showF(qoyda.rasim)
                    update()
                }

                override fun editQoyda(qoyda: Qoyda) {
                    edit(qoyda)
                    update()
                }

                override fun deleteQoyda(qoyda: Qoyda) {

                    update()
                }

                override fun likeQoyda(qoyda: Qoyda) {
                    update()
                }

            })

        root.rv_like.adapter = rvAdapterLike
    }

    private fun edit(qoyda: Qoyda) {
        val alertDialog = AlertDialog.Builder(root.context)
        val dialog = alertDialog.create()

        val dialogView =
            layoutInflater.inflate(R.layout.item_birinchi_vazifa, null, false)
        dialog.setView(dialogView)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.setCancelable(true)
        val file = File("${qoyda.rasim.toString()}")
        val uri = Uri.fromFile(file)
        dialogView.o_rasim_qoy.setImageURI(uri)
        val oYolnomi = dialogView.o_yolNomi
        val oYoltoliqmalumot = dialogView.o_yolToliqMalumot
        oYolnomi.setText(qoyda.qoydaNomi)
        oYoltoliqmalumot.setText(qoyda.qoydaToliqMalumot)

        dRasim = dialogView.o_rasim_qoy

        if (qoyda.yolanishi == "Warning")
            a = 0
        if (qoyda.yolanishi == "Privileged")
            a = 1
        if (qoyda.yolanishi == "Prohibition")
            a = 2
        if (qoyda.yolanishi == "Commander")
            a = 3

        customAdapter = CustomAdapter(
            root.context,
            arrayListOf("Warning", "Privileged", "Prohibition", "Commander")
        )
        dialogView.o_spiner!!.adapter = customAdapter

        dialog.show()

        dialogView.o_rasim_qoy.setOnClickListener {
            startActivityForResult(
                Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"
                }, 1
            )
        }


        val spinnerMentorlar23 = dialogView.o_spiner

        spinnerMentorlar23.onItemSelectedListener =
            object : AdapterView.OnItemClickListener,
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

        dialogView.o_saqlash.setOnClickListener {
            val text1 = dialogView.o_yolNomi.text.toString()
            val text2 = dialogView.o_yolToliqMalumot.text.toString()
            var yoji = ""

            val filesDir = requireActivity().filesDir
            if (filesDir.isDirectory) {
                val listFiles = filesDir.listFiles()
                for (listFile1 in listFiles) {
                    yoji = listFile1.toString()
                }
            }


            val myDbHelper = MyDbHelper(root.context)
            qoyda.qoydaNomi = text1
            qoyda.qoydaToliqMalumot = text2
            qoyda.like = "1"

            qoyda.rasim = yoji

            println(yoji)

            when (a) {
                0 -> text = "Warning"
                1 -> text = "Privileged"
                2 -> text = "Prohibition"
                3 -> text = "Commander"
            }

            println(text)
            println(a)
            println(qoyda.qoydaNomi)
            println(qoyda.qoydaToliqMalumot)


            qoyda.yolanishi = text


            myDbHelper.editQoydalar(qoyda)

            Toast.makeText(root.context, "Malumot Saqlandi", Toast.LENGTH_SHORT)
                .show()
            dialog.dismiss()

            update()

        }
    }


    private fun showF(rasim: String?) {
        val alertDialog = AlertDialog.Builder(root.context)
        val dialog = alertDialog.create()

        val dialogView =
            layoutInflater.inflate(R.layout.item_ikkinchi_vazifa, null, false)
        dialog.setView(dialogView)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        if (rasim != null) {
            val file = File(rasim)
            val uri = Uri.fromFile(file)
            dialogView.ikkinchi_item_rasim.setImageURI(uri)
        }
        dialog.setCancelable(true)
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val uri = data?.data ?: return
            dRasim.setImageURI(uri)


            val inputStream = requireActivity().contentResolver?.openInputStream(uri)
            val localDateTime = LocalDateTime.now()
            val file = File(requireActivity().filesDir, "$localDateTime images.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream?.close()


        }

    }
    companion object {
        fun newInstance(): InfoFragment{
            return InfoFragment()
        }
    }
}


