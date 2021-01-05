package com.example.githubusersubmission3.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.githubusersubmission3.Constants.Companion.EXTRA_USERNAME
import com.example.githubusersubmission3.Constants.Companion.IS_ABOUT_US
import com.example.githubusersubmission3.R
import com.example.githubusersubmission3.Utility
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Utility.backGroundColor(this)

        searchActivity.setOnClickListener(this)
        favoriteActivity.setOnClickListener(this)
        settingActivity.setOnClickListener(this)
        aboutActivity.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.searchActivity -> intent(SearchActivity::class.java)
            R.id.favoriteActivity -> intent(FavoriteActivity::class.java)
            R.id.aboutActivity -> {
                val about = Intent(this, DetailActivity::class.java)
                about.putExtra(EXTRA_USERNAME, "haekalzain")
                about.putExtra(IS_ABOUT_US,true)
                startActivity(about)
                overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
            }
            R.id.settingActivity -> intent(SettingActivity::class.java)
        }
    }

    private fun intent(nextAct: Class<*>) {
        startActivity(Intent(this, nextAct))
        Utility.transitionAct(this)
    }
}