package com.example.profiletest // Substitua com.example.profiletest pelo seu nome de pacote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class PerfilActivity : AppCompatActivity() {
    private val PREFS_NAME = "UserProfilePrefs"
    private val KEY_NOME = "nomeCompleto"
    private val KEY_EMAIL = "email"
    private val KEY_RUA = "rua"
    private val KEY_CIDADE = "cidade"
    private val KEY_NEWSLETTER = "newsletter"
    private val KEY_TEMA = "tema"


    private val TAG = "PerfilActivityLifecycle" // Para o Logcat

    private lateinit var editTextNomeCompleto: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var buttonProximoPerfil: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        carregarPrefPreenchimento()
        Log.d(TAG, "onCreate: Activity Criada")

        // Inicializar Views
        editTextNomeCompleto = findViewById(R.id.editTextNomeCompleto)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonProximoPerfil = findViewById(R.id.buttonProximoPerfil)

        // Configurar Listener para o botão Próximo
        buttonProximoPerfil.setOnClickListener {
            val nome = editTextNomeCompleto.text.toString()
            val email = editTextEmail.text.toString()

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
        // Aqui você pode carregar dados do SharedPreferences se necessário
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity Pausada - Salvar dados temporários se necessário")
        // Aqui você pode salvar dados temporariamente
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

    private fun carregarPrefPreenchimento() {
        val sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val nome = sp.getString(KEY_NOME, null)
        val email = sp.getString(KEY_EMAIL, null)
        val rua = sp.getString(KEY_RUA, null)
        val cidade = sp.getString(KEY_CIDADE, null)
        val newsletter = sp.getBoolean(KEY_NEWSLETTER, false)
        val tema = sp.getString(KEY_TEMA, null)

        // Preencher campos se as Views existirem nesta Activity
        try {
            findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextNomeCompleto)?.setText(nome)
        } catch (_: Exception) {}
        try {
            findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextEmail)?.setText(email)
        } catch (_: Exception) {}
        try {
            findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextRua)?.setText(rua)
        } catch (_: Exception) {}
        try {
            findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextCidade)?.setText(cidade)
        } catch (_: Exception) {}
        try {
            findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.switchNewsletter)?.isChecked = newsletter
        } catch (_: Exception) {}
        try {
            val spinner = findViewById<android.widget.Spinner>(R.id.spinnerTema)
            if (spinner != null && tema != null) {
                val adapter = spinner.adapter
                if (adapter != null) {
                    for (i in 0 until adapter.count) {
                        if (adapter.getItem(i).toString() == tema) {
                            spinner.setSelection(i)
                            break
                        }
                    }
                }
            }
        } catch (_: Exception) {}
    }


    private fun salvarRascunho() {
        val sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val ed = sp.edit()
        try { ed.putString(KEY_NOME, findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextNomeCompleto)?.text?.toString()) } catch (_: Exception) {}
        try { ed.putString(KEY_EMAIL, findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextEmail)?.text?.toString()) } catch (_: Exception) {}
        try { ed.putString(KEY_RUA, findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextRua)?.text?.toString()) } catch (_: Exception) {}
        try { ed.putString(KEY_CIDADE, findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editTextCidade)?.text?.toString()) } catch (_: Exception) {}
        try { ed.putBoolean(KEY_NEWSLETTER, findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.switchNewsletter)?.isChecked == true) } catch (_: Exception) {}
        try { 
            val spn = findViewById<android.widget.Spinner>(R.id.spinnerTema)
            ed.putString(KEY_TEMA, spn?.selectedItem?.toString())
        } catch (_: Exception) {}
        ed.apply()
    }

}