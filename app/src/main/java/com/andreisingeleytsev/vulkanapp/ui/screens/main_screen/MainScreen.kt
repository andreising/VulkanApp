package com.andreisingeleytsev.vulkanapp.ui.screens.main_screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavHostController
import com.andreisingeleytsev.vulkanapp.ui.utils.Fonts
import com.andreisingeleytsev.vulkanapp.ui.utils.Routes
import com.andreisingeleytsev.vulkanapp.R
import com.andreisingeleytsev.vulkanapp.ui.theme.MainColor
import com.andreisingeleytsev.vulkanapp.ui.theme.MainColor2
import com.andreisingeleytsev.vulkanapp.ui.theme.PrimaryColor

@Composable
fun MainScreen(navHostController: NavHostController) {
    val context = LocalContext.current.applicationContext
    val sharedPreferences = context.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
    val money = sharedPreferences?.getInt("money", 5000000)
    val wins = sharedPreferences?.getInt("wins", 0)
    var state = remember {
        mutableStateOf("main")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.main_bg_img),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
    Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
        when (state.value) {
            "main" -> {
                MainMenu(state, navHostController)
            }
            "bank" -> {
                Bank(state, money!!, wins!!)
            }
            "stats" -> {
                Stats(state, wins!!)
            }
            "info" -> {
                RulesScreen(state)
            }
        }
    }
}

@Composable
fun MainMenu(state: MutableState<String>, navHostController: NavHostController) {
    Box(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()){
        Image(
            painter = painterResource(id = R.drawable.money),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(modifier = Modifier.padding(40.dp)) {
            Image(
                painter = painterResource(id = R.drawable.btn_start),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navHostController.navigate(Routes.GAME_SCREEN)
                    },
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(12.dp))
            Image(
                painter = painterResource(id = R.drawable.btn_bank),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        state.value = "bank"
                    },
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(12.dp))
            Image(
                painter = painterResource(id = R.drawable.btn_stats),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        state.value = "stats"
                    },
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(12.dp))
            Image(
                painter = painterResource(id = R.drawable.btn_info),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        state.value = "info"
                    },
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Composable
fun Bank(state: MutableState<String>, money: Int, wins: Int) {
    Box(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                containerColor = MainColor2
            ),
            shape = RoundedCornerShape(24.dp)
            ) {
                Column(Modifier.padding(50.dp)) {
                    Row(Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.money_bag),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = money.toString(), style = TextStyle(
                                fontFamily = Fonts.font,
                                color = Color.White,
                                fontSize = 36.sp
                            )
                        )
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.chips),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "10.00", style = TextStyle(
                                fontFamily = Fonts.font,
                                color = Color.White,
                                fontSize = 36.sp
                            )
                        )
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.cup),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = wins.toString(), style = TextStyle(
                                fontFamily = Fonts.font,
                                color = Color.White,
                                fontSize = 36.sp
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Image(
                    painter = painterResource(id = R.drawable.btn_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .clickable {
                            state.value = "main"
                        },
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

@Composable
fun Stats(state: MutableState<String>, wins: Int) {
    Box(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                containerColor = MainColor2
            ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(Modifier.padding(50.dp)) {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = "MAX BALANCE", style = TextStyle(
                                fontFamily = Fonts.font,
                                color = PrimaryColor,
                                fontStyle = FontStyle.Italic,
                                fontSize = 18.sp
                            ),
                            modifier = Modifier.width(80.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "5,000,000", style = TextStyle(
                                fontFamily = Fonts.font,
                                color = Color.White,
                                fontSize = 36.sp
                            )
                        )
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = "MAX COINS", style = TextStyle(
                                fontFamily = Fonts.font,
                                color = PrimaryColor,
                                fontStyle = FontStyle.Italic,
                                fontSize = 18.sp
                            ),
                            modifier = Modifier.width(80.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "5,000,000", style = TextStyle(
                                fontFamily = Fonts.font,
                                color = Color.White,
                                fontSize = 36.sp
                            )
                        )
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = "JACKPOTS", style = TextStyle(
                                fontFamily = Fonts.font,
                                color = PrimaryColor,
                                fontStyle = FontStyle.Italic,
                                fontSize = 18.sp
                            ),
                            modifier = Modifier.width(80.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = wins.toString(), style = TextStyle(
                                fontFamily = Fonts.font,
                                color = Color.White,
                                fontSize = 36.sp
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Image(
                    painter = painterResource(id = R.drawable.btn_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .clickable {
                            state.value = "main"
                        },
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

@Composable
fun RulesScreen(state: MutableState<String>) {
    Box(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.rules),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.btn_back),
                contentDescription = null,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .clickable {
                        state.value = "main"
                    },
                contentScale = ContentScale.FillWidth
            )
        }
    }
}