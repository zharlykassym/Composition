package com.tamerlan.composition.domain.repository

import com.tamerlan.composition.domain.entity.GameSettings
import com.tamerlan.composition.domain.entity.Level
import com.tamerlan.composition.domain.entity.Question

interface GameRepository {
    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}