package fxml;

import domains.*;
import domains.validators.ValidationException;
import events.ChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.stage.Popup;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import observer.Observer;
import services.Service;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GUI implements Observer<ChangeEvent> {
    private ObservableList<Student> studentData = FXCollections.observableArrayList();
    private ObservableList<Tema> temaData = FXCollections.observableArrayList();
    private ObservableList<Nota> notaData = FXCollections.observableArrayList();
    private ObservableList<StudentMedie> studentiMedie = FXCollections.observableArrayList();
    private ObservableList<StudentMedie> studentiExamen =  FXCollections.observableArrayList();
    private ObservableList<Student> studentiSilitori =  FXCollections.observableArrayList();
    private ObservableList<TemaMedie> temeMedie =  FXCollections.observableArrayList();

    private Service service;

    // ---- TEXT FIELDS ----
    @FXML
    private TextField idTF;
    @FXML
    private TextField numeTF;
    @FXML
    private TextField prenumeTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField profTF;
    @FXML
    private TextField grupaTF;

    @FXML
    private TextField cautaTF;
    @FXML
    private Button doITBT;

    @FXML
    private Button undoITBT;

    @FXML
    private Button changeITBT;

    @FXML
    MenuBar menuBar;
    // ---- TABEL ----
    @FXML
    private TableView<Student> tableView;

    @FXML
    private TableColumn<Student, String> columnID;
    @FXML
    private TableColumn<Student, String> columnNume;
    @FXML
    private TableColumn<Student, String> columnPrenume;
    @FXML
    private TableColumn<Student, String> columnEmail;
    @FXML
    private TableColumn<Student, String> columnProfesor;
    @FXML
    private TableColumn<Student, Integer> columnGrupa;
    @FXML
    private TableColumn<Student, Integer> columnNrM;


//    ---- HBOXEX ----
    @FXML
    private HBox studentHBox;
    @FXML
    private VBox mainVBox;
    @FXML
    private HBox temaHBox;
    @FXML
    private HBox notaHBox;
    @FXML
    private HBox rapoarteHBox;
    @FXML
    private Button exitMainMenu;
//    // ---- MENU BAR ----
//    @FXML
//    private MenuButton addStudent;
//    @FXML
//    private MenuButton stergeStudent;
//    @FXML
//    private MenuButton modificaStudent;
//


    @FXML
    private TextArea feedbackTF;

    void initialize(){
        initializeColumns(studentData);
        initializeTemaColumns(temaData);
        initializeNotaColumns(studentData);
        initializeNoteColumns(notaData);
        initializeRaportTables();
        service.addObserver(this);
        feedbackTF.setText("Laborator predat cu succes");
    }

    void initializeColumns(ObservableList<Student> list){
        columnID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        columnNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        columnPrenume.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnProfesor.setCellValueFactory(new PropertyValueFactory<>("cadruDidacticLab"));
        columnGrupa.setCellValueFactory(new PropertyValueFactory<>("grupa"));
        tableView.setItems(list);
    }

    @FXML
    private TableColumn<Tema, String> columnTemaID;
    @FXML
    private TableColumn<Tema, String> columnTemaDescriere;
    @FXML
    private TableColumn<Tema, Integer> columnTemaStartWeek;
    @FXML
    private TableColumn<Tema, Integer> columnTemaDeadlineWeek;
    @FXML
    private TableView<Tema> tableViewTema;


    void initializeTemaColumns(ObservableList<Tema> list){
        columnTemaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnTemaDescriere.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        columnTemaStartWeek.setCellValueFactory(new PropertyValueFactory<>("startWeek"));
        columnTemaDeadlineWeek.setCellValueFactory(new PropertyValueFactory<>("deadlineWeek"));
        tableViewTema.setItems(list);
    }

    @FXML
    private TableColumn<Nota, String> numeNota;
    @FXML
    private TableColumn<Nota, String> temaNota;
    @FXML
    private TableColumn<Nota, Float> nota;
    @FXML
    private TableColumn<Nota, String> profNota;
    @FXML
    private TableView<Nota> noteTableView;

    void initializeNoteColumns(ObservableList<Nota> list){
        numeNota.setCellValueFactory(new PropertyValueFactory<>("student"));
        temaNota.setCellValueFactory(new PropertyValueFactory<>("tema"));
        nota.setCellValueFactory(new PropertyValueFactory<>("valoare"));
        profNota.setCellValueFactory(new PropertyValueFactory<>("profesor"));
        noteTableView.setItems(list);
    }

    @FXML
    private TableColumn<Student, String> IDStudentNota;
    @FXML
    private TableColumn<Student, String> NumeStudentNota;
    @FXML
    private TableColumn<Student, String> PrenumeStudentNota;
    @FXML
    private TableColumn<Student, String> GrupaStudentNota;
    @FXML
    private TableView<Student> studentNotaTV;

    void initializeNotaColumns(ObservableList<Student> list){
        IDStudentNota.setCellValueFactory(new PropertyValueFactory<>("Id"));
        NumeStudentNota.setCellValueFactory(new PropertyValueFactory<>("nume"));
        PrenumeStudentNota.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        GrupaStudentNota.setCellValueFactory(new PropertyValueFactory<>("grupa"));
        studentNotaTV.setItems(list);
    }

    void load(){
        studentData.setAll(service.getAllStudent());
        temaData.setAll(service.getAllTema());
        notaData.setAll(service.getAllNota());
    }

    @FXML
    void saveStudent(){
        try {
            String id = idTF.getText();
            String nume = numeTF.getText();
            String prenume = prenumeTF.getText();
            String email = emailTF.getText();
            String profesor = profTF.getText();
            Integer grupa = Integer.parseInt(grupaTF.getText());
            service.saveStudent(id, nume, prenume, email, profesor, grupa);
            idTF.clear();
            idTF.setVisible(false);
            numeTF.setVisible(false);
            numeTF.clear();;
            prenumeTF.setVisible(false);
            prenumeTF.clear();
            emailTF.setVisible(false);
            emailTF.clear();
            profTF.setVisible(false);
            profTF.clear();
            grupaTF.setVisible(false);
            grupaTF.clear();
            doITBT.setVisible(false);
            load();
        } catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Argument");
            alert.setHeaderText("");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void adaugaStudent() throws Exception {
        idTF.setVisible(true);
        numeTF.setVisible(true);
        prenumeTF.setVisible(true);
        emailTF.setVisible(true);
        profTF.setVisible(true);
        grupaTF.setVisible(true);
        doITBT.setVisible(true);
    }

    private List<Tema> getTeme() {
        return service.getAllTema()
                .stream()
                .map(n -> new Tema(n.getDescriere(), n.getStartWeek(), n.getDeadlineWeek()))
                .collect(Collectors.toList());
    }
    @FXML
    private ComboBox temeComboBox;

    public void setService(Service serv){
        service = serv;
        service.addObserver(this);
        studentData.setAll(service.getAllStudent());
        temaData.setAll(service.getAllTema());
        notaData.setAll(service.getAllNota());
        temeComboBox.getItems().setAll(getTeme().stream().map(Tema::getDescriere).collect(Collectors.toSet()));
        temeComboBox.getSelectionModel().selectLast();
        studentiMedie.setAll(service.getAllStudentiMedie());
        studentiExamen.setAll(service.getAllStudentiExamen());
        studentiSilitori.setAll(service.getAllStudentiSilitori());
        temeMedie.setAll(service.getAllTemeMedie());
        initialize();
    }

    public void deleteStudent(MouseEvent mouseEvent) {
        try{
            service.deleteStudent(idTF.getText());
            undoITBT.setVisible(false);
            load();
        }catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Argument");
            alert.setHeaderText("");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    public void stergeStudent(ActionEvent event) {
        undoITBT.setVisible(true);
    }

    public void selectingItem(MouseEvent mouseEvent) {
        idTF.setText(tableView.getSelectionModel().getSelectedItem().getId());
        numeTF.setText(tableView.getSelectionModel().getSelectedItem().getNume());
        prenumeTF.setText(tableView.getSelectionModel().getSelectedItem().getPrenume());
        emailTF.setText(tableView.getSelectionModel().getSelectedItem().getEmail());
        profTF.setText(tableView.getSelectionModel().getSelectedItem().getCadruDidacticLab());
        grupaTF.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getGrupa()));
    }

    public void modificaStudent(ActionEvent event) throws Exception {
        idTF.setVisible(true);
        idTF.setEditable(false);
        numeTF.setVisible(true);
        prenumeTF.setVisible(true);
        emailTF.setVisible(true);
        profTF.setVisible(true);
        grupaTF.setVisible(true);
        changeITBT.setVisible(true);
    }

    public void updateStudent(MouseEvent mouseEvent) {
        try {
            String id = idTF.getText();
            String nume = numeTF.getText();
            String prenume = prenumeTF.getText();
            String email = emailTF.getText();
            String profesor = profTF.getText();
            Integer grupa = Integer.parseInt(grupaTF.getText());
            service.updateStudent(id, nume, prenume, email, profesor, grupa);
            idTF.setVisible(false);
            idTF.setEditable(true);
            idTF.clear();
            numeTF.setVisible(false);
            numeTF.clear();;
            prenumeTF.setVisible(false);
            prenumeTF.clear();
            emailTF.setVisible(false);
            emailTF.clear();
            profTF.setVisible(false);
            profTF.clear();
            grupaTF.setVisible(false);
            grupaTF.clear();
            changeITBT.setVisible(false);
            load();
        }catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Argument");
            alert.setHeaderText("");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    public void cautaStudent(ActionEvent event) {
        cautaTF.setVisible(true);
    }

    private List<Student> getStudentList() {
        return service.getAllStudent()
                .stream()
                .collect(Collectors.toList());
    }

    public void searchStudent(KeyEvent keyEvent) {
        Predicate<Student> filtarerNume = n -> n.getNume().startsWith(cautaTF.getText());

        studentData.setAll(getStudentList().stream().
                filter(filtarerNume)
                .collect(Collectors.toList()));
    }

    public void setInvisible(MouseEvent mouseEvent) {
        cautaTF.setVisible(false);
    }

    public void exitShow(ActionEvent event) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }

    public void minimizeWindow(ActionEvent event) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.toBack();
    }

    public void fullscreenWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.setFullScreen(true);
    }

    public void openStudent(MouseEvent mouseEvent) {
        mainVBox.setVisible(false);
        studentHBox.setVisible(true);
        temaHBox.setVisible(false);
        notaHBox.setVisible(false);
        rapoarteHBox.setVisible(false);
    }

    public void goBackButton(MouseEvent mouseEvent) {
        mainVBox.setVisible(true);
        studentHBox.setVisible(false);
        temaHBox.setVisible(false);
        notaHBox.setVisible(false);
        rapoarteHBox.setVisible(false);
    }

    public void openTema(MouseEvent mouseEvent) {
        mainVBox.setVisible(false);
        studentHBox.setVisible(false);
        temaHBox.setVisible(true);
        notaHBox.setVisible(false);
        rapoarteHBox.setVisible(false);
    }

    public void openNota(MouseEvent mouseEvent) {
        mainVBox.setVisible(false);
        studentHBox.setVisible(false);
        temaHBox.setVisible(false);
        notaHBox.setVisible(true);
        rapoarteHBox.setVisible(false);
    }

    public void exitMainMenu(MouseEvent mouseEvent) {
        Stage stage = (Stage) exitMainMenu.getScene().getWindow();
        stage.close();
    }

    public void saveTema(MouseEvent mouseEvent) {
    }

    public void deleteTema(MouseEvent mouseEvent) {
    }

    public void updateTema(MouseEvent mouseEvent) {
    }

    private String cautaIDTema(String desc){
        for (Tema t : service.getAllTema()) {
            if (t.getDescriere() == desc)
                return t.getId();
        }
        return null;
    }

    @FXML
    private TextField dataTF;
    @FXML
    private TextField notaTF;
    @FXML
    private TextField profesorTF;
    @FXML
    private TextField motivariTF;

    public void adaugaNota(MouseEvent mouseEvent) throws InstantiationException, IllegalAccessException, IOException {
        try {
            String studentID = studentNotaTV.getSelectionModel().getSelectedItem().getId();
            String temaDesc = cautaIDTema(temeComboBox.getSelectionModel().getSelectedItem().toString());
            LocalDate data = LocalDate.parse(dataTF.getText());
            Float nota = Float.parseFloat(notaTF.getText());
            String prof = profesorTF.getText();
            Integer motivari = Integer.parseInt(motivariTF.getText());
            String fb = feedbackTF.getText();

            if (nota < 1 || nota > 10) {
                EntityAlert.showErrorMessage(null, "Nota trebuie sa fie intre 1 si 10!");
                return;
            }
            if (studentID == null) {
                EntityAlert.showErrorMessage(null, "Nu a fost selectata niciun student!");
                return;
            }
            if (temaDesc == null) {
                EntityAlert.showErrorMessage(null, "Nu a fost selectata nicio tema!");
                return;
            }


            //------open check window

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/checkNotaGui.fxml"));
            AnchorPane root = loader.load();

            CheckNotaGUI cng = loader.getController();
            cng.setService(this.service);
            cng.setTextField(studentID, temaDesc, data, nota, prof, motivari, fb);

            Stage checkStage = new Stage();
            checkStage.setScene(new Scene(root, 250, 400));
            checkStage.setTitle("CHECK NOTA");
            checkStage.show();
        } catch (DateTimeParseException e){
            EntityAlert.showErrorMessage(null, "Data introdusa gresit!");
        } catch (ValidationException e){
            EntityAlert.showErrorMessage(null, "Introduceti datele cu grija!");
        } catch (NumberFormatException e){
            EntityAlert.showErrorMessage(null, "Introduceti cu grija datele!");
        }

        load();
    }

    @Override
    public void update(ChangeEvent changeEvent) {
        load();
    }

    @FXML
    private TextField cautaStudentNota;

    public void searchStudentNota(KeyEvent keyEvent) {
        Predicate<Student> filtarerNume = n -> n.getNume().startsWith(cautaStudentNota.getText());

        studentData.setAll(getStudentList().stream().
                filter(filtarerNume)
                .collect(Collectors.toList()));

    }

    public void openRapoarte(MouseEvent mouseEvent) {
        mainVBox.setVisible(false);
        studentHBox.setVisible(false);
        temaHBox.setVisible(false);
        notaHBox.setVisible(false);
        rapoarteHBox.setVisible(true);
    }

    // rapoarte area
    @FXML
    private TableView studentiExamenTableView;
    @FXML
    private TableColumn<StudentMedie, String> idStudentiExamenTableColumn;
    @FXML
    private TableColumn<StudentMedie, String> numeStudentiExamenTableColumn;
    @FXML
    private TableColumn<StudentMedie, Integer> grupaStudentiExamenTableColumn;
    @FXML
    private TableColumn<StudentMedie, Float> medieStudentiExamenTableColumn;

    @FXML
    private TableView studentiMedieTableView;
    @FXML
    private TableColumn<StudentMedie, String> idStudentiMedieTableColumn;
    @FXML
    private TableColumn<StudentMedie, String> numeStudentiMedieTableColumn;
    @FXML
    private TableColumn<StudentMedie, Integer> grupaStudentiMedieTableColumn;
    @FXML
    private TableColumn<StudentMedie, Float> medieStudentiMedieTableColumn;

    @FXML
    private TableView studentiSilitoriTableView;
    @FXML
    private TableColumn<Student, String> idStudentiSilitoriTableColumn;
    @FXML
    private TableColumn<Student, String> numeStudentiSilitoriTableColumn;
    @FXML
    private TableColumn<Student, Integer> grupaStudentiSilitoriTableColumn;

    @FXML
    private TableView temeMedieTableView;
    @FXML
    private TableColumn<TemaMedie, String> idTemeMedieTableColumn;
    @FXML
    private TableColumn<TemaMedie, String> descriereTemeMedieTableColumn;
    @FXML
    private TableColumn<TemaMedie, Float> medieTemeMedieTableColumn;

    private void initializeRaportTables() {
        initializeStudentiMedie(studentiMedie);
        initializeStudentiExamen(studentiExamen);
        initializeStudentiSilitori(studentiSilitori);
        initializeTemeMedie(temeMedie);
    }

    private void initializeStudentiSilitori(ObservableList<Student> list) {
        idStudentiSilitoriTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        numeStudentiSilitoriTableColumn.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        numeStudentiSilitoriTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        grupaStudentiSilitoriTableColumn.setCellValueFactory(new PropertyValueFactory<>("Grupa"));
        grupaStudentiSilitoriTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        studentiSilitoriTableView.getColumns().set(0, idStudentiSilitoriTableColumn);
        studentiSilitoriTableView.getColumns().set(1, numeStudentiSilitoriTableColumn);
        studentiSilitoriTableView.getColumns().set(2, grupaStudentiSilitoriTableColumn);
        studentiSilitoriTableView.setItems(list);
    }

    private void initializeTemeMedie(ObservableList<TemaMedie> list) {
        idTemeMedieTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        descriereTemeMedieTableColumn.setCellValueFactory(new PropertyValueFactory<>("Descriere"));
        descriereTemeMedieTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        medieTemeMedieTableColumn.setCellValueFactory(new PropertyValueFactory<>("Medie"));
        medieTemeMedieTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        temeMedieTableView.getColumns().set(0, idTemeMedieTableColumn);
        temeMedieTableView.getColumns().set(1, descriereTemeMedieTableColumn);
        temeMedieTableView.getColumns().set(2, medieTemeMedieTableColumn);
        temeMedieTableView.setItems(list);
        temeMedieTableView.getSelectionModel().selectFirst();
    }

    private void initializeStudentiExamen(ObservableList<StudentMedie> list) {
        idStudentiExamenTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        numeStudentiExamenTableColumn.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        numeStudentiExamenTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        grupaStudentiExamenTableColumn.setCellValueFactory(new PropertyValueFactory<>("Grupa"));
        grupaStudentiExamenTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        medieStudentiExamenTableColumn.setCellValueFactory(new PropertyValueFactory<>("Medie"));
        medieStudentiExamenTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        studentiExamenTableView.getColumns().set(0, idStudentiExamenTableColumn);
        studentiExamenTableView.getColumns().set(1, numeStudentiExamenTableColumn);
        studentiExamenTableView.getColumns().set(2, grupaStudentiExamenTableColumn);
        studentiExamenTableView.getColumns().set(3, medieStudentiExamenTableColumn);
        studentiExamenTableView.setItems(list);
    }

    private void initializeStudentiMedie(ObservableList<StudentMedie> list) {
        idStudentiMedieTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        numeStudentiMedieTableColumn.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        numeStudentiMedieTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        grupaStudentiMedieTableColumn.setCellValueFactory(new PropertyValueFactory<>("Grupa"));
        grupaStudentiMedieTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        medieStudentiMedieTableColumn.setCellValueFactory(new PropertyValueFactory<>("Medie"));
        medieStudentiMedieTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        studentiMedieTableView.getColumns().set(0, idStudentiMedieTableColumn);
        studentiMedieTableView.getColumns().set(1, numeStudentiMedieTableColumn);
        studentiMedieTableView.getColumns().set(2, grupaStudentiMedieTableColumn);
        studentiMedieTableView.getColumns().set(3, medieStudentiMedieTableColumn);
        studentiMedieTableView.setItems(list);
    }

    // running nota pane
    
}
