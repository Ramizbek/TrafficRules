package ramizbek.aliyev.trafficrule.db

import java.io.Serializable

class Qoyda :Serializable {
    var id: Int? = null
    var qoydaNomi: String? = null
    var qoydaToliqMalumot: String? = null
    var rasim: String? = null
    var yolanishi: String? = null
    var like: String? = null



    constructor(
        id: Int?,
        qoydaNomi: String?,
        qoydaToliqMalumot: String?,
        rasim: String?,
        yolanishi: String?,
        like: String?
    ) {
        this.id = id
        this.qoydaNomi = qoydaNomi
        this.qoydaToliqMalumot = qoydaToliqMalumot
        this.rasim = rasim
        this.yolanishi = yolanishi
        this.like = like
    }

    constructor(
        qoydaNomi: String?,
        qoydaToliqMalumot: String?,
        rasim: String?,
        yolanishi: String?,
        like: String?
    ) {
        this.qoydaNomi = qoydaNomi
        this.qoydaToliqMalumot = qoydaToliqMalumot
        this.rasim = rasim
        this.yolanishi = yolanishi
        this.like = like
    }
}