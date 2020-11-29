package ca.khiraish.instagramclone.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import ca.khiraish.instagramclone.R
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_navigation)
        val navHost: NavHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_fragment) as NavHostFragment
        val navController = navHost.navController
        //NavigationUI.setupWithNavController(bottomNavigationView, navController);
        bottomNav.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener setOnNavigationItemSelectedListener@{ menuItem: MenuItem ->
            when (R.id.nav_add) {
                menuItem.itemId -> {
                    Log.d(TAG, "onOptionsItemSelected: add clicked")
                    Toast.makeText(this, "add clicked", Toast.LENGTH_SHORT).show()
                    //startCropImageActivity()
                }
                else -> {
                    Log.d(TAG, "onOptionsItemSelected: else clicked")
                    Toast.makeText(this, "else clicked", Toast.LENGTH_SHORT).show()
                    return@setOnNavigationItemSelectedListener NavigationUI.onNavDestinationSelected(
                        menuItem, navController) || super.onOptionsItemSelected(menuItem)
                }
            }
            return@setOnNavigationItemSelectedListener super.onOptionsItemSelected(menuItem)
        })

    }

}