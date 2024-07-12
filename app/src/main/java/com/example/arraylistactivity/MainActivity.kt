package com.example.arraylistactivity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.arraylistactivity.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listUsers = ArrayList<User>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listUsers)

        binding.listaItens.adapter = adapter

        /*
        //Adicionar Elementos
        lista.add(10)
        lista.add(100)
        lista.add(1000)
        lista.add(10000)

        //Capturar um valor da lista
        lista.get(3)

        //Verificar a existencia de um item na lista
        lista.contains(5)

        //Remover um item da lista
        lista.remove(100)

        //Tamanho da lista
        lista.size

        //Limpas a lista
        lista.clear()
         */

        binding.listaItens.setOnItemClickListener { parent, view, position, id ->
            binding.username.setText(listUsers.get(position).username)
            binding.password.setText(listUsers.get(position).password)
            this.position = position
        }

        //Atualizar o evento de cadastrar verficando se a conta de usuario ja existe
        binding.registerButton.setOnClickListener {
            if (!binding.username.text.toString().trim().isEmpty() &&
                !binding.password.text.toString().trim().isEmpty()
            ) {

                listUsers.add(
                    User(
                        binding.username.text.toString(),
                        binding.password.text.toString()
                    )
                )
                adapter.notifyDataSetChanged()
            } else {
                Snackbar.make(binding.root, "Campos Vazios!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.purple))
                    .setTextColor(resources.getColor(R.color.white))
                    .show()
            }
        }

        binding.updateButton.setOnClickListener {
            if (position >= 0) {
                listUsers.get(position).username = binding.username.text.toString().trim()
                listUsers.get(position).password = binding.password.text.toString().trim()

                adapter.notifyDataSetChanged()

                binding.username.setText("")
                binding.password.setText("")

                position = -1
            }else{}
        }

        //Criar o evento para excluir usuarios

        binding.deleteButton.setOnClickListener {
            if (position >= 0){
                listUsers.remove(listUsers.get(position))
                adapter.notifyDataSetChanged()
                binding.username.setText("")
                binding.password.setText("")
                position = -1
            }else{
                Snackbar.make(binding.root, "Selecione um item!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.purple))
                    .setTextColor(resources.getColor(R.color.white))
                    .show()
            }
        }


        adapter.notifyDataSetChanged()

        binding.username.setText("")
        binding.password.setText("")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}