package com.tamerlan.composition.data

import com.tamerlan.composition.domain.entity.GameResult
import com.tamerlan.composition.domain.entity.GameSettings
import com.tamerlan.composition.domain.entity.Level
import com.tamerlan.composition.domain.entity.Question
import com.tamerlan.composition.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {
    private const val MIN_SUM_SIZE = 2
    private const val MIN_VISIBLE_NUMBER_SIZE = 1
    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_SIZE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_VISIBLE_NUMBER_SIZE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_VISIBLE_NUMBER_SIZE)
        val to = min(maxSumValue, rightAnswer + countOfOptions)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> {
                GameSettings(
                    10,
                    3,
                    50,
                    8
                )
            }
            Level.EASY -> {
                GameSettings(10,10,70,60)
            }
            Level.NORMAL-> {
                GameSettings(20,20,80,40)
            }
            Level.HARD-> {
                GameSettings(30,30,90,40)
            }
        }
    }
}