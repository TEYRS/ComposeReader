package com.reader.novel

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reader.novel.ui.screens.HomeScreen
import com.reader.novel.ui.screens.ReadingScreen
import com.reader.novel.ui.theme.MyComposeApplicationTheme
import com.reader.novel.viewmodel.BookViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowFlags()
        setContent {
            MyComposeApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent()
                }
            }
        }
        setInsetsController()
    }
    
    private fun setWindowFlags() {
        
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            var systemUiVisibility = window.decorView.systemUiVisibility
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.decorView.systemUiVisibility = systemUiVisibility
        }
        
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT
    }
    
    private fun setInsetsController() {
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            
            window.decorView.post {
                window.insetsController?.setSystemBarsAppearance(
                    android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
                window.insetsController?.setSystemBarsAppearance(
                    android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                    android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                )
            }
        }
    }
}

@Composable
fun AppContent(viewModel: BookViewModel = viewModel()) {
    val isReading = remember { mutableStateOf(false) }
    
    if (isReading.value) {
        ReadingScreen(
            onBackClick = { isReading.value = false },
            viewModel = viewModel
        )
    } else {
        HomeScreen(
            onStartReading = { isReading.value = true },
            viewModel = viewModel
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    MyComposeApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen(
                onStartReading = {},
                viewModel = viewModel()
            )
        }
    }
}