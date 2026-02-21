package frgp.utn.edu.tpgrupo27.entidades;

public class Habito {

    private int idHabito;
    private String nombreHabito;
    private String descripcionHabito;
    private long fechaInicio;
    private long fechaFinal;
    private int frecuencia;
    private String hora;


    public Habito() {
    }


    public Habito(String nombreHabito, String descripcionHabito,
                  long fechaInicio, long fechaFinal,
                  int frecuencia, String hora) {
        this.nombreHabito = nombreHabito;
        this.descripcionHabito = descripcionHabito;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.frecuencia = frecuencia;
        this.hora = hora;
    }


    public Habito(int idHabito, String nombreHabito, String descripcionHabito,
                  long fechaInicio, long fechaFinal,
                  int frecuencia, String hora) {
        this.idHabito = idHabito;
        this.nombreHabito = nombreHabito;
        this.descripcionHabito = descripcionHabito;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.frecuencia = frecuencia;
        this.hora = hora;
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

    public long getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(long fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}