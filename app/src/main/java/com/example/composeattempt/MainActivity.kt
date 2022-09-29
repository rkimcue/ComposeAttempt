package com.example.composeattempt

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Display
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeattempt.ui.theme.ComposeAttemptTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()
    var infoString = "Cue also supports other COVID-19 antigen tests. Go to the Virtual Care Tab to " +
            "see a doctor using antigen tests."

    var firstColor = Color.Red
    var secondColor = Color.Green
    var thirdColor = Color.Yellow
    var fourthColor = Color.Blue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isVisible by viewModel.isVisible.collectAsState()

            val text by viewModel.textviewStateFlow.collectAsState()

            val highlightedButtonStateVal by viewModel.highlightedButtonStateStateFlow.collectAsState()
            when (highlightedButtonStateVal) {
                MainViewModel.HighlightedButtonState.None -> {
                    firstColor = Color.Red
                    secondColor = Color.Green
                    thirdColor = Color.Yellow
                    fourthColor = Color.Blue
                }
                MainViewModel.HighlightedButtonState.RedHighlighted -> {
                    firstColor = Color.White
                    secondColor = Color.Green
                    thirdColor = Color.Yellow
                    fourthColor = Color.Blue
                }
                MainViewModel.HighlightedButtonState.GreenHighlighted -> {
                    firstColor = Color.Red
                    secondColor = Color.White
                    thirdColor = Color.Yellow
                    fourthColor = Color.Blue
                }
                MainViewModel.HighlightedButtonState.YellowHighlighted -> {
                    firstColor = Color.Red
                    secondColor = Color.Green
                    thirdColor = Color.White
                    fourthColor = Color.Blue
                }
                MainViewModel.HighlightedButtonState.BlueHighlighted -> {
                    firstColor = Color.Red
                    secondColor = Color.Green
                    thirdColor = Color.Yellow
                    fourthColor = Color.White
                }
                MainViewModel.HighlightedButtonState.AllHighlighted -> {
                    firstColor = Color.White
                    secondColor = Color.White
                    thirdColor = Color.White
                    fourthColor = Color.White
                }
            }

            ComposeAttemptTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        NotifyOtherAntigenSupport(isVisible, infoString, viewModel::onNotificationClose)
                        Row {
                            ButtonOne(firstColor, GameMasterRepository.ButtonValue.RED, viewModel::checkPlayerInput)
                            ButtonTwo(secondColor, GameMasterRepository.ButtonValue.GREEN, viewModel::checkPlayerInput)
                        }
                        Row {
                            ButtonThree(thirdColor, GameMasterRepository.ButtonValue.YELLOW, viewModel::checkPlayerInput)
                            ButtonFour(fourthColor, GameMasterRepository.ButtonValue.BLUE, viewModel::checkPlayerInput)
                        }
                        Textview(text)
                    }
                }
            }
        }

        Log.d("MainActivity", "Launching coroutine")
        viewModel.startView()
        Log.d("MainActivity", "Oncreate Finished")

    }
}

@Composable
fun NotifyOtherAntigenSupport(isVisible: Boolean, info: String, onClick: () -> Unit) {
    if (isVisible) {
        Card(
            backgroundColor = colorResource(id = R.color.pale_blue_10),
            modifier = Modifier.padding(16.dp),
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = null,
                    tint = colorResource(id = R.color.pale_blue),
                    modifier = Modifier.padding(8.dp, 8.dp, 12.dp)
                )
                Text(
                    text = info,
                    color = colorResource(id = R.color.pale_blue),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(270.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    tint = colorResource(id = R.color.pale_blue),
                    modifier = Modifier
                        .padding(30.dp, 8.dp, 8.dp)
                        .clickable(onClick = onClick)
                )
            }
        }
    }
}

@Composable
fun ButtonOne(firstColor: Color, buttonVal: GameMasterRepository.ButtonValue,
              onFirstButtonClick: (input: GameMasterRepository.ButtonValue) -> Unit) {
    Button(
        onClick = {onFirstButtonClick(buttonVal)},
        modifier = Modifier
            .size(width = 200.dp, height = 200.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = firstColor)
    ) {}
}

@Composable
fun ButtonTwo(secondColor: Color, buttonVal: GameMasterRepository.ButtonValue,
              onSecondButtonClick: (input: GameMasterRepository.ButtonValue) -> Unit) {
    Button(
        onClick = {onSecondButtonClick(buttonVal)},
        modifier = Modifier
            .size(width = 200.dp, height = 200.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = secondColor)
    ) {}
}

@Composable
fun ButtonThree(thirdColor: Color, buttonVal: GameMasterRepository.ButtonValue,
                onThirdButtonClick: (input: GameMasterRepository.ButtonValue) -> Unit) {
    Button(
        onClick = {onThirdButtonClick(buttonVal)},
        modifier = Modifier
            .size(width = 200.dp, height = 200.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = thirdColor)
    ) {}
}

@Composable
fun ButtonFour(fourthColor: Color, buttonVal: GameMasterRepository.ButtonValue,
               onFourthButtonClick: (input: GameMasterRepository.ButtonValue) -> Unit) {
    Button(
        onClick = {onFourthButtonClick(buttonVal)},
        modifier = Modifier
            .size(width = 200.dp, height = 200.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = fourthColor)
    ) {}
}

@Composable
fun Textview(string: String) {
    Text(text = string, modifier = Modifier.padding(8.dp))
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAttemptTheme {
        Column {
            NotifyOtherAntigenSupport(true, "Hello", {})
            Display(Color.Red, {})
        }
    }
}
 */