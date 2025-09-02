package com.example.profiletest // Substitua pelo seu nome de pacote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class EnderecoActivity : AppCompatActivity() {

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
}
