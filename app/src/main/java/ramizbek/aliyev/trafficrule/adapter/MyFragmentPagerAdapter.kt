package ramizbek.aliyev.trafficrule.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ramizbek.aliyev.trafficrule.TestFragment

class MyFragmentPagerAdapter(var list: List<User>, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(
        fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {

        return TestFragment.newInstance(list[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position].nomi
    }
}