package domains;

public class FiltrareDTO {
    private String numeStudent;
    private String prenumeStudent;
    private String profesor;
    private String descriereTema;
    private int nrSaptamana;

    public FiltrareDTO(String numeStudent, String prenumeStudent, String profesor, String descriereTema, int nrSaptamana) {
        this.numeStudent = numeStudent;
        this.prenumeStudent = prenumeStudent;
        this.profesor = profesor;
        this.descriereTema = descriereTema;
        this.nrSaptamana = nrSaptamana;
    }

    public String getNumeStudent() {
        return numeStudent;
    }

    public String getPrenumeStudent() {
        return prenumeStudent;
    }

    public String getProfesor() {
        return profesor;
    }

    public String getDescriereTema() {
        return descriereTema;
    }

    public int getNrSaptamana() {
        return nrSaptamana;
    }
}
