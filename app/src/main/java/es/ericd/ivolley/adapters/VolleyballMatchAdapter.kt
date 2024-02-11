package es.ericd.ivolley.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import es.ericd.ivolley.R
import es.ericd.ivolley.dataclases.VolleyItem
import es.ericd.ivolley.dataclases.VolleyballMatchItem

class VolleyballMatchAdapter(val context: Context, val team: String, val teamFlag: String, val volleyballMatches: MutableList<VolleyballMatchItem>): RecyclerView.Adapter<VolleyballMatchAdapter.VolleyballMatchViewHolder>() {

    class VolleyballMatchViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val ivCurrentTeam = view.findViewById<ImageView>(R.id.iv_current_team)
        private val tvCurrentTeam = view.findViewById<TextView>(R.id.tv_current_team)
        private val ivOpponentTeam = view.findViewById<ImageView>(R.id.iv_opponent_team)
        private val tvOpponentTeam = view.findViewById<TextView>(R.id.tv_opponent_team)

        private val tvResult = view.findViewById<TextView>(R.id.tv_result)


        fun bindItem(context: Context, currentTeam: String, currentTeamFlag: String, volleyMatch: VolleyballMatchItem) {
            Picasso.get().load(currentTeamFlag).into(ivCurrentTeam)
            Picasso.get().load(volleyMatch.countryFlag).into(ivOpponentTeam)
            tvCurrentTeam.text = currentTeam
            tvResult.text = volleyMatch.result
            tvOpponentTeam.text = volleyMatch.opponent

            if (volleyMatch.result.equals("3:0") || volleyMatch.result.equals("3:1") || volleyMatch.result.equals("3:2")) {
                tvResult.setTextColor(ContextCompat.getColor(context, R.color.green))
            } else {
                tvResult.setTextColor(ContextCompat.getColor(context, R.color.red))
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolleyballMatchViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.volley_matches_item, parent, false)

        return VolleyballMatchViewHolder(view)
    }

    override fun getItemCount(): Int = volleyballMatches.size

    override fun onBindViewHolder(holder: VolleyballMatchViewHolder, position: Int) {
        holder.bindItem(context, team, teamFlag, volleyballMatches[position])
    }

}