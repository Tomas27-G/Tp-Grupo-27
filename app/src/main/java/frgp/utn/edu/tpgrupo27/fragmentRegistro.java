package frgp.utn.edu.tpgrupo27;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import frgp.utn.edu.tpgrupo27.database.DAO.DaoUsuario;
import frgp.utn.edu.tpgrupo27.entidades.Usuario;
import utils.session;

public class fragmentRegistro extends Fragment {

    private TextInputEditText etNombre, etApellido, etEdad, etEmail, etPassword;
    private MaterialButton btnRegister;

    public fragmentRegistro(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registro, container, false);

        etNombre = view.findViewById(R.id.etNombre);
        etApellido = view.findViewById(R.id.etApellido);
        etEdad = view.findViewById(R.id.etEdad);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnRegister = view.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> registrarUsuario());

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvLogin = view.findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new fragmentLogin())
                        .addToBackStack(null)
                        .commit()
        );
    }

    private void registrarUsuario(){

        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String edad = etEdad.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if(nombre.isEmpty() || apellido.isEmpty()
                || email.isEmpty() || pass.isEmpty()){

            Toast.makeText(requireContext(),
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(email);
        usuario.setContrasena(pass);
        usuario.setFechaNacimiento(edad); // usás edad como fecha

        DaoUsuario dao = new DaoUsuario(requireContext());

        boolean resultado = dao.altaUsuario(usuario);

        if(resultado){

            Toast.makeText(requireContext(),
                    "Usuario registrado correctamente",
                    Toast.LENGTH_SHORT).show();

            // ⭐ OPCIONAL: loguear automáticamente
            session session = new session(requireContext());
            session.crearSesion(usuario.getIdUsuario());

            requireActivity().recreate();

        }else{
            Toast.makeText(requireContext(),
                    "Error al registrar usuario",
                    Toast.LENGTH_SHORT).show();
        }
    }
}