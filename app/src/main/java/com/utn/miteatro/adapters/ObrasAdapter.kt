package com.utn.miteatro.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utn.miteatro.R
import com.utn.miteatro.entities.Obra
import com.utn.miteatro.fragments.ListaObrasDirections

class ObrasAdapter(
    var obrasList: MutableList<Obra?>?,
    var onClick: (Int) -> Unit,
    var onClickEdit: (Int) -> Unit,
    var onClickDelete: (Int) -> Unit,
    var onClickFav: (Int) -> Unit): RecyclerView.Adapter<ObrasAdapter.ObrasHolder> ()
{

        class ObrasHolder (v : View) : RecyclerView.ViewHolder(v){
            private var view: View
            init{
                this.view = v
            }

            fun setNombre (name : String){
                var txtName : TextView = view.findViewById(R.id.txtNombre_Obra)
                txtName.text = name
            }

            fun setTeatro (teatro : String) {
                var txtTeatro : TextView = view.findViewById(R.id.txtTeatro_Obra)
                txtTeatro.text = teatro
            }

            fun setImagen(imagen: String) {
                val imgObra: ImageView = view.findViewById(R.id.imgObra)
                val resourceId = view.resources.getIdentifier(imagen, "drawable", view.context.packageName)
                Glide.with(imgObra.context)
                    .load(resourceId)
                    .into(imgObra)
            }

            fun getCard(): CardView {
                return view.findViewById(R.id.cardObra)
            }

            fun onCardViewLongClick(view: View, callback : (Int)->Unit): Boolean {
                val popupMenu = PopupMenu(view.context, view)
                popupMenu.inflate(R.menu.menu_longclick)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.btnClickEdit -> {
                            callback(1)
                            true
                        }
                        R.id.btnClickDelete -> {
                            callback(2)
                            true
                        }
                        R.id.btnClickFav -> {
                            callback(3)
                            true
                        }
                        else -> false
                    }
                }
                popupMenu.show()
                return true
            }

        }

    fun updateObras(obras_lista: MutableList<Obra?>?){
        obrasList = obras_lista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObrasHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_obra,parent,false)
        return (ObrasHolder(view))
    }

    override fun getItemCount(): Int {
        return obrasList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ObrasHolder, position: Int) {
        val obra = obrasList?.getOrNull(position)
        obra?.let {
            holder.setNombre(it.name ?: "")
            holder.setTeatro(it.theater ?: "")
            holder.setImagen(it.image ?: "")
            var card = holder.getCard()
            card.setOnClickListener{
                onClick(position)
            }
            card.setOnLongClickListener { view ->
                holder.onCardViewLongClick(view) {opcion ->
                    when (opcion){
                        1->{
                            onClickEdit(position)
                        }
                        2->{
                            onClickDelete(position)
                        }
                        3->{
                            onClickFav(position)
                        }
                    }
                }
            }

        }
    }

}





