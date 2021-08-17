package com.submission.githubuserapi.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.submission.githubuserapi.R
import com.submission.githubuserapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = getString(R.string.home)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment

        navController = navHostFragment.findNavController()
        setSupportActionBar(binding.toolbar)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favoriteActivity -> navController.navigate(R.id.action_nav_home_to_favoriteActivity)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}