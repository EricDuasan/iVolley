package es.ericd.ivolley.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import es.ericd.ivolley.R
import es.ericd.ivolley.adapters.VolleyballMatchAdapter
import es.ericd.ivolley.databases.Matches
import es.ericd.ivolley.databases.RankingDatabase
import es.ericd.ivolley.databinding.FragmentVolleyballMatchesBinding
import es.ericd.ivolley.dataclases.VolleyballMatchItem
import es.ericd.ivolley.services.ApiService
import es.ericd.ivolley.utils.PreferencesUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VolleyballMatchesFragment : Fragment() {

    lateinit var binding: FragmentVolleyballMatchesBinding

    lateinit var countryTeam: String
    lateinit var countryFlag: String
    var countryScore: Int = 0

    val matchesList = mutableListOf<VolleyballMatchItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            countryTeam = it.getString(COUNTRY, "")
            countryFlag = it.getString(FLAG, "")
            countryScore = it.getInt(SCORE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVolleyballMatchesBinding.inflate(inflater)

        Picasso.get().load(countryFlag).into(binding.ivMatchFlag)
        binding.tvMatchTeamName.text = countryTeam
        binding.tvMatchScore.text = "Score: $countryScore"

        binding.recView.adapter = VolleyballMatchAdapter(requireContext(), countryTeam, countryFlag, matchesList)
        binding.recView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        getMatches()

        return binding.root
    }

    fun getMatches() {
        lifecycleScope.launch(Dispatchers.IO) {

            try {
                val matches = ApiService.getMatches(countryTeam)

                val prefs = PreferencesUtil.getPreferences(requireContext())

                if (prefs.getBoolean(PreferencesUtil.CACHE, false)) {
                    RankingDatabase.getInstance(requireContext()).matchesDao().deleteCar(countryTeam)
                    saveData(matches)
                }

                withContext(Dispatchers.Main) {
                    matchesList.addAll(matches)
                    binding.recView.adapter?.notifyDataSetChanged()
                }

            } catch (e: Exception) {

                val dataFromCache = RankingDatabase.getInstance(requireContext()).matchesDao().getMatchesByCountry(countryTeam)

                withContext(Dispatchers.Main) {

                    dataFromCache.forEach {
                        matchesList.add(
                            VolleyballMatchItem(
                                opponent = it.opponent,
                                result = it.result,
                                countryFlag = it.opponentFlag
                            )
                        )
                    }

                    binding.recView.adapter?.notifyDataSetChanged()

                    // Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_LONG).show()
                }
            }

        }
    }

    suspend fun saveData(matches: List<VolleyballMatchItem>) {
        matches.forEach {
            RankingDatabase.getInstance(requireContext())
                .matchesDao()
                .insertMatch(
                    Matches(
                        team = countryTeam,
                        opponent = it.opponent,
                        opponentFlag = it.countryFlag,
                        result = it.result
                    )
                )
        }
    }

    companion object {

        val COUNTRY = "COUNTRY"
        val SCORE = "SCORE"
        val FLAG = "FLAG"

    }
}