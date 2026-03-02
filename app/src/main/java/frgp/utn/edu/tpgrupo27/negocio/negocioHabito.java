package frgp.utn.edu.tpgrupo27.negocio;

import android.content.Context;

import frgp.utn.edu.tpgrupo27.database.DAO.DaoHabito;
import frgp.utn.edu.tpgrupo27.entidades.Habito;

public class negocioHabito {
    private DaoHabito daoHabito;

    public negocioHabito(Context context){
        daoHabito = new DaoHabito(context);
    }

    public boolean crearHabito(Habito habito){

        return daoHabito.altaHabito(habito);
    }

    public java.util.List<Habito> obtenerHabitos(){

        return daoHabito.listaDeHabitos();
    }

}
