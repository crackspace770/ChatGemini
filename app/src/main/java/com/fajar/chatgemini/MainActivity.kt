package com.fajar.chatgemini

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.fajar.chatgemini.ui.data.MainViewModel
import com.fajar.chatgemini.ui.data.MessageDatabase
import com.fajar.chatgemini.ui.navigation.MultiTurnMode
import com.fajar.chatgemini.ui.navigation.MyNavigation
import com.fajar.chatgemini.ui.navigation.SetApi
import com.fajar.chatgemini.ui.theme.ChatGeminiTheme
import com.fajar.chatgemini.utils.datastore
import com.fajar.chatgemini.utils.getApiKey
import kotlinx.coroutines.runBlocking


@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(applicationContext, MessageDatabase::class.java, "message.db").build()
    }

    private val viewModel by viewModels<MainViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(db.dao) as T
                }
            }
        }
    )


    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ChatGemini)
        super.onCreate(savedInstanceState)
        setContent {
            ChatGeminiTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if (runBlocking {
                            val key = applicationContext.datastore.getApiKey()
                            if (key.isNotEmpty()) {
                                viewModel.updateApiKey(key)
                            }
                            key.isEmpty()
                        }) {
                        MyNavigation(viewModel = viewModel, startDestination = SetApi.route)
                    } else {
                        MyNavigation(viewModel = viewModel, startDestination = MultiTurnMode.route)
                    }
                }
            }
        }
    }
}


enum class ApiType {
    SINGLE_CHAT,
    MULTI_CHAT,
    IMAGE_CHAT
}

enum class Mode {
    USER,
    GEMINI
}