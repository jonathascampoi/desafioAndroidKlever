package com.example.desafio_android_klever.ui.listaCadastro

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio_android_klever.data.db.entity.CadastroEntity
import com.example.desafio_android_klever.repository.CadastroRepository
import com.example.desafio_android_klever.ui.cadastro.CadastroViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class ListaCadastroViewModel(private val repository: CadastroRepository) : ViewModel() {
    private val _allCadastrosEvent = MutableLiveData<List<CadastroEntity>>()
        val allCadastrosEvent: LiveData<List<CadastroEntity>>
        get()  = _allCadastrosEvent

    fun getCadastros() = viewModelScope.launch {
        try {
        _allCadastrosEvent.postValue(repository.getAllCadastro())

        } catch (ex: Exception) {
            Log.e("getAll", ex.toString())
        }
    }
}