package es.ericd.ivolley.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import es.ericd.ivolley.R
import es.ericd.ivolley.databinding.FragmentResetPassBinding
import es.ericd.ivolley.services.FirebaseService
import es.ericd.ivolley.utils.Constants

class ResetPassFragment : Fragment() {

    lateinit var binding: FragmentResetPassBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentResetPassBinding.inflate(inflater)

        binding.btnResetPass.setOnClickListener {
            val userEmail = binding.etUsername.text.toString()

            if (userEmail.isNullOrEmpty() || !userEmail.contains(Constants.emailRegex)) {
                Snackbar.make(binding.root, "You must set a correct email to reset the password", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            FirebaseService.resetPassword(userEmail)

            Snackbar.make(binding.root, "You will receive a password reset email if the email exists.", Snackbar.LENGTH_LONG).show()

        }

        return binding.root
    }

}