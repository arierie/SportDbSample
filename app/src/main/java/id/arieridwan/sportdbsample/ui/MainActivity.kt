package id.arieridwan.sportdbsample.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.patloew.navigationviewfragmentadapters.NavigationViewStateFragmentAdapter
import id.arieridwan.sportdbsample.R
import id.arieridwan.sportdbsample.ui.adapter.NavigationViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mNavAdapter: NavigationViewStateFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mNavAdapter = NavigationViewAdapter(supportFragmentManager, R.id.main_container,
                R.id.lastMatchFragment, savedInstanceState)
        mNavAdapter.attachTo(bottom_navigation)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.let { mNavAdapter.onSaveInstanceState(it) }
    }

}
