package com.example.githubusersubmission3

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

class Utility {
    companion object {

        @JvmStatic
        fun getData(data: String?): String? {
            return if (data != null && data.isNotEmpty() && data != "null") data else "-"
        }

        fun backGroundColor(activity: Activity){
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.statusBarColor = ContextCompat.getColor(activity.applicationContext, android.R.color.transparent)
            activity.window.navigationBarColor = ContextCompat.getColor(activity.applicationContext, android.R.color.transparent)
            activity.window.setBackgroundDrawableResource(R.drawable.main_background)
        }

        fun backAct(activity: Activity){
            activity.onBackPressed()
            transitionAct(activity)
        }

        fun transitionAct(activity: Activity){
            activity.overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
        }

        fun setImageGlideFromAct(activity: Activity,urlString: String?, imageView: ImageView) {
            Glide.with(activity)
                .load(urlString)
                .into(imageView)
        }

        fun setImageGlideFromAdapter(context: Context,urlString: String?, imageView: ImageView) {
            Glide.with(context)
                .load(urlString)
                .into(imageView)
        }
    }
}