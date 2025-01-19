package com.example.vento.registro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.vento.R
import com.example.vento.data.AppDatabase
import com.example.vento.data.entities.Usuario
import kotlinx.coroutines.launch

class RegistroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etNombre = view.findViewById<EditText>(R.id.etNombre)
        val etCorreo = view.findViewById<EditText>(R.id.etCorreo)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = view.findViewById<EditText>(R.id.etConfirmPassword)
        val btnRegistrar = view.findViewById<Button>(R.id.btnRegistrar)
        val btnIrALogin = view.findViewById<Button>(R.id.btnIrALogin)

        // Obtén la instancia de la base de datos
        val db = AppDatabase.getInstance(requireContext())
        val usuarioDAO = db.usuarioDAO()

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val correo = etCorreo.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                // Guardar el usuario en la base de datos
                lifecycleScope.launch {
                    val nuevoUsuario = Usuario(
                        nombre = nombre,
                        correo = correo,
                        password = password,
                        isAdmin = false // Por defecto, los nuevos usuarios no son administradores
                    )
                    usuarioDAO.insertarUsuario(nuevoUsuario)
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                        // Navegar al LoginFragment después del registro exitoso
                        findNavController().navigate(R.id.action_registroFragment_to_loginFragment)
                    }
                }
            }
        }

        btnIrALogin.setOnClickListener {
            findNavController().navigate(R.id.action_registroFragment_to_loginFragment)
        }
    }
}
