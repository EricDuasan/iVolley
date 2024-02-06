package es.ericd.ivolley.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.ericd.ivolley.R
import es.ericd.ivolley.dataclases.Chatitem

class ChatAdapter(val context: Context, val chatList: MutableList<Chatitem>): RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){

    class ChatViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tvUsername: TextView = view.findViewById(R.id.tv_chat_user)
        private val tvMessage: TextView = view.findViewById(R.id.tv_chat_message)

        fun bindItem(chatitem: Chatitem) {
            tvUsername.text = chatitem.username
            tvMessage.text = chatitem.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false)

        return ChatViewHolder(view)

    }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bindItem(chatList[position])
    }

}