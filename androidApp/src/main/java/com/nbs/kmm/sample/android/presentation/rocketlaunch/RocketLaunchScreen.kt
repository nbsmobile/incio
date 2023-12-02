package com.nbs.kmm.sample.android.presentation.rocketlaunch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.nbs.kmm.sample.android.R
import com.nbs.kmm.sample.android.utils.ComposableObserver
import com.nbs.kmm.sample.android.utils.ContextProvider
import com.nbs.kmm.sample.android.utils.showToast
import com.nbs.kmm.shared.domain.rocketlaunch.model.RocketLaunch
import org.koin.androidx.compose.getViewModel

@Composable
fun RocketLaunchScreen() {
    val rocketLaunchViewModel = getViewModel<RocketLaunchViewModel>()

    LaunchedEffect(Unit) {
        rocketLaunchViewModel.getRocketLaunches()
    }

    val rocketLaunchState = rocketLaunchViewModel.rocketLaunchResults.observeAsState()

    val removeRocketLaunchState = rocketLaunchViewModel.removeRocketLaunchResult.observeAsState()

    val rocketLaunches = remember { mutableStateOf(listOf<RocketLaunch>()) }

    ComposableObserver(state = removeRocketLaunchState, onLoading = {}, onSuccess = {
        showToast(stringResource(R.string.remove_all_launch_success))
        rocketLaunches.value = listOf()

        Button(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp, bottom = 12.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(
                    ContextCompat.getColor(
                        ContextProvider.get(),
                        R.color.colorPrimary
                    )
                )
            ),
            onClick = {
                rocketLaunchViewModel.getRocketLaunches()
            }
        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize(),
                text = stringResource(R.string.get_all_rocket_launches),
                style = TextStyle.Default.copy(color = Color.White, fontWeight = FontWeight.Medium),
                textAlign = TextAlign.Center
            )
        }
    }, onFailure = {
        showToast(it.message.toString())
    })

    ComposableObserver(state = rocketLaunchState, onLoading = {
        CircularProgressIndicator(
            modifier = Modifier
                .size(100.dp)
                .padding(100.dp),
            color = Color(ContextCompat.getColor(ContextProvider.get(), R.color.colorPrimary))
        )
    }, onSuccess = {
        rocketLaunches.value = it
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
        ) {
            items(rocketLaunches.value) {
                Card(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    backgroundColor = Color(
                        ContextCompat.getColor(
                            ContextProvider.get(),
                            R.color.colorPrimary
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        text = it.toString(),
                        style = TextStyle.Default.copy(color = Color.White, fontWeight = FontWeight.Medium)
                    )
                }
            }

            item {
                Button(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp, bottom = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(
                            ContextCompat.getColor(
                                ContextProvider.get(),
                                R.color.colorPrimary
                            )
                        )
                    ),
                    onClick = {
                        rocketLaunchViewModel.removeRocketLaunches()
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.remove_all_rocket_launches_from_database),
                        style = TextStyle.Default.copy(color = Color.White, fontWeight = FontWeight.Medium),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }, onFailure = {
        Card(
            modifier = Modifier
                .wrapContentSize()
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