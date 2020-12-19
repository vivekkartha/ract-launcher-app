package com.tesseract.ract

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tesseract.launchersdk.appinfo.AppInfo
import kotlinx.android.synthetic.main.row_launcher.view.*

class LauncherAdapter(private var appsList: List<AppInfo>) :
    RecyclerView.Adapter<LauncherAdapter.LaunchHolder>() {
    inner class LaunchHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_launcher, parent, false)
        return LaunchHolder(view)
    }

    override fun getItemCount(): Int = appsList.size

    override fun onBindViewHolder(holder: LaunchHolder, position: Int) {
        val appInfo = appsList[position]
        holder.itemView.apply {
            name.text = appInfo.toString()
            icon.setImageDrawable(appInfo.icon)
        }
    }

    fun updateList(appsList: List<AppInfo>) {
        this.appsList = appsList
        notifyDataSetChanged()
    }
}