// ListAdapter is a RecyclerView.Adapter base class for presenting List data in a RecyclerView,
// including computing diffs between Lists on a background thread.
package com.example.abdelrhman_hamdy_hyberone.ui.adapter

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.abdelrhman_hamdy_hyberone.R
import com.example.abdelrhman_hamdy_hyberone.data.models.Item
import com.example.abdelrhman_hamdy_hyberone.databinding.ListItemBinding
import com.example.abdelrhman_hamdy_hyberone.utils.DownloadStatus
import com.example.abdelrhman_hamdy_hyberone.utils.Status


class ItemsAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Item, ItemsAdapter.ViewHolder>(MyDiffUtil) {
    var items: List<Item>? = null


    // DiffUtil is a utility class that calculates the difference between two lists
    // and outputs a list of update operations that converts the first list into the second one.
    companion object MyDiffUtil : DiffUtil.ItemCallback<Item>() {

        // Checks if the two objects are the same
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        // Checks if the data between the two objects is the same
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Item?) {
            // Name
            binding.name.text = item?.name

            // Type
            item?.type.let {
                if (it.equals("PDF"))
                    binding.type.setImageResource(R.drawable.ic_pdf)
                else if (it.equals("VIDEO"))
                    binding.type.setImageResource(R.drawable.ic_video)
            }

            when (item?.status) {

                DownloadStatus.DOWNLOADING -> {
                    binding.status.visibility = View.VISIBLE
                    binding.status.text = "${item.downloadPercentage}%"

                    binding.progressBar.visibility = View.VISIBLE
                    binding.progressBar.setProgress(item.downloadPercentage)
                }

                DownloadStatus.DOWNLOADED -> {
                    binding.status.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.status.text = "downloaded"
                }

                else -> {
                    binding.status.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the list_item layout that is used to hold list item
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
//        return ViewHolder(view)

        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { onClickListener.onClick(position, item) }
        holder.bind(item)
    }


    class OnClickListener(val clickListener: (position: Int, item: Item) -> Unit) {
        fun onClick(position: Int, item: Item) = clickListener(position, item)
    }
}