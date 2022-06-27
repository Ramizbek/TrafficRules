package ramizbek.aliyev.trafficrule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import ramizbek.aliyev.trafficrule.R

class CustomAdapter(private var context1: Context, private var fruit: ArrayList<String>) :
    BaseAdapter() {
    private var inflter: LayoutInflater = LayoutInflater.from(context1)

    override fun getCount(): Int {
        return fruit.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {

        val view = inflter.inflate(R.layout.custome_spinner,null)
        //val icon = view.findViewById<View>(R.id.imageView) as ImageView?
        val names = view.findViewById<View>(R.id.textView) as TextView?

        names!!.text = fruit[i]

        return view
    }
}