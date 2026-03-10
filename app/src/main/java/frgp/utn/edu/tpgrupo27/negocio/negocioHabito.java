package frgp.utn.edu.tpgrupo27.negocio;

import android.content.Context;

import java.util.List;

import frgp.utn.edu.tpgrupo27.database.DAO.DaoHabito;
import frgp.utn.edu.tpgrupo27.entidades.Habito;

public class negocioHabito {

    private DaoHabito daoHabito;

    public negocioHabito(Context context, int idUsuario){
        daoHabito = new DaoHabito(context, idUsuario);
    }

    public boolean crearHabito(Habito habito){
        return daoHabito.altaHabito(habito);
    }

    public List<Habito> obtenerHabitos(){
        return daoHabito.listaDeHabitos();
    }

    public boolean borrarHabito(Habito habito){
        return daoHabito.bajaHabito(habito);
    }

    public boolean modificarHabito(String nombreOriginal, Habito habitoModificado) {
        return daoHabito.modificarHabito(nombreOriginal, habitoModificado);
    }
    public boolean actualizarCheckeado(int idHabito, boolean estado){
        return daoHabito.actualizarCheckeado(idHabito, estado);
    }

}