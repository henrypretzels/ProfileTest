package com.example.profiletest // Substitua com.example.profiletest pelo seu nome de pacote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import android.content.Context
import android.content.SharedPreferences

class PerfilActivity : AppCompatActivity() {

    private val TAG = "PerfilActivityLifecycle" // Para o Logcat

    private lateinit var editTextNomeCompleto: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var buttonProximoPerfil: Button

    private val PREFS_NAME = "UserProfilePrefs"
    private val KEY_NOME = "nomeCompleto"
    private val KEY_EMAIL = "email"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        Log.d(TAG, "onCreate: Activity Criada")

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Inicializar Views
        editTextNomeCompleto = findViewById(R.id.editTextNomeCompleto)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonProximoPerfil = findViewById(R.id.buttonProximoPerfil)

        // Carregar dados salvos ao iniciar a activity
        carregarDadosSalvos()

        // Configurar Listener para o botão Próximo
        buttonProximoPerfil.setOnClickListener {
            val nome = editTextNomeCompleto.text.toString()
            val email = editTextEmail.text.toString()

            // Salvar dados temporariamente antes de navegar para a próxima tela
            salvarDadosTemporarios(nome, email)

            val intent = Intent(this, EnderecoActivity::class.java).apply {
                putExtra("NOME_COMPLETO", nome)
                putExtra("EMAIL", email)
            }
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Activity Visível")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Activity Interativa")
        // Opcional: recarregar dados aqui para garantir que a interface reflita
        // qualquer mudança feita por outra activity ou se o app foi para o background.
        carregarDadosSalvos()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity Pausada - Salvar dados temporários se necessário")
        // Salvar o estado atual dos campos para evitar perda de dados
        // se a activity for destruída pelo sistema.
        val nomeAtual = editTextNomeCompleto.text.toString()
        val emailAtual = editTextEmail.text.toString()
        if (nomeAtual.isNotBlank() || emailAtual.isNotBlank()) {
            salvarDadosTemporarios(nomeAtual, emailAtual)
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

    // --- Funções de SharedPreferences ---

    /**
     * Carrega os dados salvos do SharedPreferences e preenche os campos de texto.
     */
    private fun carregarDadosSalvos() {
        val nomeSalvo = sharedPreferences.getString(KEY_NOME, null)
        val emailSalvo = sharedPreferences.getString(KEY_EMAIL, null)

        if (nomeSalvo != null) {
            editTextNomeCompleto.setText(nomeSalvo)
        }
        if (emailSalvo != null) {
            editTextEmail.setText(emailSalvo)
        }
        Log.d(TAG, "Dados carregados: Nome=$nomeSalvo, Email=$emailSalvo")
    }

    /**
     * Salva os dados dos campos de texto temporariamente no SharedPreferences.
     * @param nome O nome a ser salvo.
     * @param email O email a ser salvo.
     */
    private fun salvarDadosTemporarios(nome: String, email: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NOME, nome)
        editor.putString(KEY_EMAIL, email)
        editor.apply()
        Log.d(TAG, "Dados temporários salvos: Nome=$nome, Email=$email")
    }
}