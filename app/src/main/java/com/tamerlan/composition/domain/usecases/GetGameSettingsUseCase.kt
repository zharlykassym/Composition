package com.tamerlan.composition.domain.usecases

import com.tamerlan.composition.domain.entity.GameSettings
import com.tamerlan.composition.domain.entity.Level
import com.tamerlan.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {
    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}