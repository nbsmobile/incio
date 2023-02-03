package com.nbs.kmm.sample.android.presentation.rocketlaunch

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.nbs.kmm.sample.android.presentation.post.PostStoryActivity
import com.nbs.kmm.sample.android.theme.MyApplicationTheme

@SuppressLint("CustomSplashScreen")
class RocketLaunchActivity : ComponentActivity() {

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context, RocketLaunchActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    RocketLaunchScreen()
                }
            }
        }
    }
}