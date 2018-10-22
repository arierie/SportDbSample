package id.arieridwan.sportdbsample.ui.today

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
import kotlinx.android.synthetic.main.fragment_today.*
import org.koin.android.ext.android.get

class TodayFragment : Fragment(), TodayView {

    private lateinit var todayPresenter: TodayPresenter
    private lateinit var adapter: EventAdapter
    private val events: MutableList<EventResponse> = mutableListOf()

    @State
    var listState: Parcelable? = null

    @State
    lateinit var eventList: EventListResponse

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_today, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StateSaver.restoreInstanceState(this, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        todayPresenter = get()
        todayPresenter.attachView(this)

        if(savedInstanceState == null) {
            // TODO change hardcoded date with calendar instance time
            todayPresenter.loadTodayMatch(4328, "2018-10-06")
        } else {
            notifyList(eventList)
            rv_today_match.layoutManager?.onRestoreInstanceState(listState)
        }

    }

    private fun initList() {
        adapter = EventAdapter(events)
        rv_today_match.layoutManager = LinearLayoutManager(activity)
        rv_today_match.adapter = this.adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        todayPresenter.detachView()
    }

    override fun showLoading() {
        pb_today.visible()
    }

    override fun hideLoading() {
        pb_today.gone()
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
