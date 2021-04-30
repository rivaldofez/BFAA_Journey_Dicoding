package com.rivaldofez.cubihub.service

import android.content.Intent
import android.widget.RemoteViewsService
import com.rivaldofez.cubihub.widget.WidgetRemoteViewsFactory

class WidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        WidgetRemoteViewsFactory(this.applicationContext)
}
