package fxml;

import domains.validators.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import repository.RepositoryException;
import services.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CheckNotaGUI {
    private Service service;

    public void setService(Service service){
        this.service = service;
    }

    @FXML
    private TextField studentId;
    @FXML
    private TextField temaId;
    @FXML
    private TextField data;
    @FXML
    private TextField nota;
    @FXML
    private TextField prof;
    @FXML
    private TextField motivari;
    @FXML
    private TextField fb;
    @FXML
    private Button addBtn;
    @FXML
    private Button closeBtn;

    public void setTextField(String sid, String tid, LocalDate d, Float n, String p, Integer m, String f){
        studentId.setText(sid);
        temaId.setText(tid);
        data.setText(d.toString());
        nota.setText(n.toString());
        prof.setText(p);
        motivari.setText(m.toString());
        fb.setText(f);
    }

    public void closeWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void saveNota(MouseEvent mouseEvent) throws InstantiationException, IllegalAccessException {
        String sid = studentId.getText();
        String tid = temaId.getText();
        LocalDate d = LocalDate.parse(data.getText());
        Float n = Float.parseFloat(nota.getText());
        String p = prof.getText();
        Integer m = Integer.parseInt(motivari.getText());
        String f = fb.getText();


        try {
            service.saveNotaG(sid, tid, d, p, n, m, f);

            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
        }  catch (Exception e){
            if (n >= 0)
                service.saveNotaG(sid, tid, d, p, 1, m, f);
        }
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
