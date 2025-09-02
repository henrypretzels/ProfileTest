package com.example.profiletest // Substitua pelo seu nome de pacote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class EnderecoActivity : AppCompatActivity() {
    private val PREFS_NAME = "UserProfilePrefs"
    private val KEY_NOME = "nomeCompleto"
    private val KEY_EMAIL = "email"
    private val KEY_RUA = "rua"
    private val KEY_CIDADE = "cidade"
    private val KEY_NEWSLETTER = "newsletter"
    private val KEY_TEMA = "tema"


    private val TAG = "EnderecoActivityLifecycle"

    private lateinit var editTextRua: TextInputEditText
    private lateinit var editTextCidade: TextInputEditText
    private lateinit var buttonVoltarEndereco: Button
    private lateinit var buttonProximoEndereco: Button

    private var nomeCompleto: String? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_endereco)
        carregarPrefPreenchimento()
        Log.d(TAG, "onCreate: Activity Criada")

        // Receber dados da PerfilActivity
        nomeCompleto = intent.getStringExtra("NOME_COMPLETO")
        email = intent.getStringExtra("EMAIL")
        Log.d(TAG, "Dados recebidos: Nome=$nomeCompleto, Email=$email")


        // Inicializar Views
        editTextRua = findViewById(R.id.editTextRua)
        editTextCidade = findViewById(R.id.editTextCidade)
        buttonVoltarEndereco = findViewById(R.id.buttonVoltarEndereco)
        buttonProximoEndereco = findViewById(R.id.buttonProximoEndereco)

        buttonVoltarEndereco.setOnClickListener {
            finish() // Volta para a Activity anterior na pilha (PerfilActivity)
        }

        buttonProximoEndereco.setOnClickListener {
            val rua = editTextRua.text.toString()
            val cidade = editTextCidade.text.toString()

            val intent = Intent(this, PreferenciasActivity::class.java).apply {
                putExtra("NOME_COMPLETO", nomeCompleto)
                putExtra("EMAIL", email)
                putExtra("RUA", rua)
                putExtra("CIDADE", cidade)
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
        // Carregar dados salvos se existirem
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