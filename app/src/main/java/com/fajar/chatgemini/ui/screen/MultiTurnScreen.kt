package com.fajar.chatgemini.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.fajar.chatgemini.ApiType
import com.fajar.chatgemini.ui.component.ConversationArea
import com.fajar.chatgemini.ui.component.TypingArea
import com.fajar.chatgemini.ui.data.MainViewModel
import com.fajar.chatgemini.ui.navigation.DrawerNav
import com.fajar.chatgemini.ui.navigation.MainTopBar
import com.fajar.chatgemini.ui.navigation.items
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MultiTurnScreen(
    viewModel: MainViewModel,
    navController: NavHostController
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    val currentRole = navController.currentBackStackEntry?.destination?.route?:""
    viewModel.makeHomeVisit()

    selectedItemIndex = items.indexOfFirst { it.title == currentRole }

    ModalNavigationDrawer(
        drawerContent = {
               DrawerNav(
                   selectedItemIndex = selectedItemIndex,
                   onItemSelect = {selectedItemIndex = it},
                   onCloseDrawer = { scope.launch { drawerState.close() } },
                   navController = navController
               )
        },
        drawerState = drawerState
        ) {
            Scaffold(
                topBar = { MainTopBar(scope , drawerState )}
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = it.calculateTopPadding())
                        .fillMaxSize()
                        .fillMaxHeight(1f)
                        .background(MaterialTheme.colorScheme.background)
                ) {

                    Box(
                        modifier = Modifier.weight(1f)
                    ){
                        ConversationArea(viewModel , apiType = ApiType.MULTI_CHAT )
                    }

                    TypingArea(
                        viewModel = viewModel,
                        apiType = ApiType.MULTI_CHAT,
                        bitmaps = null,
                        galleryLauncher = null,
                        permissionLauncher = null
                    )

                }

            }

    }
}