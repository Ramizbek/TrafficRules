package ramizbek.aliyev.trafficrule.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_like.view.*
import kotlinx.android.synthetic.main.fragment_test.view.*
import ramizbek.aliyev.trafficrule.R
import ramizbek.aliyev.trafficrule.databinding.ItemRvBinding
import ramizbek.aliyev.trafficrule.databinding.ItemTalabaOchirishBinding
import ramizbek.aliyev.trafficrule.db.MyDbHelper
import ramizbek.aliyev.trafficrule.db.Qoyda
import java.io.File

class RvAdapterLike(
    val layoutInflater: LayoutInflater,
    val filesDir: File,
    val root: View,
    val context: Context,
    val list: List<Qoyda>,
    var rvClick: RvClick
) :
    RecyclerView.Adapter<RvAdapterLike.VH>() {

    var a: Int? = null
    lateinit var dRasim: ImageView
    lateinit var customAdapter: CustomAdapter
    var text: String? = null

    inner class VH(var itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(qoyda: Qoyda) {
            itemRvBinding.nomiQoyda.text = qoyda.qoydaNomi

            val file = File("${qoyda.rasim.toString()}")


            val uri = Uri.fromFile(file)

            if (qoyda.like == "1")
                itemRvBinding.yurakQoyda.setImageResource(R.drawable.yurak_bosilganda)


            itemRvBinding.belgiRasim.setImageURI(uri)

            itemRvBinding.belgiRasim.setOnClickListener {
                rvClick.onClick(qoyda)
            }
            itemRvBinding.yurakQoyda.setOnClickListener {
                rvClick.likeQoyda(qoyda)
                if (qoyda.like == "0") {
                    itemRvBinding.yurakQoyda.setImageResource(R.drawable.yurak_bosilganda)
                    qoyda.like = "1"
                    MyDbHelper(context).editQoydalar(qoyda)
                } else
                    if (qoyda.like == "1") {
                        itemRvBinding.yurakQoyda.setImageResource(R.drawable.yurak_bosilmaganda)
                        qoyda.like = "0"
                        MyDbHelper(context).editQoydalar(qoyda)
                    }

                update()

            }

            itemRvBinding.images.setOnClickListener {
                rvClick.editQoyda(qoyda)

            }

            itemRvBinding.tvName.setOnClickListener {
                rvClick.editQoyda(qoyda)
            }

            itemRvBinding.tvOchirish.setOnClickListener {
                rvClick.deleteQoyda(qoyda)
                delete(qoyda)
                update()
            }

            itemRvBinding.imagesOchirish.setOnClickListener {
                rvClick.deleteQoyda(qoyda)
                delete(qoyda)
                update()
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface RvClick {
        fun onClick(qoyda: Qoyda)
        fun editQoyda(qoyda: Qoyda)
        fun deleteQoyda(qoyda: Qoyda)
        fun likeQoyda(qoyda: Qoyda)
    }

    private fun update() {
        val myDbHelper = MyDbHelper(context)
        val showQoydalar = myDbHelper.showQoydalar() as ArrayList
        val newList = ArrayList<Qoyda>()

        for (qoyda in showQoydalar) {
            if (qoyda.like == "1")
                newList.add(qoyda)
            qoyda.rasim
        }

        if (newList.size == 0)
            Toast.makeText(context, "Bo'sh", Toast.LENGTH_SHORT).show()

        val rvAdapterLike =
            RvAdapterLike(layoutInflater, filesDir, root, context, newList, object : RvClick {
                override fun onClick(qoyda: Qoyda) {

                }

                override fun editQoyda(qoyda: Qoyda) {

                }

                override fun deleteQoyda(qoyda: Qoyda) {

                }

                override fun likeQoyda(qoyda: Qoyda) {
                    update()
                }

            })



        root.rv_like.adapter = rvAdapterLike
    }

    fun delete(qoyda: Qoyda) {

        val dialog = AlertDialog.Builder(root.context).create()
        val itemTalabaOchirishBinding =
            ItemTalabaOchirishBinding.inflate(layoutInflater)
        dialog.setView(itemTalabaOchirishBinding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        itemTalabaOchirishBinding.o.text = "Rostan ham o'chirmoqchimisiz"

        val file = File(qoyda.rasim.toString())
        val uri = Uri.fromFile(file)

        itemTalabaOchirishBinding.rasimOchirish.setImageURI(uri)


        dialog.show()

        itemTalabaOchirishBinding.ha.setOnClickListener {
            val myDbHelper = MyDbHelper(root.context)

            myDbHelper.deleteQoydalar(qoyda)

            val file = File(filesDir, "${qoyda.like.toString()}")
            for (i in filesDir.listFiles().indices) {
                if (filesDir.listFiles()[i] == file) {
                    filesDir.listFiles()[i].delete()
                    break
                }
            }

            update()


            dialog.dismiss()

        }
        itemTalabaOchirishBinding.yoq.setOnClickListener {
            dialog.hide()
        }



    }


}
