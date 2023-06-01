package uz.farxod.database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.farxod.database.databinding.UserItemBinding

class UserRecyclerAdapter(val list: List<User>): RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder>() {

    private var listener: OnUserClickedListener? = null
    inner class ViewHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(user: User){
            binding.firstname.text = user.firstname
            binding.lastname.text = user.lastname
            binding.age.text = "${user.age} yosh"

            // Ustiga uzoq bosilganida ishlashi uchun
            binding.root.setOnLongClickListener {
                listener?.onUserLongClicked(adapterPosition)
                true
            }

            binding.iconEdit.setOnClickListener{// shu bosilsa bosilgan pozitsiyani interface orqali beramiz
                listener?.onEditClicked(adapterPosition)
            }
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

    fun setOnUserClickedListener(listener: OnUserClickedListener){
        this.listener = listener
    }
}