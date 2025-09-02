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
    private val PREFS_NAME = "UserProfilePrefs" // Nome do arquivo SharedPreferences

    // Chaves para SharedPreferences
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

    // Variáveis para armazenar os dados recebidos
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
        nomeCompleto = intent.getStringExtra("NOME_COMPLETO")
        email = intent.getStringExtra("EMAIL")
        rua = intent.getStringExtra("RUA")
        cidade = intent.getStringExtra("CIDADE")
        recebeNewsletter = intent.getBooleanExtra("RECEBE_NEWSLETTER", false)
        temaApp = intent.getStringExtra("TEMA_APP")

        // Exibir dados recebidos
        exibirDados()


        buttonEditarPerfil.setOnClickListener {
            // Voltar para PerfilActivity, limpando as Activities intermediárias da pilha
            val intent = Intent(this, PerfilActivity::class.java).apply {
                // Passar os dados atuais de volta para pré-preenchimento, se desejar
                // ou simplesmente limpar e começar de novo
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            finish() // Finaliza a ResumoActivity
        }

        buttonFinalizarResumo.setOnClickListener {
            salvarDadosSharedPreferences()
            Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_LONG).show()
            // Opcionalmente, você pode querer fechar o app ou ir para uma tela inicial
            // finishAffinity() // Fecha todas as activities do app
        }
    }

    private fun exibirDados() {
        textViewValorNomeCompleto.text = nomeCompleto ?: "Não informado"
        textViewValorEmail.text = email ?: "Não informado"
        textViewValorRua.text = rua ?: "Não informado"
        textViewValorCidade.text = cidade ?: "Não informado"
        textViewValorNewsletter.text = if (recebeNewsletter) "Sim" else "Não"
        textViewValorTemaApp.text = temaApp ?: "Não informado"
        Log.d(TAG, "Dados exibidos no resumo")
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

        editor.apply() // Salva de forma assíncrona
        Log.d(TAG, "Dados salvos no SharedPreferences")
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Activity Visível")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Activity Interativa")
        // Os dados já foram carregados via Intent em onCreate neste caso.
        // Se viesse de um estado salvo (sem Intent), carregaria aqui.
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity Pausada")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Activity Não Visível")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: Activity Reiniciada")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Activity Destruída")
    }
}
