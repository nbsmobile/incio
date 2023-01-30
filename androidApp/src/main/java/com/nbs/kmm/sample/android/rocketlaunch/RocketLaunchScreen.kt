package com.nbs.kmm.sample.android.rocketlaunch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nbs.kmm.sample.android.utils.ComposableObserver
import com.nbs.kmm.sample.android.utils.showToast
import org.koin.androidx.compose.getViewModel

@Composable
fun RocketLaunchScreen() {
    val rocketLaunchViewModel = getViewModel<RocketLaunchViewModel>()

    LaunchedEffect(Unit) {
        rocketLaunchViewModel.getRocketLaunches()
    }

    val rocketLaunchState = rocketLaunchViewModel.rocketLaunchResults.observeAsState()

    val removeRocketLaunchState = rocketLaunchViewModel.removeRocketLaunchResult.observeAsState()

    ComposableObserver(state = removeRocketLaunchState, onLoading = {}, onSuccess = {
        showToast("Remove all launches success")
    }, onFailure = {
        showToast(it.message.toString())
    })

    ComposableObserver(state = rocketLaunchState, onLoading = {
        CircularProgressIndicator(
            modifier = Modifier
                .size(100.dp)
                .padding(100.dp)
        )
    }, onSuccess = {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
        ) {
            items(it) {
                Card(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    backgroundColor = Color.LightGray,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        text = it.toString(),
                        style = TextStyle.Default.copy(color = Color.Black)
                    )
                }
            }

            item {
                Button(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp, bottom = 12.dp),
                    onClick = {
                        rocketLaunchViewModel.removeRocketLaunches()
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Remove All Launches from Database",
                        style = TextStyle.Default.copy(color = Color.Black),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }, onFailure = {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    rocketLaunchViewModel.getRocketLaunches()
                },
            backgroundColor = Color.LightGray,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable {
                        rocketLaunchViewModel.getRocketLaunches()
                    },
                text = "error $it"
            )
        }
    })
}