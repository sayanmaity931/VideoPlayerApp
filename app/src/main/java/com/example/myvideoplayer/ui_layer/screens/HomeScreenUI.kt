package com.example.myvideoplayer.ui_layer.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myvideoplayer.ui_layer.view_model.MyViewModel
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
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

    val pagerState = rememberPagerState(pageCount = {tabs.size})

    val scope = rememberCoroutineScope()

        Column {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = modifier.fillMaxWidth()
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
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(imageVector = tabItem.icon, contentDescription = null)
                            Text(text = tabItem.name)
                        }
                    }
                }
            }
            HorizontalPager(state = pagerState) {
                when (it) {
                    0 -> FoldersScreenUI()
                    1 -> VideosScreenUI(navController = navController,viewModel = viewModel)
                }
            }

        }
    }
data class TabItem(val name : String , val icon : ImageVector)