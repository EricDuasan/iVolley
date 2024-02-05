package es.ericd.ivolley

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import es.ericd.ivolley.databinding.ActivityMainBinding
import es.ericd.ivolley.services.FirebaseService

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        var user = FirebaseService.getCurrentUser()

        if (user == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("error", "User not logged in")
            startActivity(intent)
            finish()
        }

        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)

        val extras = intent.extras

        if (extras != null) {
            Snackbar.make(binding.root, extras.getString("msg").toString(), Snackbar.LENGTH_LONG).show()
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

}