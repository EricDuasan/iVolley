package es.ericd.ivolley.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import es.ericd.ivolley.R
import es.ericd.ivolley.databinding.FragmentRegisterBinding
import es.ericd.ivolley.services.FirebaseService
import es.ericd.ivolley.services.FirestoreService
import es.ericd.ivolley.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    var registerInterface : RegisterInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is RegisterInterface) {
            registerInterface = context
        } else {
            throw Exception("Activity must implement RegisterInterface")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(layoutInflater)

        binding.btnLogin.setOnClickListener {
            val user = binding.etUsername.text.toString()
            val displayName = binding.etDisplayName.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordRepeat = binding.etPasswordRepeat.text.toString()

            register(user, displayName, password, passwordRepeat)

        }

        return binding.root
    }

    fun register(user: String, displayName: String, password: String, passwordRepeat: String) {
        if (!password.equals(passwordRepeat)) {
            Snackbar.make(binding.root, "Passwords don't match", Snackbar.LENGTH_LONG).show()
            return
        }

        if (!user.matches(Constants.emailRegex)) {
            Snackbar.make(binding.root, "User must be an email", Snackbar.LENGTH_LONG).show()
            return
        }

        if (displayName == "") {
            Snackbar.make(binding.root, "You must set a name", Snackbar.LENGTH_LONG).show()
            return
        }

        lifecycleScope.launch(Dispatchers.IO) {

            try {

                FirebaseService.registerUser(user, displayName, password)

                withContext(Dispatchers.Main) {

                    val bundle = bundleOf("msg" to "User created successfully")

                    registerInterface?.showMainActivity(bundle)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Snackbar.make(binding.root, "try catch " + e.message, Snackbar.LENGTH_LONG).show()
                }
            }

        }


    }

    interface RegisterInterface {
        fun showMainActivity(bundle: Bundle?)
    }

}