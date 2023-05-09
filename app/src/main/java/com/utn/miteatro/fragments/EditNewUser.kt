package com.utn.miteatro.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.utn.miteatro.R
import com.utn.miteatro.database.UserDao
import com.utn.miteatro.database.UserDatabase
import com.utn.miteatro.entities.User

class EditNewUser : Fragment() {

    lateinit var v : View

    private var db: UserDatabase? = null
    private var userDao: UserDao? = null

    lateinit var btnSave : Button
    lateinit var inputName : EditText
    lateinit var inputLastname : EditText
    lateinit var inputEmail: EditText
    lateinit var inputPass : EditText
    lateinit var inputImage : ImageView
    lateinit var btnEditImage : FloatingActionButton

    var userData : User?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_edit_new_user, container, false)

        inputName = v.findViewById(R.id.editName)
        inputLastname = v.findViewById(R.id.editLastName)
        inputEmail = v.findViewById(R.id.editEmail)
        inputPass = v.findViewById(R.id.editPassword)
        btnSave = v.findViewById(R.id.btnEdit)
        btnEditImage = v.findViewById(R.id.floatbtnImage)

        val navGraphId = findNavController().currentDestination?.id
        if (navGraphId == R.id.editNewUser) {
            userData = EditNewUserArgs.fromBundle(requireArguments()).userData
        }

        inputName.setText(userData?.name)
        inputLastname.setText(userData?.lastname)
        inputEmail.setText(userData?.email)
        setImagen(userData?.image ?: "avatar_default")

        return v
    }

    override fun onStart() {
        super.onStart()

        db = UserDatabase.getInstance(v.context)
        userDao = db?.userDao()

        // Dummy call to pre-populate db
        userDao?.fetchAllUsers()

        btnEditImage.setOnClickListener{

            // Quiero abrir un menu con los avatares disponibles
        }


        // REGISTRARSE O EDITAR INFO
        btnSave.setOnClickListener {
            var inName: String = inputName.text.toString()
            var inLastname: String = inputLastname.text.toString()
            var inEmail: String = inputEmail.text.toString()
            var inPass: String = inputPass.text.toString()
            var inImage: String = "avatar_default"

            var userSearch = userDao?.fetchUserByEmail(inEmail)

            if ( inName.isEmpty() || inLastname.isEmpty() || inEmail.isEmpty() || inPass.isEmpty() ) {
                Snackbar.make(v, "Complete todos los campos", Snackbar.LENGTH_SHORT).show()
            }
            else {
                if (userData != null) {
                    userDao?.updateUser(User(userData!!.id, inName, inLastname, inEmail, inPass, userData!!.image))
                    Snackbar.make(v, "Usuario editado con éxito", Snackbar.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                } else {
                    if (userSearch == null) {
                        userDao?.insertUser(User(0, inName, inLastname, inEmail, inPass, inImage))
                        Snackbar.make(v, "Usuario registrado con éxito", Snackbar.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    } else {
                        Snackbar.make(v, "Usuario existente", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
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



