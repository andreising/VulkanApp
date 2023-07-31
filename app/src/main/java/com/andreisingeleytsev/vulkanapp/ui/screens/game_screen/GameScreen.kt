package com.andreisingeleytsev.vulkanapp.ui.screens.game_screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.andreisingeleytsev.vulkanapp.ui.utils.Fonts
import com.andreisingeleytsev.vulkanapp.R
import com.andreisingeleytsev.vulkanapp.ui.theme.MainColor
import com.andreisingeleytsev.vulkanapp.ui.theme.PrimaryColor
import com.andreisingeleytsev.vulkanapp.ui.utils.UIEvents
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GameScreen(navHostController: NavHostController, viewModel: GameViewModel = hiltViewModel()) {
    val context = LocalContext.current.applicationContext
    val sharedPreferences = context.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
    val money = sharedPreferences?.getInt("money", 5000000)
    viewModel.money.value = money!!
    val wins = sharedPreferences.getInt("wins", 0)

    val firstColumn = rememberPagerState()
    val secondColumn = rememberPagerState()
    val thirdColumn = rememberPagerState()
    val fourthColumn = rememberPagerState()
    val corout1 = rememberCoroutineScope()
    val corout2 = rememberCoroutineScope()
    val corout3 = rememberCoroutineScope()
    val corout4 = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UIEvents.OnBack -> {
                    navHostController.popBackStack()
                }

                is UIEvents.Roll -> {
                    corout1.launch {
                            firstColumn.animateScrollToPage(firstColumn.currentPage +3)
                    }
                    corout2.launch {
                            secondColumn.animateScrollToPage(secondColumn.currentPage + 3)
                    }
                    corout3.launch {
                            thirdColumn.animateScrollToPage(thirdColumn.currentPage + 3)
                    }
                    corout1.launch {
                            fourthColumn.animateScrollToPage(fourthColumn.currentPage + 3)
                        val list = listOf(
                            viewModel.listFirth[firstColumn.currentPage],
                            viewModel.listSecond[secondColumn.currentPage],
                            viewModel.listThird[thirdColumn.currentPage],
                            viewModel.listFourth[fourthColumn.currentPage]
                        )
                        Log.d("tag", list.toString())
                        var equals = 0
                        for (i in list.indices) {
                            if (i == list.size-1) continue
                            for (j in i+1 until list.size) {
                                if (list[i]==list[j]) equals++
                            }
                            if (equals>0) break
                        }
                        when (equals) {
                            0 -> {
                                viewModel.money.value = viewModel.money.value - viewModel.betSize.value
                                sharedPreferences?.edit()?.putInt("money", viewModel.money.value)?.apply()
                            }
                            1 -> {
                                viewModel.money.value = viewModel.money.value + 2*viewModel.betSize.value
                                sharedPreferences?.edit()?.putInt("money", viewModel.money.value)?.apply()
                            }
                            2 -> {
                                viewModel.money.value = viewModel.money.value + 5*viewModel.betSize.value
                                sharedPreferences?.edit()?.putInt("money", viewModel.money.value)?.apply()
                            }
                            3 -> {
                                viewModel.money.value +=5000000
                                sharedPreferences?.edit()?.putInt("money", viewModel.money.value)?.apply()
                                sharedPreferences?.edit()?.putInt("wins", wins+1)?.apply()
                            }
                        }
                    }

                }

                else -> {}
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.game_field),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 200.dp), horizontalArrangement = Arrangement.SpaceAround) {
                    VerticalPager(
                        count = viewModel.listFirth.count(),
                        state = firstColumn,
                        modifier = Modifier
                            .width(70.dp)
                            .height(200.dp)
                    ) {
                        Image(
                            painter = painterResource(id = viewModel.imgList[viewModel.listFirth[it]]),
                            contentDescription = null,
                            modifier = Modifier.size(70.dp)
                        )
                    }
                    VerticalPager(
                        count = viewModel.listSecond.count(),
                        state = secondColumn,
                        modifier = Modifier
                            .width(70.dp)
                            .height(200.dp)
                    ) {
                        Image(
                            painter = painterResource(id = viewModel.imgList[viewModel.listSecond[it]]),
                            contentDescription = null,
                            modifier = Modifier.size(70.dp)
                        )
                    }
                    VerticalPager(
                        count = viewModel.listThird.count(),
                        state = thirdColumn,
                        modifier = Modifier
                            .width(70.dp)
                            .height(200.dp)
                    ) {
                        Image(
                            painter = painterResource(id = viewModel.imgList[viewModel.listThird[it]]),
                            contentDescription = null,
                            modifier = Modifier.size(70.dp)
                        )
                    }
                    VerticalPager(
                        count = viewModel.listFourth.count(),
                        state = fourthColumn,
                        modifier = Modifier
                            .width(70.dp)
                            .height(200.dp)
                    ) {
                        Image(
                            painter = painterResource(id = viewModel.imgList[viewModel.listFourth[it]]),
                            contentDescription = null,
                            modifier = Modifier.size(70.dp)
                        )
                    }
                }
            }
            BoxWithConstraints(modifier = Modifier
                .padding(10.dp)
                .wrapContentSize()) {
                val width = maxWidth
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 7.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.btn_autoplay),
                        contentDescription = null,
                        modifier = Modifier
                            .width(width = 0.372 * width)
                            .clickable {
                                viewModel.onEvent(GameScreenEvent.AutoPlay)
                            },
                        contentScale = ContentScale.FillWidth
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 7.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.btn_max_bet),
                        contentDescription = null,
                        modifier = Modifier
                            .width(width = 0.372 * width)
                            .clickable {
                                viewModel.onEvent(GameScreenEvent.MaxBet)
                            },
                        contentScale = ContentScale.FillWidth
                    )
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.btn_spin),
                        contentDescription = null,
                        modifier = Modifier
                            .width(width = 0.29 * width)
                            .clickable {
                                viewModel.onEvent(GameScreenEvent.Spin)
                            }
                        ,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
            Row(
                Modifier
                    .padding(start = 30.dp, end = 30.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "BET", style = TextStyle(
                            fontFamily = Fonts.font,
                            color = PrimaryColor,
                            fontStyle = FontStyle.Italic,
                            fontSize = 12.sp
                        )
                    )
                    Text(
                        text = viewModel.betSize.value.toString(), style = TextStyle(
                            fontFamily = Fonts.font,
                            color = Color.White,
                            fontSize = 15.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "LEVEL", style = TextStyle(
                            fontFamily = Fonts.font,
                            color = PrimaryColor,
                            fontStyle = FontStyle.Italic,
                            fontSize = 12.sp
                        )
                    )
                    Row() {
                        Image(
                            painter = painterResource(id = R.drawable.plus),
                            contentDescription = null,
                            modifier = Modifier.width(21.dp).clickable {
                                viewModel.onEvent(GameScreenEvent.PLusBet)
                            },
                            contentScale = ContentScale.FillWidth
                        )
                        Text(
                            text = viewModel.level.value.toString(), style = TextStyle(
                                fontFamily = Fonts.font,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                        Image(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = null,
                            modifier = Modifier.width(21.dp).clickable {
                                viewModel.onEvent(GameScreenEvent.MinusBet)
                            },
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                    Color.Black
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Row() {
                        Image(
                            painter = painterResource(id = R.drawable.money_bag),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = viewModel.money.value.toString(), style = TextStyle(
                                fontFamily = Fonts.font,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                    }
                    Row() {
                        Image(
                            painter = painterResource(id = R.drawable.chips),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "10.00", style = TextStyle(
                                fontFamily = Fonts.font,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                    }
                    Row() {
                        Image(
                            painter = painterResource(id = R.drawable.cup),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = wins.toString(), style = TextStyle(
                                fontFamily = Fonts.font,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                    }
                }
            }
        }

    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
        Image(
            painter = painterResource(id = R.drawable.btn_back),
            contentDescription = null,
            modifier = Modifier
                .padding(12.dp)
                .width(100.dp)
                .clickable {
                    navHostController.popBackStack()
                },
            contentScale = ContentScale.FillWidth
        )
    }
}