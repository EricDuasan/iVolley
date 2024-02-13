package es.ericd.ivolley.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import es.ericd.ivolley.R
import es.ericd.ivolley.dataclases.VolleyItem

class VolleyballRankingAdapter(val context: Context, val volleyballList: MutableList<VolleyItem>, val showCountryMatches: (volley: VolleyItem) -> Unit?): RecyclerView.Adapter<VolleyballRankingAdapter.VolleyballRankingViewHolder>() {

    class VolleyballRankingViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tvTop = view.findViewById<TextView>(R.id.tv_top)
        private val ivFlag = view.findViewById<ImageView>(R.id.iv_flag)

        fun bindItem(position: Int, volley: VolleyItem) {
            tvTop.text = "# " + (position + 1)
            Picasso.get().load(volley.flag).into(ivFlag)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolleyballRankingViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.volleyball_ranking_item, parent, false)

        return VolleyballRankingViewHolder(view)
    }

    override fun getItemCount(): Int = volleyballList.size

    override fun onBindViewHolder(holder: VolleyballRankingViewHolder, position: Int) {
        holder.bindItem(position, volleyballList[position])
        holder.itemView.setOnClickListener { showCountryMatches(volleyballList[position]) }

    }

}