package id.arieridwan.sportdbsample.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.arieridwan.sportdbsample.R
import id.arieridwan.sportdbsample.data.response.EventResponse
import id.arieridwan.sportdbsample.util.CommonUtils
import kotlinx.android.synthetic.main.item_event.view.*

/**
 * Created by arieridwan on 26/07/18.
 */

class EventAdapter(private val events: List<EventResponse>): RecyclerView.Adapter<EventViewHolder>() {

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindItem(events[position])
    }

    override fun getItemCount() = events.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder =
            EventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false))

}

class EventViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bindItem(item: EventResponse) {

        itemView.tvDate.text = item.dateEvent?.let { CommonUtils.getStringDate(it) } ?: "-"
        itemView.tvHomeTeam.text = item.strHomeTeam ?: "-"
        itemView.tvAwayTeam.text = item.strAwayTeam ?: "-"

        val homeScore: Int? = item.intHomeScore ?: 0
        val awayScore: Int? = item.intAwayScore ?: 0

        itemView.tvHomeScore.text = homeScore.toString()
        itemView.tvAwayScore.text = awayScore.toString()

    }

}