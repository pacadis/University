package domains;

import java.time.LocalDate;

public class NotaDTO {
    private String numeStudent;
    private String idTema;
    private float valoare;
    private LocalDate data;
    private int deadlineWeek;
    private String feedback;
    private int nrSaptamana;

    public NotaDTO(String numeStudent, String idTema, float valoare, LocalDate data, int deadlineWeek, String feedback) {
        this.numeStudent = numeStudent;
        this.idTema = idTema;
        this.valoare = valoare;
        this.data = data;
        this.deadlineWeek = deadlineWeek;
        this.feedback = feedback;
    }

    public String getNumeStudent() {
        return numeStudent;
    }

    public void setNumeStudent(String numeStudent) {
        this.numeStudent = numeStudent;
    }

    public String getIdTema() {
        return idTema;
    }

    public void setIdTema(String idTema) {
        this.idTema = idTema;
    }

    public float getValoare() {
        return valoare;
    }

    public void setValoare(float valoare) {
        this.valoare = valoare;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getDeadlineWeek() {
        return deadlineWeek;
    }

    public void setDeadlineWeek(int deadlineWeek) {
        this.deadlineWeek = deadlineWeek;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getNrSaptamana() { return nrSaptamana; }

    public void setNrSaptamana(int nrSaptamana) { this.nrSaptamana = nrSaptamana; }
}
