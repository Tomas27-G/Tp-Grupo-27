package frgp.utn.edu.tpgrupo27.negocio;

import android.content.Context;
import frgp.utn.edu.tpgrupo27.database.DAO.DaoUsuario;
import frgp.utn.edu.tpgrupo27.entidades.Usuario;

public class negocioUsuario {
    private DaoUsuario daoUsuario;
    public negocioUsuario(Context context){
        daoUsuario = new DaoUsuario(context);
    }


    public boolean crearUsuario(Usuario usuario) {
        return daoUsuario.altaUsuario(usuario);
    }

    public Usuario loguearseUsuario(String mail, String contrasena){

        return daoUsuario.loginUsuario(mail, contrasena);
    }

    public boolean verificarEmail(String mail){
        return daoUsuario.verificarMail(mail);
    }
}
