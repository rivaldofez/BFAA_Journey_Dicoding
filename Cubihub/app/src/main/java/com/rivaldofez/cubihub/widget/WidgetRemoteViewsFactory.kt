package com.rivaldofez.cubihub.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.rivaldofez.cubihub.BannerWidget
import com.rivaldofez.cubihub.R
import com.rivaldofez.cubihub.database.DetailUserDatabase
import com.rivaldofez.cubihub.helper.toListUser
import com.rivaldofez.cubihub.model.DetailUser

internal class WidgetRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {
    private var favoriteUser : List<DetailUser> = listOf()
    private var cursor: Cursor? = null

    override fun onDataSetChanged() {

       val identityToken = Binder.clearCallingIdentity()
       cursor = context.contentResolver.query(DetailUserDatabase.CONTENT_URI, null,null, null, null)
       cursor?.let {
           favoriteUser = it.toListUser()
       }
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int = favoriteUser.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_item)
        var bitmap = Glide.with(context).asBitmap()
            .load(favoriteUser[position].avatar_url)
            .submit()
            .get()

        rv.setImageViewBitmap(R.id.imageView, bitmap)
        val extras = bundleOf(
            BannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}