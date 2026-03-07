package frgp.utn.edu.tpgrupo27.entidades;

public class Habito {

    private int idHabito;
    private String nombreHabito;
    private String descripcionHabito;
    private long fechaInicio;
    private int frecuencia;


    public Habito() {
    }


    public Habito(String nombreHabito, String descripcionHabito,
                  long fechaInicio ,
                  int frecuencia) {
        this.nombreHabito = nombreHabito;
        this.descripcionHabito = descripcionHabito;
        this.fechaInicio = fechaInicio;

        this.frecuencia = frecuencia;

    }


    public Habito(int idHabito, String nombreHabito, String descripcionHabito,
                  long fechaInicio,
                  int frecuencia) {
        this.idHabito = idHabito;
        this.nombreHabito = nombreHabito;
        this.descripcionHabito = descripcionHabito;
        this.fechaInicio = fechaInicio;

        this.frecuencia = frecuencia;

    }



    public int getIdHabito() {
        return idHabito;
    }

    public void setIdHabito(int idHabito) {
        this.idHabito = idHabito;
    }

    public String getNombreHabito() {
        return nombreHabito;
    }

    public void setNombreHabito(String nombreHabito) {
        this.nombreHabito = nombreHabito;
    }

    public String getDescripcionHabito() {
        return descripcionHabito;
    }

    public void setDescripcionHabito(String descripcionHabito) {
        this.descripcionHabito = descripcionHabito;
    }

    public long getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }




    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }


}