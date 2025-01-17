package com.example.vento.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vento.R
import com.example.vento.databinding.FragmentAdminBinding // Importa el archivo generado de View Binding

class AdminFragment : Fragment() {

    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el diseño usando View Binding
        _binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el botón para gestionar productos
        binding.btnGestionProductos.setOnClickListener {
            findNavController().navigate(R.id.action_adminFragment_to_gestionProductosFragment)
        }

        // Configurar el botón para gestionar categorías
        binding.btnGestionCategorias.setOnClickListener {
            findNavController().navigate(R.id.action_adminFragment_to_gestionCategoriasFragment)
        }

        // Configurar el botón para volver a la pantalla anterior
        binding.btnVolver.setOnClickListener {
            findNavController().popBackStack() // Vuelve a la pantalla anterior
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
