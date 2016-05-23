/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package karen16;

/**
 *
 * @author David Discua
 */
public class Filetest {
    
    String Nombre;
    String Ocupado;
    String Fecha;
    String Padre;
    String Tipo;
    String Content;

    public Filetest(String Nombre, String Ocupado, String Fecha, String Padre, String Tipo, String Content) {
        this.Nombre = Nombre;
        this.Ocupado = Ocupado;
        this.Fecha = Fecha;
        this.Padre = Padre;
        this.Tipo = Tipo;
        this.Content = Content;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getOcupado() {
        return Ocupado;
    }

    public void setOcupado(String Ocupado) {
        this.Ocupado = Ocupado;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getPadre() {
        return Padre;
    }

    public void setPadre(String Padre) {
        this.Padre = Padre;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }
    
    
    
    
}
