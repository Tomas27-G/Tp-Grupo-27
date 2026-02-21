package frgp.utn.edu.tpgrupo27.entidades;

public class Tarea {
  private int idTarea;
  private String nombreTarea;
  private String descripcionTarea;

  private long fechaInicio;
  private long fechaFinal;

  private int prioridad;

  public Tarea () {

  }

  public Tarea (String nombreTarea, String descripcionTarea, long fechaInicio,
                long fechaFinal, int prioridad) {
    this.nombreTarea = nombreTarea;
    this.descripcionTarea = descripcionTarea;
    this.fechaInicio = fechaInicio;
    this.fechaFinal = fechaFinal;
    this.prioridad = prioridad;
  }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
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

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
}
