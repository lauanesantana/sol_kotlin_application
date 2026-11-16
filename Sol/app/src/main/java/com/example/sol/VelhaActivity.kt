package com.example.sol

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sol.databinding.ActivityVelhaBinding

class VelhaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVelhaBinding

    private lateinit var casas: List<ImageView>

    private val tabuleiro = IntArray(9)

    private var pontosJog = 0
    private var pontosApp = 0
    private var qntVelha = 0

    private var vezDoApp = false
    private var jogoAcabou = false

    private val handler = Handler(Looper.getMainLooper())
    private val jogadaApp = Runnable { jogarApp() }

    private val combinacoes = listOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityVelhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        casas = listOf(
            binding.i0, binding.i1, binding.i2,
            binding.i3, binding.i4, binding.i5,
            binding.i6, binding.i7, binding.i8
        )

        casas.forEachIndexed { indice, casa ->
            casa.setOnClickListener { jogarJogador(indice) }
        }

        binding.btnReiniciar.setOnClickListener { reiniciarJogo() }
        binding.btnSair2.setOnClickListener { msgSair() }

    }

    private fun jogarJogador(indice: Int) {
        when {
            jogoAcabou -> return
            tabuleiro[indice] != 0 -> toast("Não pode jogar aí!")
            vezDoApp -> toast("Ainda não é sua vez...")
            else -> {
                casas[indice].setImageResource(R.drawable.xis)
                tabuleiro[indice] = 1

                if (verificarVencedor() == 1) {
                    toast("Você ganhou!")
                    pontosJog++
                    binding.pontoJogador.text = pontosJog.toString()
                    finalizarJogo()
                } else if (deuVelha()) {
                    toast("Deu velha!")
                    qntVelha++
                    finalizarJogo()
                } else {
                    vezDoApp = true
                    handler.postDelayed(jogadaApp, 1000)
                }
            }
        }
    }

    private fun jogarApp() {
        val livres = tabuleiro.indices.filter { tabuleiro[it] == 0 }
        if (livres.isEmpty()) return

        val escolha = livres.random()
        casas[escolha].setImageResource(R.drawable.circulo)
        tabuleiro[escolha] = 2
        vezDoApp = false

        if (verificarVencedor() == 2) {
            toast("App ganhou!")
            pontosApp++
            binding.pontoApp.text = pontosApp.toString()
            finalizarJogo()
        } else if (deuVelha()) {
            toast("Deu velha!")
            qntVelha++
            finalizarJogo()
        }
    }

    private fun verificarVencedor(): Int {
        for ((a, b, c) in combinacoes) {
            if (tabuleiro[a] != 0 && tabuleiro[a] == tabuleiro[b] && tabuleiro[b] == tabuleiro[c]) {
                return tabuleiro[a]
            }
        }
        return 0
    }

    private fun deuVelha(): Boolean = tabuleiro.none { it == 0 }

    private fun finalizarJogo() {
        jogoAcabou = true
        vezDoApp = false

    }

    private fun reiniciarJogo() {
        handler.removeCallbacks(jogadaApp)
        tabuleiro.fill(0)
        casas.forEach { it.setImageResource(R.drawable.vazio) }
        vezDoApp = false
        jogoAcabou = false
    }


    private fun msgSair() {
        AlertDialog.Builder(this)
            .setTitle("Deseja mesmo sair do jogo?")
            .setNegativeButton("Não") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Sim") { _, _ ->
                startActivity(Intent(this, JogosActivity::class.java))
                finish()
            }
            .show()
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        handler.removeCallbacks(jogadaApp)
        super.onDestroy()
    }
}