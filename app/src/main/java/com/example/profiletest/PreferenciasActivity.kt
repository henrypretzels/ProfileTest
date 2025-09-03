package com.example.profiletest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial
import android.content.Context
import android.content.SharedPreferences

class PreferenciasActivity : AppCompatActivity() {

    private val TAG = "PreferenciasActivityLifecycle"

    private lateinit var switchNewsletter: SwitchMaterial
    private lateinit var spinnerTema: Spinner
    private lateinit var buttonVoltarPreferencias: Button
    private lateinit var buttonProximoPreferencias: Button

    private val PREFS_NAME = "UserProfilePrefs"
    private val KEY_NOME = "nomeCompleto"
    private val KEY_EMAIL = "email"
    private val KEY_RUA = "rua"
    private val KEY_CIDADE = "cidade"
    private val KEY_NEWSLETTER = "recebeNewsletter"
    private val KEY_TEMA = "temaApp"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var temasArray: Array<String>

    private var nomeCompleto: String? = null
    private var email: String? = null
    private var rua: String? = null
    private var cidade: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)
        Log.d(TAG, "onCreate: Activity Criada")

        // Initialize SharedPreferences and resources
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        temasArray = resources.getStringArray(R.array.temas_array)

        // Receive data from EnderecoActivity via Intent
        nomeCompleto = intent.getStringExtra(KEY_NOME)
        email = intent.getStringExtra(KEY_EMAIL)
        rua = intent.getStringExtra(KEY_RUA)
        cidade = intent.getStringExtra(KEY_CIDADE)

        Log.d(TAG, "Dados recebidos via Intent: Nome=$nomeCompleto, Email=$email, Rua=$rua, Cidade=$cidade")

        // Initialize Views
        switchNewsletter = findViewById(R.id.switchNewsletter)
        spinnerTema = findViewById(R.id.spinnerTema)
        buttonVoltarPreferencias = findViewById(R.id.buttonVoltarPreferencias)
        buttonProximoPreferencias = findViewById(R.id.buttonProximoPreferencias)

        // Configure the Spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.temas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTema.adapter = adapter
        }

        // Load any previously saved data from SharedPreferences
        carregarDadosSalvos()

        buttonVoltarPreferencias.setOnClickListener {
            finish() // Navigates back to the previous Activity
        }

        buttonProximoPreferencias.setOnClickListener {
            val recebeNewsletter = switchNewsletter.isChecked
            val temaSelecionado = spinnerTema.selectedItem.toString()

            // Save the current preferences
            salvarDadosTemporarios(recebeNewsletter, temaSelecionado)

            val nextIntent = Intent(this, ResumoActivity::class.java).apply {
                // Pass all accumulated data (from all activities) to the final summary screen
                putExtra(KEY_NOME, nomeCompleto ?: sharedPreferences.getString(KEY_NOME, null))
                putExtra(KEY_EMAIL, email ?: sharedPreferences.getString(KEY_EMAIL, null))
                putExtra(KEY_RUA, rua ?: sharedPreferences.getString(KEY_RUA, null))
                putExtra(KEY_CIDADE, cidade ?: sharedPreferences.getString(KEY_CIDADE, null))
                putExtra(KEY_NEWSLETTER, recebeNewsletter)
                putExtra(KEY_TEMA, temaSelecionado)
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
        // Load data to ensure fields are up-to-date
        carregarDadosSalvos()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity Pausada")
        val newsletterAtual = switchNewsletter.isChecked
        val temaAtual = spinnerTema.selectedItem.toString()
        // Save data to prevent loss if the activity is interrupted
        salvarDadosTemporarios(newsletterAtual, temaAtual)
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

    /**
     * Loads saved preference data from SharedPreferences and updates the UI.
     */
    private fun carregarDadosSalvos() {
        // Load all data from SharedPreferences in case the activity was started out of the typical flow
        if (nomeCompleto == null) nomeCompleto = sharedPreferences.getString(KEY_NOME, null)
        if (email == null) email = sharedPreferences.getString(KEY_EMAIL, null)
        if (rua == null) rua = sharedPreferences.getString(KEY_RUA, null)
        if (cidade == null) cidade = sharedPreferences.getString(KEY_CIDADE, null)

        val newsletterSalva = sharedPreferences.getBoolean(KEY_NEWSLETTER, false)
        val temaSalvo = sharedPreferences.getString(KEY_TEMA, temasArray[0])

        switchNewsletter.isChecked = newsletterSalva

        val temaPosition = temasArray.indexOf(temaSalvo)
        if (temaPosition >= 0) {
            spinnerTema.setSelection(temaPosition)
        }
        Log.d(TAG, "Dados carregados SharedPreferences: Newsletter=$newsletterSalva, Tema=$temaSalvo")
    }

    /**
     * Saves the current preferences to SharedPreferences.
     * @param newsletter Boolean indicating if the user wants to receive newsletters.
     * @param tema The selected theme string.
     */
    private fun salvarDadosTemporarios(newsletter: Boolean, tema: String) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_NEWSLETTER, newsletter)
        editor.putString(KEY_TEMA, tema)
        editor.apply()
        Log.d(TAG, "Dados temporários salvos: Newsletter=$newsletter, Tema=$tema")
    }
}