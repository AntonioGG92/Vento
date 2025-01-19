package com.example.vento.login

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
import com.example.vento.data.DatabaseProvider
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etCorreo = view.findViewById<EditText>(R.id.etCorreo)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val btnIrARegistro = view.findViewById<Button>(R.id.btnIrARegistro) // Botón para navegar al registro

        val db = DatabaseProvider.getDatabase(requireContext())
        val usuarioDao = db.usuarioDAO()

        btnLogin.setOnClickListener {
            val correo = etCorreo.text.toString()
            val password = etPassword.text.toString()

            if (correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Iniciar una coroutine para llamar a la función suspendida
            viewLifecycleOwner.lifecycleScope.launch {
                val usuario = usuarioDao.autenticarUsuario(correo, password)

                if (usuario != null) {
                    if (usuario.isAdmin) {
                        // Navegar al panel de administrador
                        findNavController().navigate(R.id.action_loginFragment_to_adminFragment)
                    } else {
                        // Navegar a la pantalla de categorías (Usuario)
                        findNavController().navigate(R.id.action_loginFragment_to_usuarioPanelFragment)
                    }
                } else {
                    Toast.makeText(requireContext(), "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Lógica para el botón de navegación al RegistroFragment
        btnIrARegistro.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registroFragment)
        }
    }
}
