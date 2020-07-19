package com.example.login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity(), BlankFragment.OnFragmentInteractionListener, accesoRegistro.OnFragmentInteractionListener, Registrado.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {

    }

    //objeto para mostrar fragments
    val manager = supportFragmentManager






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

            var bundle = intent.extras
            var op = bundle?.getString("opcion")
        println("RECIBIIII $op")

        when (op){
            "sesion" ->  mostrarFragmentAcceso()
          //  "registro" -> mostrarFragmentRegistro()
            else -> {
                textView5.setOnClickListener {
                    registraUsuario()
                }
                textView6.setOnClickListener {
                    val intent = Intent(this,MainActivity::class.java).apply {

                    }
                    startActivity(intent)
                }

            }
        }



           //  mostrarFragmentAcceso()





/*
        textView5.setOnClickListener {
            registraUsuario()
        }
        textView6.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java).apply {

            }
            startActivity(intent)
        }

        */
    }

    fun registraUsuario() {




        var usuario = editText.text.toString().toLowerCase()
        var pass = editText2.text.toString()
        val url =
            "https://dbsi.000webhostapp.com/BDremotaAndroid/WSJsonRegistro.php?nombreUsuario=$usuario&password=$pass"

        url.replace(" ", "%20")
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // TextReg.text = "Response: %s".format(response.toString())
                //Toast.makeText(this, "$response", Toast.LENGTH_LONG).show()
                if (editText.text.toString().equals("")||editText2.text.toString().equals("")) {
                    Toast.makeText(this, "Verifique los campos", Toast.LENGTH_LONG).show()
                }else{

                    mostrarFragmentRegistrado()
                }

            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error en el registro", Toast.LENGTH_LONG).show()
            }

        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    fun mostrarFragmentAcceso() {
        val transaction = manager.beginTransaction()
        val fragment = BlankFragment()
        transaction.replace(R.id.fragment_holder,fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    fun mostrarFragmentRegistrado() {
        val transaction = manager.beginTransaction()
        val fragment = Registrado()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()


    }

}
