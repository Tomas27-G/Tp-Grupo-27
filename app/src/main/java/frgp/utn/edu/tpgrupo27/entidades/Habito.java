package frgp.utn.edu.tpgrupo27.entidades;

public class Habito {

    private int idHabito;
    private String nombreHabito;
    private String descripcionHabito;
    private long fechaInicio;
    private int frecuencia;
    private boolean checkeado;
    private int idUsuario;

    public Habito() {

    }


    public Habito(String nombreHabito, String descripcionHabito,
                  long fechaInicio ,
                  int frecuencia,boolean checkeado,int idUsuario) {
        this.nombreHabito = nombreHabito;
        this.descripcionHabito = descripcionHabito;
        this.fechaInicio = fechaInicio;

        this.frecuencia = frecuencia;

        this.checkeado=checkeado;

        this.idUsuario=idUsuario;

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

    public boolean getCheckeado() { return checkeado;}

    public void setCheckeado(boolean checkeado){ this.checkeado=checkeado;}

    public int getIdUsuario(){return idUsuario;}
    public void setIdUsuario(int idUsuario){this.idUsuario=idUsuario;}


}