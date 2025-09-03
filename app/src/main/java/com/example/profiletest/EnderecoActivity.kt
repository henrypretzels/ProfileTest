package com.example.profiletest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import android.content.Context
import android.content.SharedPreferences

class EnderecoActivity : AppCompatActivity() {

    private val TAG = "EnderecoActivityLifecycle"

    private lateinit var editTextRua: TextInputEditText
    private lateinit var editTextCidade: TextInputEditText
    private lateinit var buttonVoltarEndereco: Button
    private lateinit var buttonProximoEndereco: Button

    private val PREFS_NAME = "UserProfilePrefs"
    private val KEY_NOME = "nomeCompleto"
    private val KEY_EMAIL = "email"
    private val KEY_RUA = "rua"
    private val KEY_CIDADE = "cidade"

    private lateinit var sharedPreferences: SharedPreferences

    private var nomeCompleto: String? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_endereco)
        Log.d(TAG, "onCreate: Activity Criada")

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        nomeCompleto = intent.getStringExtra("NOME_COMPLETO")
        email = intent.getStringExtra("EMAIL")
        Log.d(TAG, "Dados recebidos via Intent: Nome=$nomeCompleto, Email=$email")

        editTextRua = findViewById(R.id.editTextRua)
        editTextCidade = findViewById(R.id.editTextCidade)
        buttonVoltarEndereco = findViewById(R.id.buttonVoltarEndereco)
        buttonProximoEndereco = findViewById(R.id.buttonProximoEndereco)

        carregarDadosSalvos()

        buttonVoltarEndereco.setOnClickListener {
            finish()
        }

        buttonProximoEndereco.setOnClickListener {
            val rua = editTextRua.text.toString()
            val cidade = editTextCidade.text.toString()


            salvarTodosDadosTemporarios(nomeCompleto, email, rua, cidade)

            val nextIntent = Intent(this, PreferenciasActivity::class.java).apply {
                putExtra(KEY_NOME, nomeCompleto)
                putExtra(KEY_EMAIL, email)
                putExtra(KEY_RUA, rua)
                putExtra(KEY_CIDADE, cidade)
            }
            startActivity(nextIntent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Activity Visível")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Activity Interativa")
        carregarDadosSalvos()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity Pausada")
        val ruaAtual = editTextRua.text.toString()
        val cidadeAtual = editTextCidade.text.toString()
        if (ruaAtual.isNotBlank() || cidadeAtual.isNotBlank()) {
            salvarDadosTemporarios(ruaAtual, cidadeAtual)
        }
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

    private fun carregarDadosSalvos() {
        val ruaSalva = sharedPreferences.getString(KEY_RUA, null)
        val cidadeSalva = sharedPreferences.getString(KEY_CIDADE, null)

        if (ruaSalva != null) {
            editTextRua.setText(ruaSalva)
        }
        if (cidadeSalva != null) {
            editTextCidade.setText(cidadeSalva)
        }
        Log.d(TAG, "Dados carregados SharedPreferences: Rua=$ruaSalva, Cidade=$cidadeSalva")

        if (nomeCompleto == null) nomeCompleto = sharedPreferences.getString(KEY_NOME, null)
        if (email == null) email = sharedPreferences.getString(KEY_EMAIL, null)
    }

    private fun salvarDadosTemporarios(rua: String, cidade: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_RUA, rua)
        editor.putString(KEY_CIDADE, cidade)
        editor.apply()
        Log.d(TAG, "Dados temporários de endereço salvos: Rua=$rua, Cidade=$cidade")
    }

    private fun salvarTodosDadosTemporarios(nome: String?, email: String?, rua: String, cidade: String) {
        val editor = sharedPreferences.edit()
        nome?.let { editor.putString(KEY_NOME, it) }
        email?.let { editor.putString(KEY_EMAIL, it) }
        editor.putString(KEY_RUA, rua)
        editor.putString(KEY_CIDADE, cidade)
        editor.apply()
        Log.d(TAG, "Todos os dados temporários salvos antes de navegar: Nome=$nome, Email=$email, Rua=$rua, Cidade=$cidade")
    }
}