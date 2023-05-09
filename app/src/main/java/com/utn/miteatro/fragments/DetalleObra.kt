package com.utn.miteatro.fragments

import android.content.Intent
import android.net.Uri
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.utn.miteatro.R
import com.utn.miteatro.database.ObrasDao
import com.utn.miteatro.database.ObrasDatabase
import com.utn.miteatro.entities.Obra

class DetalleObra : Fragment() {

    lateinit var v: View

    lateinit var detalleObra : Obra

    lateinit var detailName: TextView
    lateinit var detailTheater: TextView
    lateinit var detailAddress: TextView
    lateinit var detailCategory: TextView
    lateinit var detailWeb: Button
    lateinit var detailAbout: TextView
    lateinit var detailImage: ImageView
    lateinit var btnEdit : FloatingActionButton

    private var db: ObrasDatabase? = null
    private var obrasDao: ObrasDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_detalle_obra, container, false)

        detailName = v.findViewById(R.id.editNameObra)
        detailTheater = v.findViewById(R.id.editTheater)
        detailAddress = v.findViewById(R.id.editAddress)
        detailCategory = v.findViewById(R.id.editCategory)
        detailWeb = v.findViewById(R.id.editlWeb)
        detailAbout = v.findViewById(R.id.editAbout)
        detailImage = v.findViewById(R.id.editImage)
        btnEdit = v.findViewById(R.id.floabtnEdit)

        detalleObra = DetalleObraArgs.fromBundle(requireArguments()).detalleObra

        return v
    }

    override fun onStart() {
        super.onStart()

        db = ObrasDatabase.getInstance(v.context)
        obrasDao = db?.obrasDao()

        updateVista()

        btnEdit.setOnClickListener {
            val action = DetalleObraDirections.actionDetalleObraToNewEditObra2(detalleObra)
            findNavController().navigate(action)
        }


    }

    override fun onResume() {
        super.onResume()
        updateVista()
    }

    fun setImagen(imagen: String?) {
        val imgObra: ImageView = v.findViewById(R.id.editImage)
        val resourceId = v.resources.getIdentifier(imagen, "drawable", v.context.packageName)
        Glide.with(imgObra.context)
            .load(resourceId)
            .into(imgObra)
    }

    fun openWeb(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    fun updateVista(){
        detalleObra = obrasDao?.fetchObraById(detalleObra.id)!!
        detailName.setText(detalleObra.name)
        detailTheater.setText(detalleObra.theater)
        detailAddress.setText(detalleObra.address)
        detailCategory.setText(detalleObra.category)
        detailAbout.setText(detalleObra.about)
        setImagen(detalleObra.image)

        detailWeb.setOnClickListener {
            if (detalleObra.web?.isNotEmpty() == true){
                openWeb(detalleObra.web!!)
            }
        }
    }


}