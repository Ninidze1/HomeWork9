package btu.ninidze.homework_9.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import btu.ninidze.homework_9.data.extensions.loadImg
import btu.ninidze.homework_9.databinding.ItemUserLayoutBinding

class UserRecyclerViewAdapter() : RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {

    private val items = mutableListOf<UserUi>()

    inner class ViewHolder(private val binding: ItemUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserUi) = with(binding) {
            ivProfile.loadImg(user.avatar.toString())
            tvFirstName.text = user.firstName
            tvLastName.text = user.lastName
            tvEmail.text = user.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size

    fun initList(notes: List<UserUi>) {
        items.clear()
        items.addAll(notes)
        notifyDataSetChanged()
    }

}