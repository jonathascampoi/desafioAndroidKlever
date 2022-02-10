package com.example.desafio_android_klever.ui.cadastro

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.desafio_android_klever.R
import com.example.desafio_android_klever.data.db.AppDatabase
import com.example.desafio_android_klever.data.db.dao.CadastroDAO
import com.example.desafio_android_klever.extensions.hideKeyboard
import com.example.desafio_android_klever.repository.CadastroRepository
import com.example.desafio_android_klever.repository.DatabaseSource
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.cadastro_fragment.input_email
import kotlinx.android.synthetic.main.cadastro_fragment.input_name
import kotlinx.android.synthetic.main.cadastro_fragment.botao_criar

class CadastroFragment : Fragment(R.layout.cadastro_fragment) {

    private val viewModel: CadastroViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val cadastroDAO: CadastroDAO = AppDatabase.getInstance(requireContext()).cadastroDAO
                val repository: CadastroRepository = DatabaseSource(cadastroDAO)
                return CadastroViewModel(repository) as T
            }
        }
    }

    private val args: CadastroFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.cadastro?.let { cadastro ->
            botao_criar.text = getString(R.string.text_btn_atualizar)
            input_name.setText(cadastro.nome)
            input_email.setText(cadastro.email)
        }

        observeEvents()
        setListners()
    }

    private fun setListners() {
        botao_criar.setOnClickListener{
            val nome = input_name.text.toString()
            val email = input_email.text.toString()
            
            viewModel.adicionaOuAtualizaCadastro(nome, email, args.cadastro?.id ?: 0)
        }
    }

    private fun observeEvents() {
        viewModel.cadastroStateEventData.observe(viewLifecycleOwner) { cadastroState ->
            when (cadastroState) {
                is CadastroViewModel.CadastroState.Inserted -> {
                    limpaTextos()
                    escondeTeclado()
                    requireView().requestFocus()
                    findNavController().popBackStack()
                }
                is CadastroViewModel.CadastroState.Updated -> {
                    limpaTextos()
                    escondeTeclado()
                    findNavController().popBackStack()
                }
            }
        }

        viewModel.messageEventData.observe(viewLifecycleOwner) { stringResId ->
            Snackbar.make(requireView(), stringResId, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun escondeTeclado() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }

    private fun limpaTextos() {
        input_email.text?.clear()
        input_name.text?.clear()
    }

}