package com.utn.miteatro.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.utn.miteatro.R
import com.utn.miteatro.activities.MainActivity
import com.utn.miteatro.database.UserDao
import com.utn.miteatro.database.UserDatabase
import com.utn.miteatro.entities.User


class UserLogin : Fragment() {
    lateinit var v : View

    private var db: UserDatabase? = null
    private var userDao: UserDao? = null

    lateinit var btnLogin : Button
    lateinit var btnAdd : Button
    lateinit var inputEmail: EditText
    lateinit var inputPass : EditText

    //  lateinit var btnDebug : Button

    // Lista de usuarios
    private lateinit var userList: MutableList<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_login, container, false)

        btnLogin = v.findViewById(R.id.btnLogin)
        btnAdd = v.findViewById(R.id.btnAdd)
        inputEmail = v.findViewById(R.id.inputEmail)
        inputPass = v.findViewById(R.id.inputPass)

        Log.d("Estado", "Estoy en UserLogin")

        return v
    }

    override fun onStart() {
        super.onStart()

        db = UserDatabase.getInstance(v.context)
        userDao = db?.userDao()

        // Dummy call to pre-populate db
        userDao?.fetchAllUsers()

        // INGRESAR
        btnLogin.setOnClickListener {
            var inEmail: String = inputEmail.text.toString()
            var inPass: String = inputPass.text.toString()

            //userList = userDao?.fetchAllUsers() as MutableList<User>

            var userSearch = userDao?.fetchUserByEmail(inEmail)

            if (inEmail.isEmpty() || inPass.isEmpty() ) {
                Snackbar.make(v, "Complete ambos campos", Snackbar.LENGTH_SHORT).show()
            }
            else {
                if (userSearch == null) {
                    Snackbar.make(v, "Usuario inexistente", Snackbar.LENGTH_SHORT).show()
                } else {
                    if (userSearch.password == inPass) {
                        Log.d("Usuario", userSearch.email)
                        inputEmail.setText("")
                        inputPass.setText("")

                        val intent = Intent(activity, MainActivity::class.java)
                        intent.putExtra("UserData", userSearch)
                        startActivity(intent)
                        activity?.finish()
                    } else {
                        Snackbar.make(v, "Contraseña incorrecta", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // REGISTRARSE
        btnAdd.setOnClickListener {
                val action = UserLoginDirections.actionUserLoginToEditNewUser2()
                findNavController().navigate(action)
        }

    }

}


