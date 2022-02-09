package com.example.desafio_android_klever.ui.listaCadastro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_android_klever.R
import com.example.desafio_android_klever.data.db.entity.CadastroEntity
import kotlinx.android.synthetic.main.cadastro_item.view.*

class ListaCadastroAdapter(private val cadastros: List<CadastroEntity>) :
    RecyclerView.Adapter<ListaCadastroAdapter.ListaCadastroViewHolder>() {

    class ListaCadastroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewNomeCadastro: TextView = itemView.name_listagem
        private val textViewEmailCadastro: TextView = itemView.email_listagem

        fun bindView(cadastro: CadastroEntity) {
            textViewNomeCadastro.text = cadastro.nome
            textViewEmailCadastro.text = cadastro.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaCadastroViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cadastro_item, parent, false)

        return ListaCadastroViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaCadastroViewHolder, position: Int) {
        holder.bindView(cadastros[position])
    }

    override fun getItemCount() = cadastros.size
}