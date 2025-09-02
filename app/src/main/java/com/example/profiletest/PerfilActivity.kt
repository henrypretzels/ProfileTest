package com.example.profiletest // Substitua com.example.profiletest pelo seu nome de pacote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class PerfilActivity : AppCompatActivity() {

    private val TAG = "PerfilActivityLifecycle" // Para o Logcat

    private lateinit var editTextNomeCompleto: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var buttonProximoPerfil: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
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
}
