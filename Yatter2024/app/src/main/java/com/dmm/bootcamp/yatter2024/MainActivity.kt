package com.dmm.bootcamp.yatter2024

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import com.dmm.bootcamp.yatter2024.ui.MainApp
import com.dmm.bootcamp.yatter2024.ui.login.LoginPage
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelinePage

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Yatter2024Theme {
        Surface {
            MainApp()
        }
      }
    }
  }
}