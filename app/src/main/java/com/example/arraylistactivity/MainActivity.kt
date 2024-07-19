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

        val database = MyDatabaseHelper(this)

        var listUsers = database.readUsers()
        var adapter = setAdapter(listUsers)

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
        listUsers.contains(binding.username.text.toString())

        //Remover um item da lista
        lista.remove(100)

        //Tamanho da lista
        lista.size

        //Limpas a lista
        lista.clear()
         */

        binding.listaItens.setOnItemClickListener { parent, view, position, id ->
            binding.id.text = listUsers.get(position).id.toString()
            binding.inputUsername.setText(listUsers.get(position).username)
            binding.inputPassword.setText(listUsers.get(position).password)
            binding.inputEmail.setText(listUsers.get(position).email)
            binding.inputCellphone.setText(listUsers.get(position).cellphone)
            this.position = position
        }


        //Atualizar o evento de cadastrar verficando se a conta de usuario ja existe
        binding.registerButton.setOnClickListener {
            if (!binding.inputUsername.text.toString().trim().isEmpty() &&
                !binding.inputPassword.text.toString().trim().isEmpty()&&
                !binding.inputEmail.text.toString().trim().isEmpty()&&
                !binding.inputCellphone.text.toString().trim().isEmpty()
            ) {

                database.insertUser(binding.inputUsername.text.toString(),
                    binding.inputPassword.text.toString(),
                    binding.inputEmail.text.toString(),
                    binding.inputCellphone.text.toString())

                listUsers = database.readUsers()
                adapter = setAdapter(listUsers)
                binding.listaItens.adapter = adapter

                //adapter.notifyDataSetChanged()
                ClearFields()
            } else {
                Snackbar.make(binding.root, "Campos Vazios!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.purple))
                    .setTextColor(resources.getColor(R.color.white))
                    .show()
            }
        }

        binding.updateButton.setOnClickListener {
            var id = binding.id.text.toString().toInt()
            var nome = binding.inputUsername.text.toString()
            var telefone = binding.inputCellphone.text.toString()
            var email = binding.inputEmail.text.toString()
            var senha = binding.inputPassword.text.toString()

            var user = User(id,nome,senha,email,telefone)

            database.updateUser(user)

            listUsers = database.readUsers()
            adapter = setAdapter(listUsers)
            binding.listaItens.adapter = adapter

            ClearFields()
        }

        //Criar o evento para excluir usuarios
        /*
        binding.deleteButton.setOnClickListener {
            if (position >= 0){
                listUsers.remove(listUsers.get(position))
                adapter.notifyDataSetChanged()
                ClearFields()
                position = -1
            }else{
                Snackbar.make(binding.root, "Selecione um item!", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(R.color.purple))
                    .setTextColor(resources.getColor(R.color.white))
                    .show()
            }
        }
        */

        //adapter.notifyDataSetChanged()
        adapter = setAdapter(listUsers)

        ClearFields()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun ClearFields(){
        binding.inputUsername.setText("")
        binding.inputPassword.setText("")
        binding.inputEmail.setText("")
        binding.inputCellphone.setText("")
    }

    fun updateListView(){

    }

    fun setAdapter(array:List<User>): ArrayAdapter<User>{
        return ArrayAdapter(this, android.R.layout.simple_list_item_1,array)
    }
}