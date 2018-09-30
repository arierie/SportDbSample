package id.arieridwan.sportdbsample.data.service

import id.arieridwan.sportdbsample.data.response.EventListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by arieridwan on 30/09/18.
 */

interface ApiService {

    @GET("eventspastleague.php")
    fun loadLastMatches(@Query("id") id: Int) : Call<EventListResponse>

    @GET("eventsnextleague.php")
    fun loadNextMatches(@Query("id") id: Int) : Call<EventListResponse>

}