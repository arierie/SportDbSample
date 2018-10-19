package id.arieridwan.sportdbsample.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.arieridwan.sportdbsample.R
import id.arieridwan.sportdbsample.ui.lastmatch.LastFragment
import id.arieridwan.sportdbsample.ui.nextmatch.NextMatchFragment
import id.arieridwan.sportdbsample.ui.today.TodayFragment
import id.arieridwan.sportdbsample.util.FragmentStateHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var stateHelper: FragmentStateHelper
    private val fragments = mutableMapOf<Int, Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stateHelper = FragmentStateHelper(supportFragmentManager)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        if (savedInstanceState == null) {
            bottom_navigation.selectedItemId = R.id.lastMatchFragment
        } else {
            val helperState = savedInstanceState.getBundle(STATE_HELPER)
            helperState?.let { stateHelper.restoreHelperState(it) }
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.lastMatchFragment -> {
                fragments[menuItem.itemId] = LastFragment()
                loadFragment(menuItem)
            }
            R.id.nextMatchFragment -> {
                fragments[menuItem.itemId] = NextMatchFragment()
                loadFragment(menuItem)
            }
            R.id.todayMatchFragment -> {
                fragments[menuItem.itemId] = TodayFragment()
                loadFragment(menuItem)
            }
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Make sure we save the current tab's state too!
        saveCurrentState()

        outState.putBundle(STATE_HELPER, stateHelper.saveHelperState())

        super.onSaveInstanceState(outState)
    }

    private fun loadFragment(menuItem: MenuItem) {
        saveCurrentState()
        fragments[menuItem.itemId]?.let {
            stateHelper.restoreState(it, menuItem.itemId)
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, it)
                    .commitNowAllowingStateLoss()
        }
    }

    private fun saveCurrentState() {
        fragments[bottom_navigation.selectedItemId]?.let { oldFragment ->
            stateHelper.saveState(oldFragment, bottom_navigation.selectedItemId)
        }
    }

    companion object {
        private const val STATE_HELPER = "helper"
    }

}
