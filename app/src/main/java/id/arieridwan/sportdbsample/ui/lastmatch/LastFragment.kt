package id.arieridwan.sportdbsample.ui.lastmatch

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.arieridwan.sportdbsample.R
import id.arieridwan.sportdbsample.data.response.EventListResponse
import id.arieridwan.sportdbsample.data.response.EventResponse
import id.arieridwan.sportdbsample.util.gone
import id.arieridwan.sportdbsample.util.visible
import id.arieridwan.sportdbsample.ui.adapter.EventAdapter
import kotlinx.android.synthetic.main.fragment_last.*
import org.koin.android.ext.android.get

class LastFragment : Fragment(), LastMatchView {

    private lateinit var lastMatchPresenter: LastMatchPresenter
    private lateinit var adapter: EventAdapter
    private val events: MutableList<EventResponse> = mutableListOf()
    private var listState: Parcelable? = null
    private lateinit var eventList: EventListResponse

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_last, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        lastMatchPresenter = get()
        lastMatchPresenter.attachView(this)

        if(savedInstanceState == null) {
            lastMatchPresenter.loadLastMatch(4328)
        } else {
            eventList = savedInstanceState.getParcelable(LIST_STATE_KEY)
            listState = savedInstanceState.getParcelable(SCROLL_STATE_KEY)
            notifyList(eventList)
            rv_last_match.layoutManager?.onRestoreInstanceState(listState)
        }

    }

    private fun initList() {
        adapter = EventAdapter(events)
        rv_last_match.layoutManager = LinearLayoutManager(activity)
        rv_last_match.adapter = this.adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lastMatchPresenter.detachView()
    }

    override fun showLoading() {
        pb_last.visible()
    }

    override fun hideLoading() {
        pb_last.gone()
    }

    override fun showResultList(eventListResponse: EventListResponse) {
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

    override fun showError() {
        Toast.makeText(context, "Error, failed to get data from server", Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LIST_STATE_KEY, eventList)
        outState.putParcelable(SCROLL_STATE_KEY, listState)
    }

    companion object {
        private const val SCROLL_STATE_KEY = "SCROLL_STATE_KEY"
        private const val LIST_STATE_KEY = "LIST_STATE_KEY"
    }

}
