package uz.farxod.database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.farxod.database.databinding.UserItemBinding

class UserRecyclerAdapter(val list: List<User>): RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(user: User){
            binding.firstname.text = user.firstname
            binding.lastname.text = user.lastname
            binding.age.text = "${user.age} yosh"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }
}