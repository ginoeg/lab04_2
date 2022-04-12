package com.eguia.lab04

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

const val ACTIVITY_A_REQUEST = 991
const val ACTIVITY_B_REQUEST = 992
const val ACTIVITY_C_REQUEST = 993

const val PARAMETER_EXTRA_NOMBRE_M = "nombre"
const val PARAMETER_EXTRA_CORREO_M = "correo"
const val PARAMETER_EXTRA_DIRECCION_M = "direccion"
const val PARAMETER_EXTRA_NUMERO_M = "numero"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun sendExplicit(view: android.view.View) {
        val nombre = tvNombre.text.toString()
        val correo = tvCorreo.text.toString()
        val direccion = tvlDireccion.text.toString()
        val numero = tvNumero.text.toString()

        validateInputFields(nombre, correo, direccion, numero)
        goDetailActivity(nombre, correo, direccion, numero)
    }

    private fun goDetailActivity(nombre: String, correo: String, direccion: String, numero: String) {
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("nombre", nombre)
        intent.putExtra("correo", correo)
        intent.putExtra("direccion", direccion)
        intent.putExtra("numero", numero)
        startActivityForResult(intent, ACTIVITY_B_REQUEST)
    }

    private fun validateInputFields(nombre: String, correo: String, direccion: String, numero: String) {
        if (nombre.isBlank() && correo.isBlank() && direccion.isBlank() && numero.isBlank()) return
    }

    fun callPhone(view: View) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvNumero.text.toString()))
        startActivity(intent)
    }

    fun sendMessage(view: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + tvNumero.text.toString()))
        intent.putExtra("sms_body", tvNombre.text.toString())
        startActivity(intent)
    }

    fun sendWhatsapp(view: View) {
        var mobileNumber = tvNumero.text.toString()
        val url =
            "https://api.whatsapp.com/send?phone=${mobileNumber}&text=You%20can%20now%20send%20me%20audio%20and%20video%20messages%20on%20the%20app%20-%20Chirp.%20%0A%0Ahttps%3A//bit.ly/chirp_android"

        val intent = Intent(Intent.ACTION_VIEW).apply {
            this.data = Uri.parse(url)
            this.`package` = "com.whatsapp"
        }

        try {
            startActivity(intent)
        } catch (ex : ActivityNotFoundException){
            Toast.makeText(this, "Whatsapp no esta instalado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(ContentValues.TAG, "requestCode:$requestCode")
        Log.d(ContentValues.TAG, "resultCode:$resultCode")
        Log.d(ContentValues.TAG, "data:" + android.R.attr.data)

        when (requestCode) {
            ACTIVITY_C_REQUEST -> Toast.makeText(this, "Regresamos del Activity A", Toast.LENGTH_SHORT).show()
            ACTIVITY_B_REQUEST -> {
                Log.d(ContentValues.TAG, "Regresamos del Activity B")
                if (resultCode === RESULT_OK) {
                    val extras = data?.extras

                    if (extras != null) {
                        if (extras.get(PARAMETER_EXTRA_NOMBRE_M) != null) {
                            tvNombre.text = extras.getString(PARAMETER_EXTRA_NOMBRE_M)
                        }

                        if (extras.get(PARAMETER_EXTRA_CORREO_M) != null) {
                            tvCorreo.text = extras.getString(PARAMETER_EXTRA_CORREO_M)
                        }

                        if (extras.get(PARAMETER_EXTRA_DIRECCION_M) != null) {
                            tvOficina.text = extras.getString(PARAMETER_EXTRA_DIRECCION_M)
                        }

                        if (extras.get(PARAMETER_EXTRA_NUMERO_M) != null) {
                            tvNumero.text = extras.getString(PARAMETER_EXTRA_NUMERO_M)
                        }
                    }
                }

            }
        }

    }
}