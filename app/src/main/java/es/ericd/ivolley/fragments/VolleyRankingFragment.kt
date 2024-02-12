package es.ericd.ivolley.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import es.ericd.ivolley.R
import es.ericd.ivolley.adapters.VolleyballRankingAdapter
import es.ericd.ivolley.databases.Ranking
import es.ericd.ivolley.databases.RankingDatabase
import es.ericd.ivolley.databinding.FragmentVolleyRankingBinding
import es.ericd.ivolley.dataclases.VolleyItem
import es.ericd.ivolley.services.ApiService
import es.ericd.ivolley.utils.PreferencesUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class VolleyRankingFragment : Fragment() {

    lateinit var binding: FragmentVolleyRankingBinding
    val volleyRankingList = mutableListOf<VolleyItem>()
    var volleyInterface: VolleyballRankingInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is VolleyballRankingInterface) {
            volleyInterface = context
        } else {
            throw Exception("Activity must implement VolleyballRankingInterface")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVolleyRankingBinding.inflate(inflater)

        val showMatches = { volley: VolleyItem ->
            volleyInterface?.showCountryMatches(volley)
        }

        binding.recView.adapter = VolleyballRankingAdapter(requireContext(), volleyRankingList, showMatches)
        binding.recView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        getRanking()

        return binding.root
    }

    fun getRanking() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val ranking = ApiService.getRanking()

                val prefs = PreferencesUtil.getPreferences(requireContext())


                withContext(Dispatchers.Main) {
                    if (prefs.getBoolean(PreferencesUtil.CACHE, false)) {

                        RankingDatabase.getInstance(requireContext()).rankingDao().deleteCar()
                        saveData(ranking)
                    }
                    volleyRankingList.addAll(ranking)

                    binding.recView.adapter?.notifyDataSetChanged()

                }


            } catch (e: Exception) {

                val data = RankingDatabase.getInstance(requireContext()).rankingDao().getRanking()

                withContext(Dispatchers.Main) {
                    Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_LONG).show()

                    data.forEach {
                        volleyRankingList.add(
                            VolleyItem(
                                country = it.country,
                                flag = it.flag,
                                score = it.score
                            )
                        )
                    }

                    binding.recView.adapter?.notifyDataSetChanged()

                }
            }
        }
    }

    suspend fun saveData(data: List<VolleyItem>) {
        data.forEach {

            RankingDatabase.getInstance(requireContext())
                .rankingDao()
                .insertRanking(
                    Ranking(
                        country = it.country,
                        flag = it.flag,
                        score = it.score
                    )
                )

        }
    }

    interface VolleyballRankingInterface {
        fun showCountryMatches(volleyItem: VolleyItem)
    }

}