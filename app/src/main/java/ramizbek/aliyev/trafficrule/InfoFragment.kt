package ramizbek.aliyev.trafficrule

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_info.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var root: View

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

        root = inflater.inflate(R.layout.fragment_info, container, false)

        //Tg
        root.telegram.setOnClickListener {
            startUri("https://t.me/Ramizbek_Aliyev")
        }
        //Insta
        root.instagram.setOnClickListener {
            startUri("https://www.instagram.com/ramizbek_aliyev")
        }
        //Facebook
        root.facebook.setOnClickListener {
            startUri("https://fb.com/ramiz.aliyev.98499")
        }
        //GitHub
        root.github.setOnClickListener {
            startUri("https://github.com/Ramizbek")
        }
        //LinkedIn
        root.linkedin.setOnClickListener {
            startUri("https://linkedin.com/in/ramizbek-aliyev-ba767722a")
        }

        return root
    }

    fun startUri(uri: String) {
        val uris = Uri.parse(uri)
        startActivity(Intent(Intent.ACTION_VIEW, uris))

    }

    companion object {
        fun newInstance(): InfoFragment{
            return InfoFragment()
        }
    }
}