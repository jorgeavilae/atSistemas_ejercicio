package com.example.atsistemas_ejercicio.splash_activity.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.atsistemas_ejercicio.commons.BaseActivity
import com.example.atsistemas_ejercicio.commons.Constants
import com.example.atsistemas_ejercicio.databinding.ActivitySplashBinding
import com.example.atsistemas_ejercicio.home_activity.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        object : Thread() {
//            override fun run() {
//                try {
//                    sleep(Constants.LOADING_SLEEP)
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                } finally {
//                    startActivity<HomeActivity>()
//                    finish()
//                }
//            }
//        }.start()

        lifecycleScope.launch {
            delay(Constants.LOADING_SLEEP)
            startActivity<HomeActivity>()
            finish()
        }

    }
}