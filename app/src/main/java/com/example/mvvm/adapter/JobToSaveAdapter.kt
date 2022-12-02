package com.example.mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvm.databinding.ItemLayoutBinding
import com.example.mvvm.model.JobToSave

class JobToSaveAdapter : ListAdapter<JobToSave, JobToSaveAdapter.JobViewHolder>(DiffCallBack()) {
    lateinit var onClick: (JobToSave) -> Unit
    lateinit var onDeleteClick: (JobToSave) -> Unit

    private class DiffCallBack : DiffUtil.ItemCallback<JobToSave>() {
        override fun areItemsTheSame(oldItem: JobToSave, newItem: JobToSave): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: JobToSave, newItem: JobToSave): Boolean {
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
        fun bind(job: JobToSave) {
            binding.apply {
                Glide.with(ivCompanyLogo)
                    .load(job.companyLogoUrl)
                    .into(ivCompanyLogo)

                tvJobTitle.text = job.title
                tvDate.text = job.publicationDate?.split("T")!![0]
                tvJobType.text = job.jobType
                tvJobLocation.text = job.candidateRequiredLocation
                tvCompanyName.text = job.companyName
                btnDelete.isVisible = true

                btnDelete.setOnClickListener {
                    onDeleteClick(job)
                }
            }
            itemView.setOnClickListener {
                onClick.invoke(job)
            }
        }
    }
}