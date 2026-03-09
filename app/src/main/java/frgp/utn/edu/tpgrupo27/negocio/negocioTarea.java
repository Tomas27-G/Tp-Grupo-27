package frgp.utn.edu.tpgrupo27.negocio;

import android.content.Context;

import java.util.List;

import frgp.utn.edu.tpgrupo27.database.DAO.DaoTarea;
import frgp.utn.edu.tpgrupo27.entidades.Tarea;

public class negocioTarea {

    private DaoTarea daoTarea;

    public negocioTarea(Context context){
        daoTarea = new DaoTarea(context);
    }

    public boolean crearTarea(Tarea tarea){
        return daoTarea.altaTarea(tarea);
    }

    public boolean buscarTarea(Tarea tarea){
        return daoTarea.buscarTarea(tarea);
    }

    public List<Tarea> listarTareas(){
        return daoTarea.listarTareas();
    }

    public boolean modificarTarea(Tarea tarea){
        return daoTarea.modificarTarea(tarea);
    }

    public boolean eliminarTarea(Tarea tarea){
        return daoTarea.bajaTarea(tarea);
    }

}