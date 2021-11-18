package com.peter.eisenhowermatrix.ui

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.peter.eisenhowermatrix.R
import com.peter.eisenhowermatrix.pojo.Task
import com.peter.eisenhowermatrix.pojo.TaskType
import com.peter.eisenhowermatrix.ui.tasks_by_type.TasksAdapter
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Task>?) {
    val adapter = recyclerView.adapter as TasksAdapter
    adapter.submitList(data)
}

@BindingAdapter("txtBinder")
fun bindTextView(tv: TextView, data: String?) {
    Log.i("I'm here", "TXT_VIEW")
    tv.text = data
    Log.i("TITLE", data.toString())
}

@BindingAdapter("colorBinder")
fun bindCardBG(linearLayout: LinearLayout, data: TaskType?) {
    when (data) {
        TaskType.IMPORTANT_URGENT ->
            linearLayout.setBackgroundColor(linearLayout.context.resources.getColor(R.color.type1))

        TaskType.IMPORTANT_NOT_URGENT ->
            linearLayout.setBackgroundColor(linearLayout.context.resources.getColor(R.color.type2))

        TaskType.NOT_IMPORTANT_URGENT ->
            linearLayout.setBackgroundColor(linearLayout.context.resources.getColor(R.color.type3))

        TaskType.NOT_IMPORTANT_NOT_URGENT ->
            linearLayout.setBackgroundColor(linearLayout.context.resources.getColor(R.color.type4))

        TaskType.UNDEFINED ->
            linearLayout.setBackgroundColor(linearLayout.context.resources.getColor(R.color.type1))


    }

}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("iconBinder")
fun bindIcon(image: ImageView, isDone: Boolean?) {
        when (isDone) {
            false -> image.setImageDrawable(image.context.resources.getDrawable(R.drawable.baseline_hourglass_empty_24))
            true -> image.setImageDrawable(image.context.resources.getDrawable(R.drawable.baseline_done_all_24))
        }
}




