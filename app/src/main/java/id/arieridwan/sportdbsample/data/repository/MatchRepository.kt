package id.arieridwan.sportdbsample.data.repository

import id.arieridwan.sportdbsample.data.response.EventListResponse
import id.arieridwan.sportdbsample.data.service.ApiService
import retrofit2.Call


/**
 * Created by arieridwan on 30/09/18.
 */

class MatchRepository(private val apiService: ApiService) {

    // load last match from network
    fun loadLastMatch(leagueId: Int): Call<EventListResponse> = apiService.loadLastMatches(leagueId)

}