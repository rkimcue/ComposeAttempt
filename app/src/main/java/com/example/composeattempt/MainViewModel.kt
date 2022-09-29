package com.example.composeattempt

import android.util.Log
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val gameMaster: GameMasterRepository,
): ViewModel() {
    val isVisible: MutableStateFlow<Boolean> = MutableStateFlow(true)

    private val textviewMutableStateFlow = MutableStateFlow<String>("Welcome to Simon!")
    val textviewStateFlow: StateFlow<String> = textviewMutableStateFlow
    private var playerPosition = 0
    private val highlightedButtonStateMutableStateFlow = MutableStateFlow<HighlightedButtonState>(
        HighlightedButtonState.None)
    val highlightedButtonStateStateFlow: StateFlow<HighlightedButtonState> = highlightedButtonStateMutableStateFlow

    enum class HighlightedButtonState {
        None,
        RedHighlighted,
        GreenHighlighted,
        YellowHighlighted,
        BlueHighlighted,
        AllHighlighted,
    }

    fun onNotificationClose() {
        isVisible.value = !isVisible.value
    }

    private suspend fun animateSequence() {
        val sequence = gameMaster.getCurrentSequence()
        Log.d("mainActivity", "Sequence $sequence")
        sequence.forEach { item ->
            when (item) {
                GameMasterRepository.ButtonValue.RED -> animateCurrentButton(HighlightedButtonState.RedHighlighted)
                GameMasterRepository.ButtonValue.GREEN -> animateCurrentButton(HighlightedButtonState.GreenHighlighted)
                GameMasterRepository.ButtonValue.YELLOW -> animateCurrentButton(HighlightedButtonState.YellowHighlighted)
                GameMasterRepository.ButtonValue.BLUE -> animateCurrentButton(HighlightedButtonState.BlueHighlighted)
            }
            delay(500)
        }
    }
    private suspend fun animateCurrentButton(buttonState: HighlightedButtonState) {
        Log.d("MainActivity", "Animate Button")
        highlightedButtonStateMutableStateFlow.update{buttonState}
        delay(1000)
        highlightedButtonStateMutableStateFlow.update{HighlightedButtonState.None}
        Log.d("MainActivity", "Return to Original Color")
    }
    fun startView() {
        viewModelScope.launch {
            delay(3000)
            animateSequence()
        }
    }

    fun checkPlayerInput(guess: GameMasterRepository.ButtonValue) {
        val guessResult = gameMaster.checkPlayerInput(playerPosition, guess)
        textviewMutableStateFlow.value = guessResult.value

        when (guessResult) {
            GameMasterRepository.PlayerResult.CorrectGuess -> playerPosition++
            GameMasterRepository.PlayerResult.CorrectSequence -> {
                playerPosition = 0
                viewModelScope.launch {
                    delay(500)
                    animateSequence()
                }
            }
            GameMasterRepository.PlayerResult.CorrectGame -> {
                highlightedButtonStateMutableStateFlow.value = HighlightedButtonState.AllHighlighted
            }
            GameMasterRepository.PlayerResult.IncorrectGuess -> {
                playerPosition = 0
                viewModelScope.launch {
                    delay(500)
                    animateSequence()
                }
            }
        }
    }
}