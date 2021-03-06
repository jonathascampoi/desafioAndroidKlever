package com.example.desafio_android_klever.ui.cadastro

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio_android_klever.R
import com.example.desafio_android_klever.repository.CadastroRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class CadastroViewModel(private val repository: CadastroRepository) : ViewModel() {

    private val _cadastroStateEventData = MutableLiveData<CadastroState>()
    val cadastroStateEventData: LiveData<CadastroState>
        get() = _cadastroStateEventData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun adicionaOuAtualizaCadastro(
        nome: String,
        email: String,
        id: Long = 0,
        cep: String,
        estado: String,
        cidade: String,
        bairro: String,
        rua: String
    ) {
        if (id > 0) {
            atualizaCadastro(id, nome, email, cep, estado, cidade, bairro, rua)
        } else {
            criarCadastro(nome, email, cep, estado, cidade, bairro, rua)
        }
    }

    private fun atualizaCadastro(
        id: Long,
        nome: String,
        email: String,
        cep: String,
        estado: String,
        cidade: String,
        bairro: String,
        rua: String
    ) = viewModelScope.launch {
        try {
            repository.updateCadastro(id, nome, email, cep, estado, cidade, bairro, rua)

            _cadastroStateEventData.value = CadastroState.Updated
            _messageEventData.value = R.string.cadastro_atualizado_sucesso

        } catch (ex: Exception) {
            _messageEventData.value = R.string.cadastro_erro_atualizar
            Log.e(TAG, ex.toString())
        }
    }

    private fun criarCadastro(
        nome: String,
        email: String,
        cep: String,
        estado: String,
        cidade: String,
        bairro: String,
        rua: String
    ) = viewModelScope.launch {
        try {
            val id = repository.insertCadastro(nome, email, cep, estado, cidade, bairro, rua)
            if (id > 0) {
                _cadastroStateEventData.value = CadastroState.Inserted
                _messageEventData.value = R.string.cadastro_inserido_sucesso
            }
        } catch (ex: Exception) {
            _messageEventData.value = R.string.cadastro_erro_inserir
            Log.e(TAG, ex.toString())
        }
    }

    fun deletarCadastro(id: Long) = viewModelScope.launch {
        try {
            if (id > 0) {
                repository.deleteCadastro(id)
                _cadastroStateEventData.value = CadastroState.Deleted
                _messageEventData.value = R.string.cadastro_deletado_sucesso
            }
        } catch (ex: Exception) {
            _messageEventData.value = R.string.cadastro_erro_deletar
            Log.e(TAG, ex.toString())
        }
    }

    sealed class CadastroState {
        object Inserted : CadastroState()
        object Updated : CadastroState()
        object Deleted : CadastroState()
    }

    companion object {
        private val TAG = CadastroViewModel::class.java.simpleName
    }
}