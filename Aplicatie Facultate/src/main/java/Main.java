import com.sun.tools.javac.util.Pair;
import domains.Nota;
import domains.StructuraAnUniversitar;
import domains.Student;
import domains.Tema;
import domains.validators.*;
import javafx.application.Application;
import repository.XMLRepository.XMLNotaRepository;
import repository.XMLRepository.XMLStudentRepository;
import repository.XMLRepository.XMLTemaRepository;
import repository.fileRepository.NotaFileRepository;
import repository.fileRepository.StudentFileRepository;
import repository.fileRepository.TemaFileRepository;
import repository.inMemoryRepository.CrudRepository;
import services.Service;
import userinterface.Consola;
import utils.Paths;

import java.time.LocalDate;

public class Main{
    public static void main(String[] args) throws ValidationException {

//        StructuraAnUniversitar structuraAnUniversitar = StructuraAnUniversitar.getInstance(Paths.AN_UNIVERSITAR);
//
//        Validator<Student> validatorStudent = new StudentValidator();
//        Validator<Tema> validatorTema = new TemaValidator();
//        Validator<Nota> validatorNota = new NotaValidator();
//
//        //CrudRepository<String, Student> studentRepository = new StudentRepository(validatorStudent);
//        //CrudRepository<String, Tema> temaRepository = new TemaRepository(validatorTema);
//
//        CrudRepository<String, Student> studentFileRepository = new XMLStudentRepository(validatorStudent, Paths.STUDENT);
//        CrudRepository<String, Tema> temaFileRepository = new XMLTemaRepository(validatorTema, Paths.TEMA);
//        CrudRepository<Pair<String, String>, Nota> notaFileRepository = new XMLNotaRepository(validatorNota, Paths.NOTA);
//        Service service = new Service(studentFileRepository, temaFileRepository, notaFileRepository);
//        Consola.setCurrentWeek(structuraAnUniversitar.getWeek(LocalDate.now(), structuraAnUniversitar.getSemestru(LocalDate.now())));
//        Consola consola = new Consola(service);
//        consola.runMenu();
        ApplicationMain.main(args);
    }
}