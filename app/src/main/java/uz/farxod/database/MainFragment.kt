@file:Suppress("UNREACHABLE_CODE")

package uz.farxod.database

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import uz.farxod.database.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        databaseHelper = DatabaseHelper(requireContext())

        binding.save.setOnClickListener {
            if (binding.editFirstname.text.isNotEmpty() &&
                binding.editLastname.text.isNotEmpty() &&
                binding.editAge.text.isNotEmpty()){

                val firstname = binding.editFirstname.text.toString()
                val lastname = binding.editLastname.text.toString()
                val age = binding.editAge.text.toString().toInt()

                databaseHelper.incertUser(firstname, lastname, age)
                Toast.makeText(requireContext(), "Foydalanuvchi ma'lumoti saqlandi!", Toast.LENGTH_SHORT).show()

                Log.d("Tag", "Ok")
                binding.editFirstname.text.clear()
                binding.editLastname.text.clear()
                binding.editAge.text.clear()
            }
            else{
                Toast.makeText(requireContext(), "Barcha ma'lumotni to'ldiring!", Toast.LENGTH_SHORT).show()
                Log.d("Tag", "Ok1")
            }
        }

        binding.showAllUsers.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_blank, UsersListFragment())
                .addToBackStack(MainFragment().toString())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}