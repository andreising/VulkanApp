package com.andreisingeleytsev.vulkanapp.ui.screens.game_screen

sealed class GameScreenEvent {
    object OnBack: GameScreenEvent()
    object AutoPlay: GameScreenEvent()
    object Spin: GameScreenEvent()
    object MaxBet: GameScreenEvent()
    object PLusBet: GameScreenEvent()
    object MinusBet: GameScreenEvent()
}
