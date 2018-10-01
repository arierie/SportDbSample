package id.arieridwan.sportdbsample.data.repository

import id.arieridwan.sportdbsample.data.response.EventListResponse
import id.arieridwan.sportdbsample.data.service.ApiService
import retrofit2.Call

/**
 * Created by arieridwan on 30/09/18.
 */

interface MatchRepository {

    fun loadLastMatch(leagueId: Int): Call<EventListResponse>

    fun loadNextMatch(leagueId: Int): Call<EventListResponse>

    fun loadTodayMatch(leagueId: Int, date: String): Call<EventListResponse>

}