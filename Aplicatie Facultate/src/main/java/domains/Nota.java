package domains;


import com.sun.tools.javac.util.Pair;

import java.time.LocalDate;

public class Nota extends Entity<Pair<String, String>> {
    private LocalDate data;
    private String profesor;
    private float valoare;


    public Nota(Pair<String, String>id, LocalDate data, String profesor, float valoare) {
        super(id);
        this.data = data;
        this.profesor = profesor;
        this.valoare = valoare;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public float getValoare() {
        return valoare;
    }

    public void setValoare(float valoare) {
        this.valoare = valoare;
    }

    public String getStudent(){
        return getId().fst;
    }

    public String getTema(){
        return getId().snd;
    }

    @Override
    public String toString() {
        return getId().fst + "; " + getId().snd + "; " + getData().getDayOfMonth() + "/" + getData().getMonthValue() + "/" + getData().getYear()
                + "; " + getProfesor() + "; " + getValoare();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Nota nota = (Nota) obj;
        if (!this.data.equals(nota.getData())) return false;
        if (this.valoare != nota.getValoare()) return false;
        return this.profesor.equals(nota.getProfesor());
    }
}
