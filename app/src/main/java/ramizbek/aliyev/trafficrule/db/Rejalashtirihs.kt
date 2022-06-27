package ramizbek.aliyev.trafficrule.db

import ramizbek.aliyev.trafficrule.db.Qoyda

interface Rejalashtirihs {
    fun addQoyda(qoyda: Qoyda)
    fun showQoydalar():List<Qoyda>
    fun editQoydalar(qoyda: Qoyda):Int
    fun deleteQoydalar(qoyda: Qoyda)
    fun getQoydalarById(id:Int): Qoyda
}