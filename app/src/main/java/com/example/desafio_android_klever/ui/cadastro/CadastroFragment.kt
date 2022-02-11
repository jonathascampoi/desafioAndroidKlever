package com.example.desafio_android_klever.ui.cadastro

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.desafio_android_klever.services.HttpService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.cadastro_fragment.*
import java.util.regex.Matcher
import java.util.regex.Pattern


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

            botao_deletar.visibility = View.VISIBLE
        }

        observeEvents()
        setListners()
    }

    private fun setListners() {
        botao_criar.setOnClickListener {
            val nome = input_name.text.toString()
            val email = input_email.text.toString()
            val cep = input_cep.text.toString()
            val estado = input_estado.text.toString()
            val cidade = input_cidade.text.toString()
            val bairro = input_bairro.text.toString()
            val rua = input_rua.text.toString()

            viewModel.adicionaOuAtualizaCadastro(
                nome,
                email,
                args.cadastro?.id ?: 0,
                cep,
                estado,
                cidade,
                bairro,
                rua
            )
        }

        botao_deletar.setOnClickListener {
            viewModel.deletarCadastro(args.cadastro?.id ?: 0)
        }

        input_name.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(nome: Editable?) {
                val content = nome.toString()
                input_name.error =
                    if (content.length >= 4) null else getString(R.string.validacao_nome)
                botao_criar.isEnabled = input_name.error === null
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })

        input_email.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(email: Editable?) {
                val content = email.toString()
                input_email.error = if (android.util.Patterns.EMAIL_ADDRESS.matcher(content)
                        .matches()
                ) null else getString(R.string.validacao_email)
                botao_criar.isEnabled = input_email.error === null
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })

        input_cep.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(cep: Editable?) {
                val content = cep.toString()
//                input_cep.error = if (content.length >= 4) null else getString(R.string.validacao_nome)
                val pattern_zipcode: Pattern =
                    Pattern.compile("(^\\d{5}-\\d{3}|^\\d{2}.\\d{3}-\\d{3}|\\d{8})")
                val matcher: Matcher = pattern_zipcode.matcher(content)
                if (content == "") {
                    input_cep.error = "O campo não pode estar vazio"
                    botao_criar.isEnabled = input_cep.error === null
                }
                if (!matcher.matches()) {
                    input_cep.error = "Informe um CEP válido"
                    botao_criar.isEnabled = input_cep.error === null
                } else {
                    val retorno = HttpService(content).execute().get()
                    input_estado.setText(retorno.uf)
                    input_cidade.setText(retorno.localidade)
                    input_bairro.setText(retorno.bairro)
                    input_rua.setText(retorno.logradouro)
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
    }

    private fun observeEvents() {
        viewModel.cadastroStateEventData.observe(viewLifecycleOwner) { cadastroState ->
            when (cadastroState) {
                is CadastroViewModel.CadastroState.Inserted,
                is CadastroViewModel.CadastroState.Updated,
                is CadastroViewModel.CadastroState.Deleted,
                -> {
                    limpaTextos()
                    escondeTeclado()
                    requireView().requestFocus()
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