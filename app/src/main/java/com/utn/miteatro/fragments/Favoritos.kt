package com.utn.miteatro.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.utn.miteatro.R
import com.utn.miteatro.adapters.ObrasAdapter
import com.utn.miteatro.database.ObrasDao
import com.utn.miteatro.database.ObrasDatabase
import com.utn.miteatro.entities.Obra

class Favoritos : Fragment() {
    lateinit var v : View

    private var db: ObrasDatabase? = null
    private var obrasDao: ObrasDao? = null

    lateinit var recObrasFav : RecyclerView

    lateinit var adapter : ObrasAdapter

    var obrasList: MutableList<Obra?>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_favoritos, container, false)

        recObrasFav = v.findViewById(R.id.recObrasFav)

        return v
    }

    fun updateVista(){
        obrasList = obrasDao?.fetchAllObras()
        adapter.updateObras(obrasList)
    }

    override fun onStart() {
        super.onStart()
/**
        db = ObrasDatabase.getInstance(v.context)
        obrasDao = db?.obrasDao()

        obrasList = obrasDao?.fetchAllObras()

        adapter = ObrasAdapter(obrasList ,
            { position ->
                val action = ListaObrasDirections.actionListaObrasToDetalleObra(obrasList?.get(position)!!)
                findNavController().navigate(action)
            }, {position ->
                val action = ListaObrasDirections.actionListaObrasToNewEditObra2(obrasList?.get(position)!!)
                findNavController().navigate(action)
            }, {position ->
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("¿Está seguro que desea eliminarla?")
                    .setPositiveButton("Si") { _, _ ->
                        obrasDao?.delete(Obra(obrasList?.get(position)!!.id))
                        Snackbar.make(v, "Obra eliminada con éxito", Snackbar.LENGTH_SHORT).show()
                        updateVista()
                    }
                    .setNegativeButton("No", null)
                builder.create().show()
            })

        recObras.layoutManager = LinearLayoutManager(context)
        recObras.adapter = adapter

        btnAdd.setOnClickListener {
            val action = ListaObrasDirections.actionListaObrasToNewEditObra2(Obra(0))
            findNavController().navigate(action)
        }
*/
    }

    /**
    override fun onResume() {
        super.onResume()
        updateVista()
    }
*/

}



