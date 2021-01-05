package com.example.githubusersubmission3.presentation.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return UserWidgetRemoteViewsFactory(this.applicationContext)
    }
}
