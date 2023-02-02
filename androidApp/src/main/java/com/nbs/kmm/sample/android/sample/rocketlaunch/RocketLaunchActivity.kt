package com.nbs.kmm.sample.android.sample.rocketlaunch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.nbs.kmm.sample.android.sample.rocketlaunch.RocketLaunchScreen
import com.nbs.kmm.sample.android.theme.MyApplicationTheme

class RocketLaunchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RocketLaunchScreen()
                }
            }
        }
    }
}