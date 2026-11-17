package com.example.sol

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sol.databinding.ActivityJogosBinding

class JogosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJogosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityJogosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.voltarMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnVelha.setOnClickListener {
            val intent = Intent(this, VelhaActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSequencia.setOnClickListener {
            val intent = Intent(this, SequenciaActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}