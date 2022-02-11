package com.example.desafio_android_klever.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "cadastro")
class CadastroEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val email: String,
    val cep: String,
    val estado: String,
    val cidade: String,
    val bairro: String,
    val rua: String
): Parcelable