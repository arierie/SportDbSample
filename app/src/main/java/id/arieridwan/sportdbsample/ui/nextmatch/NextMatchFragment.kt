package id.arieridwan.sportdbsample.ui.nextmatch

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.evernote.android.state.State
import com.evernote.android.state.StateSaver

import id.arieridwan.sportdbsample.R
import id.arieridwan.sportdbsample.data.response.EventListResponse
import id.arieridwan.sportdbsample.data.response.EventResponse
import id.arieridwan.sportdbsample.ui.adapter.EventAdapter
import id.arieridwan.sportdbsample.util.gone
import id.arieridwan.sportdbsample.util.visible
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.koin.android.ext.android.get

class NextMatchFragment : Fragment(), NextMatchView {

    private lateinit var nextMatchPresenter: NextMatchPresenter
    private lateinit var adapter: EventAdapter
    private val events: MutableList<EventResponse> = mutableListOf()

    @State
    var listState: Parcelable? = null

    @State
    lateinit var eventList: EventListResponse

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_next_match, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StateSaver.restoreInstanceState(this, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        nextMatchPresenter = get()
        nextMatchPresenter.attachView(this)

        if(savedInstanceState == null) {
            nextMatchPresenter.loadNextMatch(4328)
        } else {
            notifyList(eventList)
            rv_next_match.layoutManager?.onRestoreInstanceState(listState)
        }
    }

    private fun initList() {
        adapter = EventAdapter(events)
        rv_next_match.layoutManager = LinearLayoutManager(activity)
        rv_next_match.adapter = this.adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        nextMatchPresenter.detachView()
    }

    override fun showLoading() {
        pb_next.visible()
    }

    override fun hideLoading() {
       pb_next.gone()
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
        StateSaver.saveInstanceState(this, outState)
    }

}
