package id.arieridwan.sportdbsample.data.repository

import id.arieridwan.sportdbsample.data.response.EventListResponse
import id.arieridwan.sportdbsample.data.service.ApiService
import retrofit2.Call

class MatchRepositoryImpl(private val apiService: ApiService) : MatchRepository {

    override fun loadLastMatch(leagueId: Int): Call<EventListResponse> = apiService.loadLastMatches(leagueId)

    override fun loadNextMatch(leagueId: Int): Call<EventListResponse> = apiService.loadNextMatches(leagueId)

    override fun loadTodayMatch(leagueId: Int, date: String): Call<EventListResponse> = apiService.loadTodayMatch(leagueId, date)

}