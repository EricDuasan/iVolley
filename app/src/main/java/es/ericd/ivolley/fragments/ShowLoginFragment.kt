package es.ericd.ivolley.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.ericd.ivolley.R
import es.ericd.ivolley.databinding.FragmentShowLoginBinding


class ShowLoginFragment : Fragment() {

    var showLoginInterface: ShowLoginInterface? = null
    lateinit var binding: FragmentShowLoginBinding

    override fun onAttach(context: Context) {
        if (context is ShowLoginInterface) {
            showLoginInterface = context
        } else {
            throw Exception("Activity must implement ShowLoginInterface")
        }

        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentShowLoginBinding.inflate(inflater)

        binding.btnShowLogin.setOnClickListener {
            showLoginInterface?.clickLogin()
        }

        binding.btnShowRegister.setOnClickListener {
            showLoginInterface?.clickRegister()
        }

        binding.btnForgotPass.setOnClickListener {
            showLoginInterface?.clickForgotPassword()
        }

        return binding.root
    }

    interface ShowLoginInterface {
        fun clickLogin()
        fun clickRegister()
        fun clickForgotPassword()
    }

}