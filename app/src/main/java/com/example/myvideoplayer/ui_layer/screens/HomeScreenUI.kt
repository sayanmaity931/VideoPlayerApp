package com.example.myvideoplayer.ui_layer.screens

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FolderSpecial
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myvideoplayer.ui_layer.view_model.MyViewModel
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation", "UnrememberedAnimatable")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenUI(modifier: Modifier = Modifier,navController: NavController , viewModel: MyViewModel = hiltViewModel()) {

    val tabs = listOf(
        TabItem(
            name = "Folders",
            icon = Icons.Outlined.FolderSpecial
        ),
        TabItem(
            name = "Videos",
            icon = Icons.Outlined.Videocam
        )
    )

    val brush1 = Brush.verticalGradient(
        listOf(
            Color(0xFFE0326D),
            Color(0xFFC017DA)
        )
    )

    val brush2 = Brush.linearGradient(
        listOf(
            Color(0xFF431693),
            Color(0xFFC017DA)
        )
    )

    val pagerState = rememberPagerState(pageCount = {tabs.size})

    val scope = rememberCoroutineScope()

        Column (
        ){
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = modifier.fillMaxWidth().background(
                    brush2
                ).padding(top = 32.dp),
                containerColor = Color.Transparent
            ) {

                tabs.forEachIndexed { index, tabItem ->

                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        unselectedContentColor = Color.DarkGray,
                        selectedContentColor = Color.Black,
                        modifier = modifier.fillMaxWidth()
//                    modifier = modifier.clickable()
                    ) {
                        androidx.compose.animation.AnimatedVisibility(pagerState.currentPage == index
                            , enter = fadeIn(animationSpec = tween(500) , initialAlpha = 0.4f), exit = fadeOut(animationSpec = tween(500) , targetAlpha = 1f),
                        )
                        {
                    Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Icon(imageVector = tabItem.icon, contentDescription = null)
                            Text(text = tabItem.name)
                        }
                    }
                    }
                }
            }
            HorizontalPager(state = pagerState , modifier = Modifier.background(brush1)) {
                when (it) {
                    0 -> FoldersScreenUI(navController = navController)
                    1 -> VideosScreenUI(navController = navController,viewModel = viewModel)
                }
            }

        }
    }
data class TabItem(val name : String , val icon : ImageVector)