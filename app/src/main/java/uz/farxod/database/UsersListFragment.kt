package uz.farxod.database

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.farxod.database.databinding.CustomDeletDialogBinding
import uz.farxod.database.databinding.CustomUpdateDialogBinding
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

        adapter.setOnUserClickedListener(object : OnUserClickedListener{
            override fun onEditClicked(position: Int) {
                val selectedUserToUpdate = list[position]

                val builder = AlertDialog.Builder(requireContext())
                val dialogBinding = CustomUpdateDialogBinding.inflate(layoutInflater)   //chizgan layoutimizni topib oldik

                val dialog = builder.create()
                dialog.setView(dialogBinding.root)
                dialog.show()   //dialog ochilishi un

                //dialog un
                dialogBinding.btnSave.setOnClickListener {
                    //DB-ga saqlaymiz
                    if(dialogBinding.editFirstname.text.equals("") && dialogBinding.editLastname.text.equals("") && dialogBinding.editAge.text.equals(""))
                    {

                    }
                        Toast.makeText(requireContext(), "User record has been updated!", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                dialogBinding.btnCancel.setOnClickListener {
                    dialog.dismiss()
                }
            }

            override fun onUserLongClicked(position: Int) {
                val deleteUserId = list[position]
                val builder = AlertDialog.Builder(requireContext())
                val dialogBinding = CustomDeletDialogBinding.inflate(layoutInflater)
                val dialog = builder.create()
                dialog.setView(dialogBinding.root)
                dialog.show()

                //dialog un
                dialogBinding.btnYes.setOnClickListener {
                    //DB-dan o'chiramiz
                    databaseHelper.deleteUser(deleteUserId.id)
                    //list (cursor) - dan o'chiramiz
                    list.remove(deleteUserId)

                    adapter.notifyItemRemoved(position) //adapterga o'chirganimizni aytamiz

                    Toast.makeText(requireContext(), "Foydalanuvchi o'chirildi!", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                dialogBinding.btnNo.setOnClickListener {
                    dialog.dismiss()
                }
            }

        })
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