package com.peter.eisenhowermatrix.ui.tasks_by_type

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.peter.eisenhowermatrix.databinding.TaskItemBinding
import com.peter.eisenhowermatrix.pojo.Task

class TasksAdapter (val onClickListener: OnClickListener) :
    ListAdapter<Task, TaskViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            TaskViewHolder {
        return TaskViewHolder(TaskItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(task)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }
    }
}



class TaskViewHolder(private var binding: TaskItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(property: Task) {
        binding.property = property
        binding.executePendingBindings()
    }
}

class OnClickListener(val clickListener: (property: Task) -> Unit) {
    fun onClick(task:Task) = clickListener(task)
}


