package ma.emsi.tp_sqlite.bean;

public class Machine {

    private int id;
    private String marque;
    private String refernce;
    private Salle salle;

    public Machine(String marque, String refernce, Salle salle) {
        this.marque = marque;
        this.refernce = refernce;
        this.salle = salle;
    }


    public Machine() {
    }

    public Machine(int id, String marque, String refernce, Salle salle) {
        this.id = id;
        this.marque = marque;
        this.refernce = refernce;
        this.salle = salle;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMarque() {
        return marque;
    }
    public void setMarque(String marque) {
        this.marque = marque;
    }
    public Salle getSalle() {
        return salle;
    }
    public void setSalle(Salle salle) {
        this.salle = salle;
    }
    public String getRefernce() {
        return refernce;
    }
    public void setRefernce(String refernce) {
        this.refernce = refernce;
    }
    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", marque='" + marque + '\'' +
                ", refernce='" + refernce + '\'' +
                ", salle=" + salle +
                '}';
    }
}
