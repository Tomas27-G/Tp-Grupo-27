package frgp.utn.edu.tpgrupo27;

import android.app.DatePickerDialog;
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

import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import frgp.utn.edu.tpgrupo27.entidades.Usuario;
import frgp.utn.edu.tpgrupo27.negocio.negocioUsuario;
import utils.session;

public class fragmentRegistro extends Fragment {

    private TextInputEditText etNombre, etApellido, etEdad, etEmail, etPassword;
    private MaterialButton btnRegister;

    public fragmentRegistro(){}

    private void mostrarCalendario() {
        Calendar c = Calendar.getInstance();
        int anioA = c.get(Calendar.YEAR);
        int mesA = c.get(Calendar.MONTH);
        int diaA = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {

            int edadCalculada = anioA - year;
            if (mesA < month || (mesA == month && diaA < dayOfMonth)) {
                edadCalculada--;

            }
            if(edadCalculada > 0 && edadCalculada < 100){
                String fechaS = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, (month + 1), year);
                etEdad.setError(null);
                etEdad.setText(fechaS + " (" + edadCalculada + " años)");
            } else if (edadCalculada <= 0) {
                etEdad.setText("");
                etEdad.setError("Fecha invalida");
                Toast.makeText(getContext(), "La fecha seleccionada no es válida", Toast.LENGTH_SHORT).show();
            }

        }, anioA, mesA, diaA);

        dpd.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registro, container, false);

        etNombre = view.findViewById(R.id.etNombre);
        etApellido = view.findViewById(R.id.etApellido);
        etEdad = view.findViewById(R.id.editEdad);



        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnRegister = view.findViewById(R.id.btnRegister);

        etEdad.setFocusable(false);
        etEdad.setClickable(true);


        etEdad.setOnClickListener(v -> mostrarCalendario());

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
    private boolean contieneNumeros(String texto){
        return texto.matches(".*\\d.*");
    }
    private void registrarUsuario(){

        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String textoEdad = etEdad.getText().toString().split(" ")[0];
        long edad = convertirFecha(textoEdad);

        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if(contieneNumeros(nombre) || contieneNumeros(apellido)){
            Toast.makeText(getContext(),
                    "El nombre y el apellido no pueden tener números",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(nombre.isEmpty() || apellido.isEmpty()
                || email.isEmpty() || pass.isEmpty()){

            Toast.makeText(requireContext(),
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Ingrese un correo válido");
            return;
        }else{
            etEmail.setError(null);
        }

        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$";

        if (!pass.matches(passwordPattern)) {
            etPassword.setError("Contraseña débil: Debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y simbolo.");
            return;
        } else {
            etPassword.setError(null);
        }



        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(email);
        usuario.setContrasena(pass);
        usuario.setFechaNacimiento(String.valueOf(edad));

        negocioUsuario negocio = new negocioUsuario(requireContext());

        if(negocio.verificarEmail(email)){
            etEmail.setError("El email ya está registrado");
            return;
        }

        boolean resultado = negocio.crearUsuario(usuario);

        if(resultado){

            Toast.makeText(requireContext(),
                    "Usuario registrado correctamente",
                    Toast.LENGTH_SHORT).show();

            session session = new session(requireContext());
            session.crearSesion(usuario.getIdUsuario(), usuario.getNombre());

            requireActivity().recreate();

        }else{
            Toast.makeText(requireContext(),
                    "Error al registrar usuario",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private long convertirFecha(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date date = formato.parse(fecha);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}