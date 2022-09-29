package com.example.composeattempt

interface GameMasterRepository {
    fun checkPlayerInput(position: Int, input: ButtonValue): PlayerResult
    fun getCurrentSequence(): List<ButtonValue>
    enum class PlayerResult(val value: String) {
        CorrectGuess("You guessed correctly!"),
        CorrectSequence("Correct sequence! Starting next sequence!"),
        CorrectGame("You win!"),
        IncorrectGuess("Learn how to play Simon!")
    }
    enum class ButtonValue() {
        RED,
        GREEN,
        YELLOW,
        BLUE
    }
}