package ramizbek.aliyev.trafficrule.adapter

import android.widget.ImageView
import java.io.Serializable

class User:Serializable {
    var nomi:String? = null
    var matn:String? = null
    var like:Boolean? = null



    constructor(nomi: String?, matn: String?, like: Boolean?) {
        this.nomi = nomi
        this.matn = matn
        this.like = like
    }
}