package es.ericd.ivolley.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
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


        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoView)
        mediaController.setAnchorView(binding.videoView)
        binding.videoView.setMediaController(mediaController)

        val packageName = "es.ericd.ivolley"

        val uri = Uri.parse("android.resource://$packageName/" + R.raw.volleyball_rules)

        binding.videoView.setVideoURI( uri )

        binding.btnPlay.setOnClickListener {
            binding.videoView.start()

        }

        return binding.root
    }


}