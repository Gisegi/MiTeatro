package com.utn.miteatro.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.utn.miteatro.R
import com.utn.miteatro.activities.LoginActivity
import com.utn.miteatro.activities.MainActivity
import com.utn.miteatro.database.UserDao
import com.utn.miteatro.database.UserDatabase
import com.utn.miteatro.entities.User

class UserInfo : Fragment() {

    lateinit var v : View

    private var db: UserDatabase? = null
    private var userDao: UserDao? = null

    private lateinit var userData : User

    lateinit var userName: TextView
    lateinit var userLastname : TextView
    lateinit var userEmail: TextView
    lateinit var btnEdit: Button
    lateinit var btnDelete : Button
    lateinit var btnClose : Button
    lateinit var userAvatar : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_info, container, false)

        userData = (requireActivity() as MainActivity).userData

        userName = v.findViewById(R.id.userName)
        userLastname = v.findViewById(R.id.userLastname)
        userEmail = v.findViewById(R.id.userEmail)

        btnEdit = v.findViewById(R.id.btnEdit)
        btnDelete = v.findViewById(R.id.btnDelete)
        btnClose = v.findViewById(R.id.btnClose)

        userAvatar = v.findViewById(R.id.userAvatarEdit)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = UserDatabase.getInstance(v.context)
        userDao = db?.userDao()

        userName.text = userData.name
        userLastname.text = userData.lastname
        userEmail.text = userData.email
        setImagen(userData.image)


        btnEdit.setOnClickListener {
            val action = UserInfoDirections.actionUserInfoToEditNewUser(userData)
            findNavController().navigate(action)
        }


        btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("¿Está seguro que desea eliminar la cuenta?")
                .setPositiveButton("Si") { _, _ ->
                    userDao?.delete(User(userData.id, "", "", "", "", ""))
                    Snackbar.make(v, "Cuenta eliminada con éxito", Snackbar.LENGTH_SHORT).show()
                    val intent = Intent(activity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                .setNegativeButton("No", null)
            builder.create().show()
        }

        btnClose.setOnClickListener {
                    val intent = Intent(activity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
        }

    }

    fun setImagen(imagen: String?) {
        val imgUser: ImageView = v.findViewById(R.id.userAvatarEdit)
        val resourceId = v.resources.getIdentifier(imagen, "drawable", v.context.packageName)
        Glide.with(imgUser.context)
            .load(resourceId)
            .into(imgUser)
    }

}
