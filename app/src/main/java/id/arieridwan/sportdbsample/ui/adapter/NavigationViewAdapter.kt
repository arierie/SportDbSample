package id.arieridwan.sportdbsample.ui.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.patloew.navigationviewfragmentadapters.NavigationViewStateFragmentAdapter
import id.arieridwan.sportdbsample.R
import id.arieridwan.sportdbsample.ui.lastmatch.LastFragment
import id.arieridwan.sportdbsample.ui.nextmatch.NextMatchFragment
import id.arieridwan.sportdbsample.ui.today.TodayFragment

/**
 * Created by arieridwan on 30/09/18.
 */

class NavigationViewAdapter(fragmentManager: FragmentManager, containerId: Int,
                            defaultMenuItemId: Int, savedInstanceState: Bundle?) :
        NavigationViewStateFragmentAdapter(fragmentManager, containerId,
                defaultMenuItemId, savedInstanceState) {

    override fun getFragment(menuItemId: Int): Fragment {
        return when(menuItemId) {
            R.id.lastMatchFragment -> {
                LastFragment()
            }
            R.id.nextMatchFragment -> {
                NextMatchFragment()
            }
            R.id.todayMatchFragment -> {
                TodayFragment()
            }
            else -> {
                LastFragment()
            }
        }
    }

}