package com.dicoding.asclepius.view.activities

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.NetworkUtil
import com.dicoding.asclepius.helper.NetworkUtil.isNetworkAvailable
import com.dicoding.asclepius.view.dialogs.NetworkDialog
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var networkChangeReceiver: NetworkUtil.NetworkChangeReceiver

    private var networkDialog: NetworkDialog? = null
    private var dataRefreshListener: NetworkChangeListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav: BottomNavigationView = binding.bottomNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)

        binding.fab.setOnClickListener {
            val intent = Intent(this@MainActivity, CancerActivity::class.java)
            startActivity(intent)
        }

        networkDialog = NetworkDialog(this)
        setupNetworkChangeReceiver()
    }

    private fun setupNetworkChangeReceiver() {
        networkChangeReceiver = NetworkUtil.NetworkChangeReceiver { isConnected ->
            if (!isConnected) {
                networkDialog?.showNoInternetDialog {
                    if (isNetworkAvailable(this)) {
                        networkDialog?.dismissDialog()
                        dataRefreshListener?.onNetworkChanged()
                    } else {
                        Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                networkDialog?.dismissDialog()
                dataRefreshListener?.onNetworkChanged()
            }
        }
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        networkDialog?.dismissDialog()
    }

    interface NetworkChangeListener {
        fun onNetworkChanged()
    }

}