package es.ericd.ivolley.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import com.google.android.material.snackbar.Snackbar
import es.ericd.ivolley.R
import es.ericd.ivolley.databinding.FragmentLoginBinding
import es.ericd.ivolley.services.FirebaseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    var loginInterface: LoginInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is LoginInterface) {
            loginInterface = context
        } else {
            throw Exception("Activity must implement LoginInterface")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater)

        binding.btnLogin.setOnClickListener {

            val user = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (user.isNullOrEmpty() || password.isNullOrEmpty()) {
                Snackbar.make(requireView(), "You must fill both user and password.", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val user = FirebaseService.logIn(user,password)

                withContext(Dispatchers.Main) {
                    if (user != null) {
                        loginInterface?.showMainActivity(null)
                    } else {
                        Snackbar.make(requireView(), "Login error.", Snackbar.LENGTH_LONG).show()
                    }
                }

            }
        }

        return binding.root
    }

    interface LoginInterface {
        fun showMainActivity(bundle: Bundle?)
    }

}