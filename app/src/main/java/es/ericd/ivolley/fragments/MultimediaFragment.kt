package es.ericd.ivolley.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MediaController
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import es.ericd.ivolley.R
import es.ericd.ivolley.databinding.FragmentMultimediaBinding
import es.ericd.ivolley.interfaces.ApiInterface
import es.ericd.ivolley.services.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MultimediaFragment : Fragment() {

    lateinit var binding: FragmentMultimediaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMultimediaBinding.inflate(inflater)

        val myAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.countries, android.R.layout.simple_spinner_item)
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.team1.adapter = myAdapter
        binding.team2.adapter = myAdapter

        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoView)
        mediaController.setAnchorView(binding.videoView)
        binding.videoView.setMediaController(mediaController)

        binding.btnSearch.setOnClickListener {
            val team1 = binding.team1.selectedItem.toString()
            val team2 = binding.team2.selectedItem.toString()

            if (binding.team1.selectedItem.toString().equals(binding.team2.selectedItem.toString())) {
                Snackbar.make(binding.root, "You must select two different countries", Snackbar.LENGTH_LONG).show()
            } else {
                playVideo(team1, team2)
            }
        }


        return binding.root
    }

    fun playVideo(team: String, team2: String) {

        // val uri = Uri.parse(video)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val video = ApiService.getVideo(team, team2)

                withContext(Dispatchers.Main) {
                    binding.videoView.setVideoURI( Uri.parse(video.matchUrl) )
                    binding.videoView.start()

                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_LONG).show()

                }
            }

        }



    }

}