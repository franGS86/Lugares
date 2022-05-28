package com.example.lugares

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lugares.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        // se define el metodo para el login y registro en los botones de login y registro
        binding.btnlogin.setOnClickListener{
            hacerLogin();
        }
        binding.btnRegistrar.setOnClickListener{
            hacerRegistro();
        }
    }
    // funciones de login y registro
    private fun hacerRegistro(){
        val email = binding.textInputEmail.text.toString()
        val password = binding.textInputPassword.text.toString()
    // este es el metodo para hace el registro pasandole los parametros creado una tarea asincrona
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
        task ->
        //se pregunta si la tarea se logro se hace el registro
        if (task.isSuccessful){
            //este Log.d es para mostrar un mensaje en pantalla
            Log.d("Usuario creado","Registrado")
            //recupera lo ingresado y lo ingresa a la variable
            val user = auth.currentUser
            //funcion actuaizar
            actualiza(user)
        }else{
            //este Log.d y Toast.makeText es para mostrar un mensaje en pantalla
            Log.d("Usuario creado","Error al realizar el registro")
            Toast.makeText(baseContext,"Error", Toast.LENGTH_LONG).show()
            //al falla el registro se ingres null en la funcion actualizar
            actualiza(null)
        }
    }
    }

    private fun actualiza(user: FirebaseUser?) {
        //se pasa un parametro el usuario y si el usuario es diferente de null para poder pasar de pantalla
        if (user != null){
            //se hace el intento de pasar de pantalla
            val intent = Intent(this, Principal::class.java)
            //se inicia otro activity
            startActivity(intent)
        }
    }
    // esta funcion se va ajecutar cuanto la aplicacion se presente en la pantalla esto hara que una vez
    // no pida mas a menos que se cierre la session
    public override fun onStart(){
        //
        super.onStart()
    //valida si el usuario ya esta conectado para que no pida el usuario y el password
        val usuario = auth.currentUser
        actualiza(usuario)
    }

    private fun hacerLogin(){
        val email = binding.textInputEmail.text.toString()
        val password = binding.textInputPassword.text.toString()
        // este es el metodo para hace login pasandole los parametros creado una tarea asincrona
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                task ->
            //se pregunta si la tarea se logro se hace el registro
            if (task.isSuccessful){
                //este Log.d es para mostrar un mensaje en pantalla
                Log.d("Autenticando","Autenticado")
                //recupera lo ingresado y lo ingresa a la variable
                val user = auth.currentUser
                //funcion actuaizar
                actualiza(user)
            }else{
                //este Log.d y Toast.makeText es para mostrar un mensaje en pantalla
                Log.d("Autenticado","Error al realizar la autenticacion")
                Toast.makeText(baseContext,"Error", Toast.LENGTH_LONG).show()
                //al falla el registro se ingres null en la funcion actualizar
                actualiza(null)
            }
        }
    }
}