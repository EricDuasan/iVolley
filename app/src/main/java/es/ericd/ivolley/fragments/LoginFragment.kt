package es.ericd.ivolley.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import es.ericd.ivolley.R
import es.ericd.ivolley.databinding.FragmentLoginBinding
import es.ericd.ivolley.services.FirebaseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater)

        binding.btnLogin.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val user = FirebaseService.logIn(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString()
                )

            }
        }

        return binding.root
    }

}