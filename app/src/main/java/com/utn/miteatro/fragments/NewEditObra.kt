package com.utn.miteatro.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.utn.miteatro.R
import com.utn.miteatro.database.ObrasDao
import com.utn.miteatro.database.ObrasDatabase
import com.utn.miteatro.entities.Obra
import com.utn.miteatro.entities.User
import java.io.File


class NewEditObra : Fragment() {

    companion object {
        const val PICK_IMAGE_REQUEST_CODE = 1
    }

    lateinit var v: View

    private lateinit var spinner: Spinner
    private val optionsCategory = arrayOf("Seleccione:", "Comedia","Comedia dramática","Drama", "Musical", "Stand up", "Ópera", "Ballet", "Música clásica", "Infatil")

    lateinit var editNameObra: EditText
    lateinit var editTheater: EditText
    lateinit var editAddress: EditText
    lateinit var editAbout: EditText
    lateinit var editWeb: EditText
    lateinit var editImage: ImageView
    lateinit var btnSaveObra: Button
    lateinit var btnEditImage : FloatingActionButton

    private var imageName: String? = null

    private var db: ObrasDatabase? = null
    private var obrasDao: ObrasDao? = null

    var editObra: Obra? = null

    var flagNewObra = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_new_edit_obra, container, false)

        spinner = v.findViewById(R.id.spinnerCategory)
        btnSaveObra = v.findViewById(R.id.btnSaveObra)

        editNameObra = v.findViewById(R.id.editNameObra)
        editTheater = v.findViewById(R.id.editTheater)
        editAddress = v.findViewById(R.id.editAddress)
        editAbout = v.findViewById(R.id.editAbout)
        editWeb = v.findViewById(R.id.editlWeb)
        editImage = v.findViewById(R.id.editImage)
        btnEditImage = v.findViewById(R.id.floatbtnEditImage)

        editObra = NewEditObraArgs.fromBundle(requireArguments()).editObra

        if (editObra!!.id == 0) {
            flagNewObra = true
        }

        editNameObra.setText(editObra?.name)
        editTheater.setText(editObra?.theater)
        editAddress.setText(editObra?.address)
        editAbout.setText(editObra?.about)
        editWeb.setText(editObra?.web)

        imageName = editObra?.image
        if (editObra?.image.isNullOrEmpty()){
            imageName = "imagenobradefault"
        }
        setImagen(imageName)

        // Completo el spinner con las opciones
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, optionsCategory)
        spinner.adapter = adapter

        val categoryIndex = adapter.getPosition(editObra?.category)
        if (editObra?.category.isNullOrEmpty()){
            val categoryIndex = 0
        }
        spinner.setSelection(categoryIndex)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = ObrasDatabase.getInstance(v.context)
        obrasDao = db?.obrasDao()

        btnEditImage.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        }

        // REGISTRARSE
        btnSaveObra.setOnClickListener {
            var newObra: String? = null
            var newTheater: String? = null
            var newAddress: String? = null
            var newAbout: String? = null
            var newWeb: String? = null
            var newCategory: String
            var newImage = editObra?.image

            if (editObra?.image.isNullOrEmpty()){
                newImage = "imagenobradefault"
            }

            newObra = editNameObra?.text.toString()
            newTheater = editTheater?.text.toString()
            newAddress = editAddress?.text.toString()
            newAbout = editAbout?.text.toString()
            newWeb = editWeb?.text.toString()
            newCategory = optionsCategory[spinner.selectedItemPosition]

            if (flagNewObra == true) {
                obrasDao?.insertObra(Obra(0,
                                            newObra,
                                            newTheater,
                                            newAddress,
                                            newCategory,
                                            newImage,
                                            newWeb,
                                            newAbout))
                Snackbar.make(v, "Nueva obra creada con éxito", Snackbar.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
            else {
                obrasDao?.updateObra(Obra(  editObra!!.id,
                                            newObra,
                                            newTheater,
                                            newAddress,
                                            newCategory,
                                            newImage,
                                            newWeb,
                                            newAbout))
                Snackbar.make(v, "Información editada con éxito", Snackbar.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            editImage.setImageURI(imageUri)
            val imageFile = File(getRealPathFromURI(imageUri))
            imageName = imageFile.name
        }
    }

    fun getRealPathFromURI(uri: Uri?): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity?.contentResolver?.query(uri!!, projection, null, null, null)
        if (cursor != null) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val s = cursor.getString(column_index)
            cursor.close()
            return s
        }
        return ""
    }


    fun setImagen(imagen: String?) {
        val imgObra: ImageView = v.findViewById(R.id.editImage)
        val resourceId = v.resources.getIdentifier(imagen, "drawable", v.context.packageName)
        Glide.with(imgObra.context)
            .load(resourceId)
            .into(imgObra)
    }
}