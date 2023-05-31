package uz.farxod.database

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.farxod.database.databinding.FragmentUsersListBinding

class UsersListFragment : Fragment() {
    private var _binding: FragmentUsersListBinding? = null
    private val binding get() = _binding!!

    private lateinit var databaseHelper: DatabaseHelper

    private val list : MutableList<User> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUsersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        databaseHelper = DatabaseHelper(requireContext())

        initList()

        val adapter = UserRecyclerAdapter(list) // adapter

        binding.recyclerview.adapter = adapter

        // Reciycler view orasiga chiziq chizish uchun
        binding.recyclerview.addItemDecoration(DividerItemDecoration(binding.recyclerview.context, DividerItemDecoration.VERTICAL))
    }

    private fun initList(){
        val cursor = databaseHelper.readUser() //cursorni olib kelamiz
        if(cursor.count > 0 ){  // agar 0- dan katta bo'lsa ma'lumot mavjud
            while (cursor.moveToNext()){    // cursorda kiyingi ma'lumot topilgunigacha, kiyingisiga o'tolish o'tolmasligini tekshiradi
                val id = cursor.getString(0)
                val firstname = cursor.getString(1)
                val lastname = cursor.getString(2)
                val age = cursor.getInt(3)

                val user = User(id, firstname,lastname,age) // user yaratamiz, construktor ga beramiz ma'l/ ni
                list.add(user)


            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}