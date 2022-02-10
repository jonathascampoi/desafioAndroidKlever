package com.example.desafio_android_klever.ui.listaCadastro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio_android_klever.data.db.entity.CadastroEntity
import com.example.desafio_android_klever.repository.CadastroRepository
import kotlinx.coroutines.launch

class ListaCadastroViewModel(private val repository: CadastroRepository) : ViewModel() {
    private val _allCadastrosEvent = MutableLiveData<List<CadastroEntity>>()
        val allCadastrosEvent: LiveData<List<CadastroEntity>>
        get()  = _allCadastrosEvent

    fun getCadastros() = viewModelScope.launch {
        _allCadastrosEvent.postValue(repository.getAllCadastro())
    }
}