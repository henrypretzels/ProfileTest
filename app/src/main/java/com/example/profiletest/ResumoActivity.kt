package com.example.profiletest // Substitua pelo seu nome de pacote

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResumoActivity : AppCompatActivity() {

    private val TAG = "ResumoActivityLifecycle"
    private val PREFS_NAME = "UserProfilePrefs"

    private val KEY_NOME = "nomeCompleto"
    private val KEY_EMAIL = "email"
    private val KEY_RUA = "rua"
    private val KEY_CIDADE = "cidade"
    private val KEY_NEWSLETTER = "recebeNewsletter"
    private val KEY_TEMA = "temaApp"

    private lateinit var textViewValorNomeCompleto: TextView
    private lateinit var textViewValorEmail: TextView
    private lateinit var textViewValorRua: TextView
    private lateinit var textViewValorCidade: TextView
    private lateinit var textViewValorNewsletter: TextView
    private lateinit var textViewValorTemaApp: TextView
    private lateinit var buttonEditarPerfil: Button
    private lateinit var buttonFinalizarResumo: Button

    private var nomeCompleto: String? = null
    private var email: String? = null
    private var rua: String? = null
    private var cidade: String? = null
    private var recebeNewsletter: Boolean = false
    private var temaApp: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumo)
        Log.d(TAG, "onCreate: Activity Criada")

        // Inicializar Views
        textViewValorNomeCompleto = findViewById(R.id.textViewValorNomeCompleto)
        textViewValorEmail = findViewById(R.id.textViewValorEmail)
        textViewValorRua = findViewById(R.id.textViewValorRua)
        textViewValorCidade = findViewById(R.id.textViewValorCidade)
        textViewValorNewsletter = findViewById(R.id.textViewValorNewsletter)
        textViewValorTemaApp = findViewById(R.id.textViewValorTemaApp)
        buttonEditarPerfil = findViewById(R.id.buttonEditarPerfil)
        buttonFinalizarResumo = findViewById(R.id.buttonFinalizarResumo)

        // Receber dados da PreferenciasActivity
        nomeCompleto = intent.getStringExtra(KEY_NOME) // Usar as constantes KEY_ aqui também é uma boa prática
        email = intent.getStringExtra(KEY_EMAIL)
        rua = intent.getStringExtra(KEY_RUA)
        cidade = intent.getStringExtra(KEY_CIDADE)
        recebeNewsletter = intent.getBooleanExtra(KEY_NEWSLETTER, false)
        temaApp = intent.getStringExtra(KEY_TEMA)

        // Log para verificar os dados recebidos ANTES de exibi-los
        Log.d(TAG, "DADOS RECEBIDOS NA RESUMO (do Intent):")
        Log.d(TAG, "Nome: $nomeCompleto")
        Log.d(TAG, "Email: $email")
        Log.d(TAG, "Rua: $rua")
        Log.d(TAG, "Cidade: $cidade")
        Log.d(TAG, "Newsletter: $recebeNewsletter")
        Log.d(TAG, "Tema: $temaApp")

        // Exibir dados recebidos SOMENTE DEPOIS de carregá-los
        exibirDados()

        buttonEditarPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            finish()
        }

        buttonFinalizarResumo.setOnClickListener {
            salvarDadosSharedPreferences()
            Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_LONG).show()
        }
    }

    private fun exibirDados() {
        textViewValorNomeCompleto.text = nomeCompleto ?: "Não informado"
        textViewValorEmail.text = email ?: "Não informado"
        textViewValorRua.text = rua ?: "Não informado"
        textViewValorCidade.text = cidade ?: "Não informado"
        textViewValorNewsletter.text = if (recebeNewsletter) "Sim" else "Não"
        textViewValorTemaApp.text = temaApp ?: "Não informado"
        Log.d(TAG, "Dados exibidos no resumo (após atribuição aos TextViews)")
    }

    private fun salvarDadosSharedPreferences() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString(KEY_NOME, nomeCompleto)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_RUA, rua)
        editor.putString(KEY_CIDADE, cidade)
        editor.putBoolean(KEY_NEWSLETTER, recebeNewsletter)
        editor.putString(KEY_TEMA, temaApp)

        editor.apply()
        Log.d(TAG, "Dados salvos no SharedPreferences")
    }

}

