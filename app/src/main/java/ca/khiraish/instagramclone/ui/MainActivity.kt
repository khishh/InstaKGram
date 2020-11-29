package ca.khiraish.instagramclone.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import ca.khiraish.instagramclone.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.theartofdev.edmodo.cropper.CropImage
import dagger.android.support.DaggerAppCompatActivity

private const val TAG = "MainActivity"
class MainActivity : DaggerAppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_navigation)
        val navHost: NavHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_fragment) as NavHostFragment
        navController = navHost.navController
        bottomNav.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener setOnNavigationItemSelectedListener@{ menuItem: MenuItem ->
            when (R.id.nav_add) {
                menuItem.itemId -> {startCropImageActivity() }
                else -> {
                    return@setOnNavigationItemSelectedListener NavigationUI.onNavDestinationSelected(
                        menuItem, navController) || super.onOptionsItemSelected(menuItem) }
            }
            return@setOnNavigationItemSelectedListener super.onOptionsItemSelected(menuItem)
        })

    }

    private fun startCropImageActivity() {
        CropImage.activity().start(this@MainActivity)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result: CropImage.ActivityResult = CropImage.getActivityResult(data) ?: return
            if (resultCode == Activity.RESULT_OK) {
                val imageUri: Uri = result.uri
                val bundle = Bundle()
                bundle.putString("imageUri", imageUri.toString())
                navController.navigate(R.id.nav_add, bundle)
            }
        }
    }

}