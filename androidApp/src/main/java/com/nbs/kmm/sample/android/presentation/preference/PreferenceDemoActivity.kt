package com.nbs.kmm.sample.android.presentation.preference

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.nbs.kmm.sample.android.databinding.ActivityPreferenceDemoBinding
import com.nbs.kmm.sample.data.preference.PreferenceManager
import com.nbs.kmm.sample.utils.emptyString
import org.koin.android.ext.android.inject

class PreferenceDemoActivity : AppCompatActivity() {

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context, PreferenceDemoActivity::class.java))
        }
    }

    private val binding: ActivityPreferenceDemoBinding by lazy {
        ActivityPreferenceDemoBinding.inflate(layoutInflater)
    }

    private val prefereceManager: PreferenceManager by inject()

    private val PREF_KEY_ANYTEXT = "key_anytext"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadPersistedText()

        binding.btnSave.setOnClickListener {
            val anyText = binding.edtQuery.text.toString()
            if (anyText.isNotEmpty()){
                prefereceManager.saveString(PREF_KEY_ANYTEXT, anyText)
                Snackbar.make(this, binding.root, "Saved!", Snackbar.LENGTH_SHORT).show()
            }
            loadPersistedText()
        }

        binding.btnDelete.setOnClickListener {
            prefereceManager.saveString(PREF_KEY_ANYTEXT, emptyString())
        }
    }

    private fun loadPersistedText() {
        binding.edtQuery.setText(prefereceManager.getString(PREF_KEY_ANYTEXT))
    }
}