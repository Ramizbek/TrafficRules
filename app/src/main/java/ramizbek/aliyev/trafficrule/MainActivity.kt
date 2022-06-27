package ramizbek.aliyev.trafficrule


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val infoFragment = InfoFragment()
    private val likeFragment = LikeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        replaceFragment(homeFragment)
        bottom_navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.my_home -> replaceFragment(homeFragment)
                R.id.my_like -> replaceFragment(likeFragment)
                R.id.my_malumot -> replaceFragment(infoFragment)
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContener, fragment)
            transaction.commit()
        }

    }
}


