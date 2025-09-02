package com.example.profiletest // Substitua pelo seu nome de pacote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class PreferenciasActivity : AppCompatActivity() {

    private val TAG = "PreferenciasActivityLifecycle"

    private lateinit var switchNewsletter: SwitchMaterial
    private lateinit var spinnerTema: Spinner
    private lateinit var buttonVoltarPreferencias: Button
    private lateinit var buttonProximoPreferencias: Button

    private var nomeCompleto: String? = null
    private var email: String? = null
    private var rua: String? = null
    private var cidade: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)
        Log.d(TAG, "onCreate: Activity Criada")

        // Receber dados da EnderecoActivity
        nomeCompleto = intent.getStringExtra("NOME_COMPLETO")
        email = intent.getStringExtra("EMAIL")
        rua = intent.getStringExtra("RUA")
        cidade = intent.getStringExtra("CIDADE")
        Log.d(TAG, "Dados recebidos: Nome=$nomeCompleto, Email=$email, Rua=$rua, Cidade=$cidade")

        // Inicializar Views
        switchNewsletter = findViewById(R.id.switchNewsletter)
        spinnerTema = findViewById(R.id.spinnerTema)
        buttonVoltarPreferencias = findViewById(R.id.buttonVoltarPreferencias)
        buttonProximoPreferencias = findViewById(R.id.buttonProximoPreferencias)

        // Configurar Spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.temas_array, // Certifique-se de ter este array em strings.xml
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTema.adapter = adapter
        }

        buttonVoltarPreferencias.setOnClickListener {
            finish() // Volta para a EnderecoActivity
        }

        buttonProximoPreferencias.setOnClickListener {
            val recebeNewsletter = switchNewsletter.isChecked
            val temaSelecionado = spinnerTema.selectedItem.toString()

            val intent = Intent(this, ResumoActivity::class.java).apply {
                putExtra("NOME_COMPLETO", nomeCompleto)
                putExtra("EMAIL", email)
                putExtra("RUA", rua)
                putExtra("CIDADE", cidade)
                putExtra("RECEBE_NEWSLETTER", recebeNewsletter)
                putExtra("TEMA_APP", temaSelecionado)
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
        // Carregar dados salvos
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
