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

    fun addCadastro(nome: String, email: String) = viewModelScope.launch {
        try {
            val id = repository.insertCadastro(nome, email)
            if (id > 0) {
                _cadastroStateEventData.value = CadastroState.Inserted
                _messageEventData.value = R.string.cadastro_inserido_sucesso
            }
        } catch (ex: Exception) {
            _messageEventData.value = R.string.cadastro_erro_inserir
            Log.e(TAG, ex.toString())
        }
    }

    sealed class CadastroState {
        object Inserted : CadastroState()
    }

    companion object {
        private val TAG = CadastroViewModel::class.java.simpleName
    }
}