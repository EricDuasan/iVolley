package es.ericd.ivolley.fragments

import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import es.ericd.ivolley.R
import es.ericd.ivolley.adapters.ChatAdapter
import es.ericd.ivolley.databinding.FragmentChatBinding
import es.ericd.ivolley.dataclases.Chatitem
import es.ericd.ivolley.services.FirebaseService
import es.ericd.ivolley.services.FirestoreService
import es.ericd.ivolley.utils.PreferencesUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding

    var listOfChats = mutableListOf<Chatitem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatBinding.inflate(inflater)

        binding.btnToCommitMessage.setOnClickListener {
            sendChat(binding.etMessageToSend.text.toString())
        }

        setRecyclerView()

        return binding.root
    }

    fun setRecyclerView() {

        val prefs = PreferencesUtil.getPreferences(requireContext())

        binding.recView.adapter = ChatAdapter(requireContext(), listOfChats, prefs.getString(PreferencesUtil.USERNAME, "").toString())
        binding.recView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        lifecycleScope.launch(Dispatchers.IO) {
            val chats = FirestoreService.getChats()

            withContext(Dispatchers.Main) {
                listOfChats.addAll(chats)
                binding.recView.adapter?.notifyDataSetChanged()
            }

        }

    }

    fun sendChat(message: String) {
        val prefs = PreferencesUtil.getPreferences(requireContext())

        val userUID = prefs.getString(PreferencesUtil.USERUID, null)

        if (userUID != null) {

            lifecycleScope.launch(Dispatchers.IO) {
                FirestoreService.addChat(userUID, message)

            }

        }

    }

}