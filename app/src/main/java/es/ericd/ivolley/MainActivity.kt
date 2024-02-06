package es.ericd.ivolley

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import es.ericd.ivolley.databinding.ActivityMainBinding
import es.ericd.ivolley.fragments.InformationFragment
import es.ericd.ivolley.services.FirebaseService
import es.ericd.ivolley.utils.Constants

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        checkIfUserIsLogged()

        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)

        val extras = intent.extras

        if (extras != null) {
            Snackbar.make(binding.root, extras.getString("msg").toString(), Snackbar.LENGTH_LONG).show()
        }

        setDefaultUsernameOnPreferences()

    }

    fun checkIfUserIsLogged() {
        var user = FirebaseService.getCurrentUser()

        if (user == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("error", "User not logged in")
            startActivity(intent)
            finish()
        }
    }

    fun setDefaultUsernameOnPreferences() {
        val prefs = getSharedPreferences(Constants.preferences, Context.MODE_PRIVATE)

        val username = prefs.getString("username", null)

        if (username == null || username == "") {
            with(prefs.edit()) {
                putString("username", FirebaseService.getCurrentUser()!!.email)
                commit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.menu_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.navbottom_btn_info -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<InformationFragment>(binding.fragmentContainerView.id)
                }
                true
            }
            R.id.navbottom_btn_ranking -> true
            R.id.navbottom_btn_chat -> true
            R.id.navbottom_btn_media -> true
            else -> false
        }

    }


}