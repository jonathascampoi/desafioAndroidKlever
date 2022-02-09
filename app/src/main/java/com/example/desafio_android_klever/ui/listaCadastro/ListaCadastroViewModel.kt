package com.example.desafio_android_klever.ui.listaCadastro

import androidx.lifecycle.ViewModel
import com.example.desafio_android_klever.repository.CadastroRepository

class ListaCadastroViewModel(private val repository: CadastroRepository) : ViewModel() {
    val allCadastrosEvent = repository.getAllCadastro()
}