package repository.XMLRepository;

import com.sun.tools.javac.util.Pair;
import domains.Nota;
import domains.NotaDTO;
import domains.validators.NotaValidator;
import domains.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Paths;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static utils.Constants.DATE_FORMAT;

public class XMLNotaRepository extends XMLRepository<Nota, Pair<String, String>> {
    private static XMLNotaRepository single_instance = null;

    public XMLNotaRepository(Validator<Nota> validator, String fileName) {
        super(validator, fileName);
    }

    public static XMLNotaRepository getInstance() {
        if (single_instance == null)
            single_instance = new XMLNotaRepository(new NotaValidator(), Paths.NOTA);
        return single_instance;
    }

    @Override
    public Nota createEntityFromElement(Element entityElement) {
        String idStudent = entityElement.getAttribute("idStudent");
        String idTema = entityElement.getAttribute("idTema");
        String data = entityElement.getElementsByTagName("data")
                .item(0)
                .getTextContent();
        String profesor = entityElement.getElementsByTagName("profesor")
                .item(0)
                .getTextContent();
        String valoare = entityElement.getElementsByTagName("valoare")
                .item(0)
                .getTextContent();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate dataIntrodusa = LocalDate.from(formatter.parse(data));
        Nota student = new Nota(Pair.of(idStudent, idTema), dataIntrodusa, profesor, Float.parseFloat(valoare));
//        student.setId(Pair.of(idStudent, idTema));
        return student;
    }

    @Override
    public Element createElementFromEntity(Document document, Nota entity) {
        Element element = document.createElement("nota");
        element.setAttribute("idStudent", entity.getId().fst);
        element.setAttribute("idTema", entity.getId().snd);
        Element data = document.createElement("data");
        data.setTextContent(entity.getData().getDayOfMonth() + "/" + entity.getData().getMonthValue() + "/" + entity.getData().getYear());
        element.appendChild(data);
        Element profesor = document.createElement("profesor");
        profesor.setTextContent(entity.getProfesor());
        element.appendChild(profesor);
        Element valoare = document.createElement("valoare");
        valoare.setTextContent(((Float) entity.getValoare()).toString());
        element.appendChild(valoare);
        return element;
    }

    @Override
    public Nota update(Nota entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity " + Nota.class.getName() + " NULL!");
        validator.validate(entity);
        if (entities.get(entity.getId()).equals(entity)) {
            return entity;
        }
        Nota oldValue = entities.get(entity.getId());
        oldValue.setValoare(entity.getValoare());
        oldValue.setData(entity.getData());
        saveAllToFile();
        return null;
    }

    public void saveToTextFile(NotaDTO entity) {
        String file = "./data/catalog/" + entity.getNumeStudent() + ".txt";
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file, true))) {
            out.write("Tema: " + entity.getIdTema());
            out.newLine();
            out.write("Nota: " + entity.getValoare());
            out.newLine();
            out.write("Predata in saptamna: " + entity.getNrSaptamana());
            out.newLine();
            out.write("Deadline: " + entity.getDeadlineWeek());
            out.newLine();
            out.write("Feedback: " + entity.getFeedback());
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
