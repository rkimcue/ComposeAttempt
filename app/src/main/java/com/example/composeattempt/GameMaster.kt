package com.example.composeattempt

import com.example.composeattempt.GameMasterRepository.ButtonValue
import kotlin.random.Random

class GameMaster : GameMasterRepository {
    private var gameSequence: List<ButtonValue> =
        Array<ButtonValue>(4) {
            ButtonValue.values()[Random.nextInt(0,3)]
        }.asList()
    private var playerPosition = 0

    override fun checkPlayerInput(position: Int, input: ButtonValue): GameMasterRepository.PlayerResult {

        if (position > 3) {
            throw Exception("Position Too High")
        }
        else if (position < 0) {
            throw Exception("Position Too Low")
        }
        else if (position > playerPosition) {
            throw Exception("Position Invalid")
        }

        else if (gameSequence[position] == input) {
            if (position == playerPosition) {
                if (playerPosition + 1 == gameSequence.size) {
                    return GameMasterRepository.PlayerResult.CorrectGame
                }
                else {
                    playerPosition++
                    return GameMasterRepository.PlayerResult.CorrectSequence
                }
            }
            return GameMasterRepository.PlayerResult.CorrectGuess
        }
        else {
            return GameMasterRepository.PlayerResult.IncorrectGuess
        }
    }
    override fun getCurrentSequence(): List<ButtonValue> {
        return gameSequence.subList(0, playerPosition+1)
    }
}