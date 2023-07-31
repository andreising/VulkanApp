package com.andreisingeleytsev.vulkanapp.ui.screens.game_screen

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreisingeleytsev.vulkanapp.R
import com.andreisingeleytsev.vulkanapp.ui.utils.UIEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class GameViewModel: ViewModel() {
    private val _uiEvent = Channel<UIEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: GameScreenEvent){
        when(event) {
            is GameScreenEvent.MaxBet -> {
                level.value = 10
                betSize.value = 10000
            }
            is GameScreenEvent.OnBack -> {
                sendUIEvent(UIEvents.OnBack)
            }
            is GameScreenEvent.AutoPlay -> {
                isTick = if (isTick) {
                    timer.cancel()
                    false
                } else {
                    timer.start()
                    true
                }
            }
            is GameScreenEvent.Spin -> {
                if (money.value>=betSize.value) sendUIEvent(UIEvents.Roll)
            }
            is GameScreenEvent.PLusBet -> {
                if (level.value!=10) level.value++
                betSize.value = when (level.value) {
                    1 -> {
                        100
                    }

                    2 -> {
                        250
                    }

                    3 -> {
                        500
                    }

                    4 -> {
                        1000
                    }

                    5 -> {
                        2000
                    }

                    6 -> {
                        5000
                    }

                    7 -> {
                        10000
                    }

                    8 -> {
                        25000
                    }

                    9 -> {
                        50000
                    }

                    10 -> {
                        10000
                    }

                    else -> {
                        100
                    }
                }
            }
            is GameScreenEvent.MinusBet -> {
                if (level.value!=1) level.value--
                betSize.value = when (level.value) {
                    1 -> {
                        100
                    }

                    2 -> {
                        250
                    }

                    3 -> {
                        500
                    }

                    4 -> {
                        1000
                    }

                    5 -> {
                        2000
                    }

                    6 -> {
                        5000
                    }

                    7 -> {
                        10000
                    }

                    8 -> {
                        25000
                    }

                    9 -> {
                        50000
                    }

                    10 -> {
                        10000
                    }

                    else -> {
                        100
                    }
                }
            }
        }
    }
    private fun sendUIEvent(event: UIEvents){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
    val imgList = listOf(
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_4,
        R.drawable.img_5,
        R.drawable.img_6,
        R.drawable.img_7,
        R.drawable.img_8,
        R.drawable.img_9,
        R.drawable.img_10,
        R.drawable.img_11,
        R.drawable.img_12,
    )
    val listFirth = mutableListOf<Int>().also {
            for (i in 0..1000) {
                it.add((0..11).random())
            }
        }
    val listSecond = mutableListOf<Int>().also {
        for (i in 0..1000) {
            it.add((0..11).random())
        }
    }
    val listThird = mutableListOf<Int>().also {
        for (i in 0..1000) {
            it.add((0..11).random())
        }
    }
    val listFourth = mutableListOf<Int>().also {
        for (i in 0..1000) {
            it.add((0..11).random())
        }
    }
    val money = mutableStateOf(5000000)
    val level = mutableStateOf(1)
    val betSize = mutableStateOf(
        100
    )
    var isTick = false
    val timer = object : CountDownTimer(2000000, 2000) {
        override fun onTick(p0: Long) {
            if (money.value>=betSize.value) sendUIEvent(UIEvents.Roll)
            else {
                isTick = false
                cancel()
            }
        }

        override fun onFinish() {

        }

    }

}