package id.arieridwan.sportdbsample.ui.lastmatch

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.evernote.android.state.State
import com.evernote.android.state.StateSaver
import id.arieridwan.sportdbsample.R
import id.arieridwan.sportdbsample.data.response.EventListResponse
import id.arieridwan.sportdbsample.data.response.EventResponse
import id.arieridwan.sportdbsample.data.states.LoadState.LOADING
import id.arieridwan.sportdbsample.data.states.LoadState.SUCCESS
import id.arieridwan.sportdbsample.data.states.LoadState.FAILED
import id.arieridwan.sportdbsample.util.gone
import id.arieridwan.sportdbsample.util.visible
import id.arieridwan.sportdbsample.ui.adapter.EventAdapter
import kotlinx.android.synthetic.main.fragment_last.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LastFragment : Fragment() {

    private val mViewModel by viewModel<LastMatchViewModel>()
    private lateinit var adapter: EventAdapter
    private val events: MutableList<EventResponse> = mutableListOf()

    @State
    var listState: Parcelable? = null

    @State
    lateinit var eventList: EventListResponse

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_last, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StateSaver.restoreInstanceState(this, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initObserver()

        if (savedInstanceState == null) {
            mViewModel.loadLastMatch(4328)
        } else {
            notifyList(eventList)
            rv_last_match.layoutManager?.onRestoreInstanceState(listState)
        }

    }

    private fun initObserver() {
        mViewModel.eventLoadLastMatches.observe(this, Observer {
            it?.let { response ->
                when (response.state) {
                    LOADING -> showLoading()
                    SUCCESS -> {
                        hideLoading()
                        response.data?.let { eventList -> showResultList(eventList) }
                    }
                    FAILED -> {
                        hideLoading()
                        showError(response.error)
                    }
                }
            }
        })
    }

    private fun initList() {
        adapter = EventAdapter(events)
        rv_last_match.layoutManager = LinearLayoutManager(activity)
        rv_last_match.adapter = this.adapter
    }


    private fun showLoading() {
        pb_last.visible()
    }

    private fun hideLoading() {
        pb_last.gone()
    }

    private fun showResultList(eventListResponse: EventListResponse) {
        eventList = eventListResponse
        notifyList(eventList)
    }

    private fun notifyList(eventListResponse: EventListResponse) {
        events.clear()
        eventListResponse.events?.let {
            for (i in eventListResponse.events.indices) {
                events.add(eventListResponse.events[i])
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun showError(error: String?) {
        val errorMessage = error ?: "Error, failed to get data from server"
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        StateSaver.saveInstanceState(this, outState)
    }

}
