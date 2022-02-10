package com.example.desafio_android_klever.ui.listaCadastro

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.desafio_android_klever.R
import com.example.desafio_android_klever.data.db.AppDatabase
import com.example.desafio_android_klever.data.db.dao.CadastroDAO
import com.example.desafio_android_klever.extension.navigateWithAnimations
import com.example.desafio_android_klever.repository.CadastroRepository
import com.example.desafio_android_klever.repository.DatabaseSource
import com.example.desafio_android_klever.ui.cadastro.CadastroViewModel
import kotlinx.android.synthetic.main.lista_cadastro_fragment.*

class ListaCadastroFragment : Fragment(R.layout.lista_cadastro_fragment) {

    private val viewModel: ListaCadastroViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val cadastroDAO: CadastroDAO = AppDatabase.getInstance(requireContext()).cadastroDAO
                val repository: CadastroRepository = DatabaseSource(cadastroDAO)
                return ListaCadastroViewModel(repository) as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModelEvents()
        configureViewListeners()
    }

    private fun observeViewModelEvents() {
        viewModel.allCadastrosEvent.observe(viewLifecycleOwner) { allCadastros ->
            val listaCadastroAdapter = ListaCadastroAdapter(allCadastros).apply {
                onItemClick = { cadastro ->
                    val directions =
                        ListaCadastroFragmentDirections.actionListaCadastroFragmentToCadastroFragment(
                            cadastro
                        )
                    findNavController().navigateWithAnimations(directions)

                }
            }

            with(recycler_cadastros) {
                setHasFixedSize(true)
                adapter = listaCadastroAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCadastros()
    }

    private fun configureViewListeners() {
        btn_adicionar.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.action_listaCadastroFragment_to_cadastroFragment)
        }
    }
}