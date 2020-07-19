package com.example.login

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var txtUser = ""
    var txtpass = ""
    public var acceso = false
    public var regitrar = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    val TextUser = findViewById<EditText>(R.id.campoUsuario)
    val Textpass = findViewById<EditText>(R.id.campoPass)
    val TextReg = findViewById<TextView>(R.id.reg)
        val Err = findViewById<TextView>(R.id.err) as TextView
        Err.visibility = TextView.INVISIBLE




    val queue = Volley.newRequestQueue(this)
        val iniSesion = findViewById<TextView>(R.id.iniSesion)
//Inicia sesion
        iniSesion.setOnClickListener {
                txtpass = Textpass.text.toString()
            txtUser = TextUser.text.toString()



           println("ELLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL $txtUser")
            println("ELLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL $txtpass")
              validUser()
        }

//Registro de usuarios
        TextReg.setOnClickListener {

            val inten = Intent(this,Main2Activity::class.java)
            var menu = "registro"
            val b : Bundle = Bundle()
            b.putString("opcion",menu)
            inten.putExtras(b)
            startActivity(inten)



//            val intent = Intent(this,Main2Activity::class.java).apply {

  //          }

    //        startActivity(intent)



        }

    }

    private fun validUser() {


        val url = "https://dbsi.000webhostapp.com/BDremotaAndroid/wsJsonConsulta.php?nombreUsuario=$txtUser"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url , null, Response.Listener {
                response ->
            Toast.makeText(this,"$response",Toast.LENGTH_LONG ).show()
            val datos = Usuario()
            var jsonArray = response.getJSONArray("usuario")
            //-------var jsonObject = JSONObject().getJSONObject("usuario")
            println("objeto JSONARRAY $jsonArray")
            var jsonObject = JSONObject(jsonArray.getString(0))
            println("objeto JSON $jsonObject")
            datos.nomUser = jsonObject.optString("nombreUsuario")
            datos.pass = jsonObject.optString("password")



            //comparamos datos con los campos
            if  (datos.nomUser.equals(campoUsuario.text.toString().toLowerCase())&&datos.pass.equals(campoPass.text.toString())){
                Toast.makeText(this,"Valido",Toast.LENGTH_LONG ).show()



                val inten = Intent(this,Main2Activity::class.java)
                var menu = "sesion"
                val b : Bundle = Bundle()
                b.putString("opcion",menu)
                inten.putExtras(b)
                startActivity(inten)



            //en caso de que sea coincidan iniciamos la sig actividad
       //         val intent = Intent(this,Main2Activity::class.java).apply {

         //       }
         //       startActivity(intent)


            }else{
                err.visibility = TextView.VISIBLE

            }


            println(datos.nomUser + " " + datos.pass)

            campoUsuario.setText("")
            campoPass.setText("")









        },
            Response.ErrorListener { error ->
                Toast.makeText(this,"$error",Toast.LENGTH_LONG ).show()
                val Err = findViewById<TextView>(R.id.err) as TextView
                Err.visibility = TextView.VISIBLE
             campoPass.setText("")

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    fun registraUsuario(){

        val url=
            "http://localhost/BDremotaAndroid/WSJsonRegistro.php?nombreUsuario=$txtUser&password=$txtpass"
        url.replace(" ", "%20")
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
               // TextReg.text = "Response: %s".format(response.toString())
                Toast.makeText(this,"BIEN",Toast.LENGTH_LONG ).show()

            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Mal",Toast.LENGTH_LONG ).show()


            }

        )

       MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
        Toast.makeText(this,"BOTON",Toast.LENGTH_LONG ).show()
    }



}
