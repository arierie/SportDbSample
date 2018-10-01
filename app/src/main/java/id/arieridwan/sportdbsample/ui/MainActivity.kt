package id.arieridwan.sportdbsample.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import id.arieridwan.sportdbsample.R
import id.arieridwan.sportdbsample.ui.lastmatch.LastFragment
import id.arieridwan.sportdbsample.ui.nextmatch.NextMatchFragment
import id.arieridwan.sportdbsample.ui.today.TodayFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.lastMatchFragment -> {
                loadFragment(LastFragment())
            }
            R.id.nextMatchFragment -> {
                loadFragment(NextMatchFragment())
            }
            R.id.todayMatchFragment -> {
                loadFragment(TodayFragment())
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(LastFragment())
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        //switching fragment
        fragment?.let {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, it)
                    .commit()
            return true
        }
        return false
    }

}
