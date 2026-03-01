package frgp.utn.edu.tpgrupo27;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import frgp.utn.edu.tpgrupo27.database.DAO.DaoUsuario;
import frgp.utn.edu.tpgrupo27.entidades.Usuario;
import utils.session;

public class fragmentLogin extends Fragment {

    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin;

    public fragmentLogin() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> loginUsuario());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvRegistro = view.findViewById(R.id.tvRegistro);

        tvRegistro.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new fragmentRegistro())
                        .addToBackStack(null)
                        .commit()
        );
    }

    private void loginUsuario(){

        String mail = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        DaoUsuario dao = new DaoUsuario(requireContext());
        Usuario usuario = dao.loginUsuario(mail, pass);

        if(usuario != null){

            // ⭐ GUARDAR SESIÓN
            session session = new session(requireContext());
            session.crearSesion(usuario.getIdUsuario());

            Toast.makeText(requireContext(),
                    "Bienvenido " + usuario.getNombre(),
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(requireContext(), MainActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(requireContext(),
                    "Usuario o contraseña incorrectos",
                    Toast.LENGTH_SHORT).show();
        }
    }
}