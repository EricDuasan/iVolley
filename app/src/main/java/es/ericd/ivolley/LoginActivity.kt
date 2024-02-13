package es.ericd.ivolley

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import es.ericd.ivolley.databinding.ActivityLoginBinding
import es.ericd.ivolley.fragments.LoginFragment
import es.ericd.ivolley.fragments.RegisterFragment
import es.ericd.ivolley.fragments.ResetPassFragment
import es.ericd.ivolley.fragments.ShowLoginFragment
import es.ericd.ivolley.services.FirebaseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.Manifest

class LoginActivity : AppCompatActivity(), ShowLoginFragment.ShowLoginInterface, LoginFragment.LoginInterface, RegisterFragment.RegisterInterface {

    lateinit var binding: ActivityLoginBinding
    val NOTIFICATION_REQUEST_CODE = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setNotification()

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val extras = intent.extras

        if (extras != null) {
            Snackbar.make(binding.root, extras.getString("error").toString(), Snackbar.LENGTH_LONG).show()
        }

    }

    fun setNotification() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                NOTIFICATION_REQUEST_CODE
            )
        }
    }

    override fun clickLogin() {
        supportFragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace<LoginFragment>(binding.fragmentContainerView.id)
        }
    }

    override fun clickRegister() {
        supportFragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace<RegisterFragment>(binding.fragmentContainerView.id)
        }
    }

    override fun clickForgotPassword() {
        supportFragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace<ResetPassFragment>(binding.fragmentContainerView.id)
        }
    }

    override fun showMainActivity(bundle: Bundle?) {

        val intent = Intent(this, MainActivity::class.java)

        if (bundle != null) {
            intent.putExtra("msg", bundle.getString("msg"))
        }

        startActivity(intent)
        finish()

    }
}