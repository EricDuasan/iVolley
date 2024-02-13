package es.ericd.ivolley.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Paint.Align
import android.opengl.Visibility
import android.text.Layout.Alignment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView
import es.ericd.ivolley.R
import es.ericd.ivolley.dataclases.Chatitem
import es.ericd.ivolley.utils.PreferencesUtil

class ChatAdapter(val context: Context, val chatList: MutableList<Chatitem>, val currentUser: String): RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){

    class ChatViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tvUsername: TextView = view.findViewById(R.id.tv_chat_user)
        private val tvMessage: TextView = view.findViewById(R.id.tv_chat_message)
        private val cardView: CardView = view.findViewById(R.id.cardView)

        fun bindItem(context: Context, chatitem: Chatitem, currentUser: String) {


            if (chatitem.username == currentUser) {
                tvUsername.visibility = View.GONE
                tvMessage.gravity = Gravity.RIGHT

                val layoutParams = cardView.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.leftMargin = 350
                cardView.layoutParams = layoutParams

            } else {
                val layoutParams = cardView.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.rightMargin = 350
                cardView.layoutParams = layoutParams

            }

            tvUsername.text = chatitem.username
            tvMessage.text = chatitem.message

            val prefs = PreferencesUtil.getPreferences(context)

            val color = prefs.getString(PreferencesUtil.COLOR, "#000000")

            tvMessage.setTextColor(Color.parseColor(color))

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false)

        return ChatViewHolder(view)

    }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bindItem(context, chatList[position], currentUser)
    }

}