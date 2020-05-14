import domains.validators.NotaValidator;
import domains.validators.StudentValidator;
import domains.validators.TemaValidator;
import fxml.GUI;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import repository.XMLRepository.XMLNotaRepository;
import repository.XMLRepository.XMLStudentRepository;
import repository.XMLRepository.XMLTemaRepository;
import services.Service;
import utils.Paths;

public class ApplicationMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        XMLStudentRepository studentRepo = new XMLStudentRepository(new StudentValidator(), Paths.STUDENT);
        XMLTemaRepository temaRepo = new XMLTemaRepository(new TemaValidator(), Paths.TEMA);
        XMLNotaRepository notaRepo = new XMLNotaRepository(new NotaValidator(), Paths.NOTA);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/mainView.fxml"));
        AnchorPane root = loader.load();

        GUI gui = loader.getController();
        gui.setService(new Service(studentRepo, temaRepo, notaRepo));

        primaryStage.setScene(new Scene(root, 1200, 480));
        primaryStage.setTitle("STUDENT");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image("images/student.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
