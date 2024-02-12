package es.ericd.ivolley.services

import es.ericd.ivolley.adapters.VolleyballVideo
import es.ericd.ivolley.dataclases.VolleyItem
import es.ericd.ivolley.dataclases.VolleyballMatchItem
import es.ericd.ivolley.interfaces.ApiInterface
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    companion object {
        private const val BASE_URL = "https://8b1f-45-133-138-16.ngrok-free.app/"

        private var retrofit: Retrofit? = null

        private fun getRetrofit(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!

        }

        suspend fun getRanking(): List<VolleyItem> {
            val retrofit = getRetrofit()

            val call: Response<List<VolleyItem>> = retrofit.create(ApiInterface::class.java).getRanking()

            if (!call.isSuccessful) throw Exception("Something went wrong")

            return call.body()!!
        }

        suspend fun getMatches(country: String): List<VolleyballMatchItem> {
            val retrofit = getRetrofit()

            val call: Response<List<VolleyballMatchItem>> = retrofit.create(ApiInterface::class.java).getMatchesByCountry(country)

            if (!call.isSuccessful) throw Exception("Something went wrong")

            return call.body()!!
        }

        suspend fun getVideo(team1: String, team2: String): VolleyballVideo {
            val retrofit = getRetrofit()

            val call: Response<VolleyballVideo> = retrofit.create(ApiInterface::class.java).getVideo(team1, team2)

            if (!call.isSuccessful) throw Exception("Something went wrong")

            return call.body()!!

        }

    }
}