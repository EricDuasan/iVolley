package es.ericd.ivolley.interfaces

import es.ericd.ivolley.dataclases.VolleyItem
import es.ericd.ivolley.dataclases.VolleyballMatchItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/")
    suspend fun getRanking(): Response<List<VolleyItem>>

    @GET("matches")
    suspend fun getMatchesByCountry(@Query("country") country: String): Response<List<VolleyballMatchItem>>
}