package com.example.mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvm.databinding.ItemLayoutBinding
import com.example.mvvm.model.Job

class JobAdapter : ListAdapter<Job, JobAdapter.JobViewHolder>(DiffCallBack()) {
    lateinit var onClick: (Job) -> Unit

    private class DiffCallBack : DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        return JobViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class JobViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(job: Job) {
            binding.apply {
                Glide.with(ivCompanyLogo)
                    .load(job.companyLogoUrl)
                    .into(ivCompanyLogo)

                tvJobTitle.text = job.title
                tvDate.text = job.publicationDate
                tvJobType.text = job.jobType
                tvJobLocation.text = job.candidateRequiredLocation
                tvCompanyName.text = job.companyName
            }
            itemView.setOnClickListener {
                onClick.invoke(job)
            }
        }
    }
}