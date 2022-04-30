package com.eguia.lab04

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*
import com.eguia.lab04.databinding.ActivityMain2Binding

const val PARAMETER_EXTRA_NOMBRE = "nombre"
const val PARAMETER_EXTRA_CORREO = "correo"
const val PARAMETER_EXTRA_DIRECCION = "direccion"
const val PARAMETER_EXTRA_NUMERO = "numero"

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding:ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)


        val view = binding.root

        setContentView(view)

        val extras = this.intent.extras

        if (extras != null) {
            if (extras.get(PARAMETER_EXTRA_NOMBRE) != null) {
                edtNombre.setText(extras.getString(PARAMETER_EXTRA_NOMBRE))

            }

            if (extras.get(PARAMETER_EXTRA_CORREO) != null) {
                edtCorreo.setText(extras.getString(PARAMETER_EXTRA_CORREO))
            }

            if (extras.get(PARAMETER_EXTRA_DIRECCION) != null) {
                edtDireccion.setText(extras.getString(PARAMETER_EXTRA_DIRECCION))
            }
            if (extras.get(PARAMETER_EXTRA_NUMERO) != null) {
                edtNumero.setText(extras.getString(PARAMETER_EXTRA_NUMERO))
            }
        }

        listenerCallButtons()
    }

    fun listenerCallButtons() {
        binding.btnGuardar.setOnClickListener {
            val intent = Intent()
            intent.putExtra("nombre", edtNombre.getText().toString())
            intent.putExtra("correo", edtCorreo.getText().toString())
            intent.putExtra("direccion", edtDireccion.getText().toString())
            intent.putExtra("numero", edtNumero.getText().toString())
            setResult(RESULT_OK, intent) // Set ResultCode and DataIntent
            finish()
        }
    }
}
