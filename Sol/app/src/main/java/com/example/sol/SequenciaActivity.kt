package com.example.sol

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sol.databinding.ActivitySequenciaBinding

class SequenciaActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySequenciaBinding
    private var fase = 1
    private var atual = 0
    private val opcoesCorretas = hashMapOf(1 to R.drawable.e, 2 to R.drawable.k, 3 to R.drawable.v, 4 to R.drawable.g, 5 to R.drawable.m)
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySequenciaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSair3.setOnClickListener { msgSair() }

        atualizarFase()
        mudarOpcoes()

        binding.recorde.text = "0s"
        iniciarTempo()
    }

    
    private fun iniciarTempo() {
        val runnable = object : Runnable {
            override fun run() {
                atual++
                binding.tempo.text = "${atual}s"
                handler.postDelayed(this, 1000)
            }
        }
        handler.postDelayed(runnable, 1000)
    }

    private fun finalizar() {
        handler.removeCallbacksAndMessages(null)
    }

    private fun mudarOpcoes() {
        binding.opcao1.setOnClickListener {
            val letraEscolhida = binding.opcao1.tag as Int
            opcaoEscolhida(letraEscolhida)
        }
        binding.opcao2.setOnClickListener {
            val letraEscolhida = binding.opcao2.tag as Int
            opcaoEscolhida(letraEscolhida)
        }
        binding.opcao3.setOnClickListener {
            val letraEscolhida = binding.opcao3.tag as Int
            opcaoEscolhida(letraEscolhida)
        }
    }

    private fun opcaoEscolhida(letraEscolhida: Int) {
        val letraCorreta = opcoesCorretas[fase]

        if (letraEscolhida == letraCorreta) {
            Toast.makeText(this, "Você acertou", Toast.LENGTH_SHORT).show()
            fase++
            if (fase <= 5) {
                atualizarFase()
            } else {
                finalizar()
                msgFinal()
            }
        } else {
            Toast.makeText(this, "Tente novamente!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun atualizarFase() {
        when (fase) {
            1 -> {
                binding.letra1.setImageResource(R.drawable.a)
                binding.letra2.setImageResource(R.drawable.b)
                binding.letra3.setImageResource(R.drawable.c)
                binding.letra4.setImageResource(R.drawable.d)
                binding.letra5.setImageResource(R.drawable.interrogacao)
                binding.letra6.setImageDrawable(null)

                binding.opcao1.setImageResource(R.drawable.e)
                binding.opcao1.tag = R.drawable.e
                binding.opcao2.setImageResource(R.drawable.i)
                binding.opcao2.tag = R.drawable.i
                binding.opcao3.setImageResource(R.drawable.f)
                binding.opcao3.tag = R.drawable.f
            }
            2 -> {
                binding.letra1.setImageResource(R.drawable.h)
                binding.letra2.setImageResource(R.drawable.i)
                binding.letra3.setImageResource(R.drawable.j)
                binding.letra4.setImageResource(R.drawable.interrogacao)
                binding.letra5.setImageResource(R.drawable.l)
                binding.letra6.setImageDrawable(null)

                binding.opcao1.setImageResource(R.drawable.m)
                binding.opcao1.tag = R.drawable.m
                binding.opcao2.setImageResource(R.drawable.k)
                binding.opcao2.tag = R.drawable.k
                binding.opcao3.setImageResource(R.drawable.o)
                binding.opcao3.tag = R.drawable.o
            }
            3 -> {
                binding.letra1.setImageResource(R.drawable.u)
                binding.letra2.setImageResource(R.drawable.interrogacao)
                binding.letra3.setImageResource(R.drawable.w)
                binding.letra4.setImageResource(R.drawable.x)
                binding.letra5.setImageResource(R.drawable.y)
                binding.letra6.setImageResource(R.drawable.z)

                binding.opcao1.setImageResource(R.drawable.t)
                binding.opcao1.tag = R.drawable.t
                binding.opcao2.setImageResource(R.drawable.v)
                binding.opcao2.tag = R.drawable.v
                binding.opcao3.setImageResource(R.drawable.s)
                binding.opcao3.tag = R.drawable.s
            }
            4 -> {
                binding.letra1.setImageResource(R.drawable.e)
                binding.letra2.setImageResource(R.drawable.f)
                binding.letra3.setImageResource(R.drawable.interrogacao)
                binding.letra4.setImageResource(R.drawable.h)
                binding.letra5.setImageResource(R.drawable.i)
                binding.letra6.setImageResource(R.drawable.j)

                binding.opcao1.setImageResource(R.drawable.g)
                binding.opcao1.tag = R.drawable.g
                binding.opcao2.setImageResource(R.drawable.l)
                binding.opcao2.tag = R.drawable.l
                binding.opcao3.setImageResource(R.drawable.q)
                binding.opcao3.tag = R.drawable.q
            }
            5 -> {
                binding.letra1.setImageResource(R.drawable.interrogacao)
                binding.letra2.setImageResource(R.drawable.n)
                binding.letra3.setImageResource(R.drawable.o)
                binding.letra4.setImageResource(R.drawable.p)
                binding.letra5.setImageResource(R.drawable.q)
                binding.letra6.setImageResource(R.drawable.r)

                binding.opcao1.setImageResource(R.drawable.w)
                binding.opcao1.tag = R.drawable.w
                binding.opcao2.setImageResource(R.drawable.s)
                binding.opcao2.tag = R.drawable.s
                binding.opcao3.setImageResource(R.drawable.m)
                binding.opcao3.tag = R.drawable.m
            }
        }
    }

    private fun msgFinal() {
        val msg = AlertDialog.Builder(this)
        msg.setTitle("Parabéns, você ganhou!!")
        msg.setPositiveButton("Fechar") { _, _ ->
            val intent = Intent(applicationContext, JogosActivity::class.java)
            startActivity(intent)
            finish()
        }
        msg.show()
    }

    private fun msgSair() {
        val msg = AlertDialog.Builder(this)
        msg.setTitle("Deseja mesmo sair do jogo?")
        msg.setNegativeButton("Não") { dialog, _ ->
            dialog.dismiss()
        }
        msg.setPositiveButton("Sim") { _, _ ->
            val intent = Intent(applicationContext, JogosActivity::class.java)
            startActivity(intent)
            finish()
        }
        msg.show()
    }
}