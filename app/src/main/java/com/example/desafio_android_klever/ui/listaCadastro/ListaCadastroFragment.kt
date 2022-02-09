package com.example.desafio_android_klever.ui.listaCadastro

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.desafio_android_klever.R
import kotlinx.android.synthetic.main.lista_cadastro_fragment.*

class ListaCadastroFragment : Fragment(R.layout.lista_cadastro_fragment) {

    private lateinit var viewModel: ListaCadastroViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModelEvents()
    }

    private fun observeViewModelEvents() {
        viewModel.allCadastrosEvent.observe(viewLifecycleOwner) { allCadastros ->
            val listaCadastroAdapter = ListaCadastroAdapter(allCadastros)

            with(recycler_cadastros) {
                setHasFixedSize(true)
                adapter = listaCadastroAdapter
            }
        }
    }
}