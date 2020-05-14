package services;

import com.sun.tools.javac.util.Pair;
import domains.*;
import events.ChangeEvent;
import events.ChangeEventType;
import observer.Observable;
import observer.Observer;
import repository.XMLRepository.XMLNotaRepository;
import repository.inMemoryRepository.CrudRepository;
import utils.Paths;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Service implements Observable<ChangeEvent> {

    private static int currentWeek = StructuraAnUniversitar.getInstance(Paths.AN_UNIVERSITAR).getWeek(LocalDate.now(), StructuraAnUniversitar.getInstance().getSem1());
    private CrudRepository<String, Student> studentRepository;
    private CrudRepository<String, Tema> temaRepository;
    private CrudRepository<Pair<String, String>, Nota> notaRepository;

    public Service(CrudRepository<String, Student> studentFileRepository, CrudRepository<String, Tema> temaFileRepository, CrudRepository<Pair<String, String>, Nota> notaFileRepository) {
        this.studentRepository = studentFileRepository;
        this.temaRepository = temaFileRepository;
        this.notaRepository = notaFileRepository;
    }

    public static void setCurrentWeek(int currentWeek) {
        Service.currentWeek = currentWeek;
    }

    public Student findOneStudent(String id) {
        return studentRepository.findOne(id);
    }

    public Iterable<Student> findAllStudent() {
        return studentRepository.findAll();
    }

    public List<Student> getAllStudent(){
        List<Student> all = new ArrayList<>();
        studentRepository.findAll().forEach(all::add);
        return all;
    }

    public List<Tema> getAllTema(){
        List<Tema> all = new ArrayList<>();
        temaRepository.findAll().forEach(all::add);
        return all;
    }

    public List<Nota> getAllNota() {
        List<Nota> all = new ArrayList<>();
        notaRepository.findAll().forEach(all::add);
        return all;
    }

    public Student saveStudent(String id, String nume, String prenume, String email, String cadruDidacticLab, int grupa) {
        Student st = new Student(nume, prenume, email, cadruDidacticLab, grupa);
        st.setId(id);
        st.setNr_motivari(2);
        return studentRepository.save(st);
    }

    public Student deleteStudent(String id) {
        return studentRepository.delete(id);
    }

    public Student updateStudent(String id, String nume, String prenume, String email, String cadruDidacticLab, int grupa) {
        Student st = new Student(nume, prenume, email, cadruDidacticLab, grupa);
        st.setId(id);
        return studentRepository.update(st);
    }

    public Tema findOneTema(String id) {
        return temaRepository.findOne(id);
    }

    public Iterable<Tema> findAllTema() {
        return temaRepository.findAll();
    }

    public Tema saveTema(String id, String descriere, int startWeek, int deadLineWeek) {
        Tema tema = new Tema(descriere, startWeek, deadLineWeek);
        tema.setId(id);
        return temaRepository.save(tema);
    }

    public Tema deleteTema(String id) {
        return temaRepository.delete(id);
    }

    public Tema updateTema(String id, String descriere, int startWeek, int deadLineWeek) {
        Tema tema = new Tema(descriere, startWeek, deadLineWeek);
        tema.setId(id);
        return temaRepository.update(tema);
    }

    public Nota findOneNota(Pair<String, String> id) {
        return notaRepository.findOne(id);
    }

    public Iterable<Nota> findAllNota() {
        return notaRepository.findAll();
    }

    public Nota saveNota(String idStudent, String idTema, LocalDate data, String profesor, float valoare, String feedback, String raspuns) throws IllegalAccessException, InstantiationException {
        Student student = studentRepository.findOne(idStudent);
        Tema tema = temaRepository.findOne(idTema);
        if (student == null || tema == null)
            throw new ServiceException("Nota nu se poate asigna, id invalid.");

        Student student1 = new Student(student.getNume(), student.getPrenume(), student.getEmail(), student.getCadruDidacticLab(), student.getGrupa());
        student1.setId(idStudent);
        student1.setNr_motivari(student.getNr_motivari());

        if (((currentWeek - tema.getDeadlineWeek() == 1 || currentWeek - tema.getDeadlineWeek() == 2) && raspuns.equals("NU")) ||
                (currentWeek - tema.getDeadlineWeek() >= 1 && raspuns.equals("DA") && student1.getNr_motivari() == 0))
            valoare -= currentWeek - tema.getDeadlineWeek();

        if (currentWeek - tema.getDeadlineWeek() >= 3)
            valoare = 1f;

        if (currentWeek - tema.getDeadlineWeek() == 1 && raspuns.equals("DA") && student1.getNr_motivari() >= 1) {
            int nr_motivari = student1.getNr_motivari();
            nr_motivari--;
            student1.setNr_motivari(nr_motivari);
        }

        if (currentWeek - tema.getDeadlineWeek() == 2 && raspuns.equals("DA") && student1.getNr_motivari() == 2)
            student1.setNr_motivari(0);

        if (currentWeek - tema.getDeadlineWeek() == 2 && raspuns.equals("DA") && student1.getNr_motivari() == 1) {
            valoare -= 1;
            student1.setNr_motivari(0);
        }

        studentRepository.update(student1);
        Nota nota = new Nota(new Pair<>(idStudent, idTema), data, profesor, valoare);
//        nota.setId(new Pair<>(idStudent, idTema));
        NotaDTO notaDTO = new NotaDTO(student.getNume(), tema.getId(), nota.getValoare(), nota.getData(), tema.getDeadlineWeek(), feedback);
        XMLNotaRepository.getInstance().saveToTextFile(notaDTO);
        return notaRepository.save(nota);
    }

    public Nota saveNotaG(String idStudent, String idTema, LocalDate data, String profesor, float valoare, Integer motivari, String feedback) throws IllegalAccessException, InstantiationException {
        Student student = studentRepository.findOne(idStudent);
        Tema tema = temaRepository.findOne(idTema);
            if (student == null || tema == null)
                throw new ServiceException("Nota nu se poate asigna, id invalid.");

            Student student1 = new Student(student.getNume(), student.getPrenume(), student.getEmail(), student.getCadruDidacticLab(), student.getGrupa());
        student1.setId(idStudent);

        if (((currentWeek - tema.getDeadlineWeek() == 1 || currentWeek - tema.getDeadlineWeek() == 2)))
            valoare -= currentWeek - tema.getDeadlineWeek();

//        if (currentWeek - tema.getDeadlineWeek() >= 3 && motivari )
//            valoare = 1f;

        if (motivari <= 2 && (currentWeek - tema.getDeadlineWeek() - motivari <= 2)) {
            valoare += motivari;
        }
        else
            valoare = 1f;


        studentRepository.update(student1);
        Nota nota = new Nota(new Pair<>(idStudent, idTema), data, profesor, valoare);
//        nota.setId(new Pair<>(idStudent, idTema));
        NotaDTO notaDTO = new NotaDTO(student.getNume(), tema.getId(), nota.getValoare(), nota.getData(), tema.getDeadlineWeek(), feedback);
        XMLNotaRepository.getInstance().saveToTextFile(notaDTO);
        Nota n = notaRepository.save(nota);
        if(n == null){
            notifyObservers(new ChangeEvent(ChangeEventType.ADD, n));
        }
        return n;
    }

    public Nota deleteNota(Pair<String, String> id) {
        return notaRepository.delete(id);
    }

    public Nota updateNota(String idStudent, String idTema, LocalDate data, String profesor, float valoare) {
        Nota nota = new Nota(new Pair<>(idStudent, idTema), data, profesor, valoare);
//        nota.setId(new Pair<>(idStudent, idTema));
        return notaRepository.update(nota);
    }

    public <E> List<E> genericFilter(List<E> lista, Predicate<E> p) {
        return lista.stream()
                .filter(p)
                .collect(Collectors.toList());
    }

    public List<Student> filtrareStudentGrupa(int grupa) {
        List<Student> listaStudenti = new ArrayList<Student>((Collection<? extends Student>) findAllStudent());
        Predicate<Student> p1 = st -> st.getGrupa() == grupa;
        List<Student> rezultat = genericFilter(listaStudenti, p1);
        if (rezultat.size() == 0)
            throw new ServiceException("Nu s-au gasit rapoarte!");
        return rezultat;
    }

    public List<FiltrareDTO> cautaStudent(List<Nota> lista) {
        List<FiltrareDTO> rezultat = new ArrayList<>();
        lista.forEach(nota -> {
            Student student = studentRepository.findOne(nota.getId().fst);
            Tema tema = temaRepository.findOne(nota.getId().snd);
            LocalDate data = nota.getData();
            int nrSaptamana = StructuraAnUniversitar.getInstance().getWeek(data, StructuraAnUniversitar.getInstance().getSemestru(data));
            FiltrareDTO entity = new FiltrareDTO(student.getNume(), student.getPrenume(), nota.getProfesor(), tema.getDescriere(), nrSaptamana);
            rezultat.add(entity);
        });
        return rezultat;
    }

    public List<FiltrareDTO> filtrareNotaStudentTema(String idTema) {
        List<Nota> catalog = new ArrayList<Nota>((Collection<? extends Nota>) findAllNota());
        Predicate<Nota> p1 = nota -> nota.getId().snd.equals(idTema);
        List<Nota> lista = genericFilter(catalog, p1);
        List<FiltrareDTO> rezultat = cautaStudent(lista);
        if (rezultat.size() == 0)
            throw new ServiceException("Nu s-au gasit rapoarte!");
        return rezultat;
    }

    public List<FiltrareDTO> filtrareNotaProfesor(String idTema, String profesor) {
        List<Nota> catalog = new ArrayList<Nota>((Collection<? extends Nota>) findAllNota());
        Predicate<Nota> p1 = nota -> nota.getId().snd.equals(idTema) && nota.getProfesor().equals(profesor);
        List<Nota> lista = genericFilter(catalog, p1);
        List<FiltrareDTO> rezultat = cautaStudent(lista);
        if (rezultat.size() == 0)
            throw new ServiceException("Nu s-au gasit rapoarte!");
        return rezultat;
    }

    public List<FiltrareDTO> filtrareNotaSaptamnaData(String idTema, int nrSaptamana) {
        List<Nota> catalog = new ArrayList<Nota>((Collection<? extends Nota>) findAllNota());
        Predicate<Nota> p1 = nota -> nota.getId().snd.equals(idTema) && StructuraAnUniversitar.getInstance()
                .getWeek(nota.getData(), StructuraAnUniversitar.getInstance().getSemestru(nota.getData())) == nrSaptamana;
        List<Nota> lista = genericFilter(catalog, p1);
        List<FiltrareDTO> rezultat = cautaStudent(lista);
        if (rezultat.size() == 0)
            throw new ServiceException("Nu s-au gasit rapoarte!");
        return rezultat;
    }


    private ArrayList<Observer<ChangeEvent>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<ChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<ChangeEvent> e) {
        observers.remove(e);
    }


    @Override
    public void notifyObservers(events.ChangeEvent t) {
        observers.stream().forEach(x -> x.update(t));
    }

    private static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public List<StudentMedie> getAllStudentiMedie() {
        List<StudentMedie> list = new ArrayList<>();
        Integer sumaPonderi = 0;
        for (Tema tema : getAllTema()) {
            sumaPonderi += (tema.getDeadlineWeek() - tema.getStartWeek());
        }
        for (Student student : getAllStudent()) {
            Float suma = 0f;
            Float medieFinala = 0f;
            Integer pondere;
            for (Nota nota : getAllNota()) {
                if (nota.getId().fst.equals(student.getId())) {
                    Tema tema = findOneTema(nota.getId().snd);
                    pondere = tema.getDeadlineWeek() - tema.getStartWeek();
                    suma += nota.getValoare() * pondere;
                }
            }
            if (suma != 0)
                medieFinala = suma / sumaPonderi;
            else
                medieFinala = 0f;
            medieFinala = round(medieFinala, 2);
            StudentMedie studentMedie = new StudentMedie(student.getId(), student.getNume(), student.getGrupa(), medieFinala);
            list.add(studentMedie);
        }
        return list;
    }

    //studentii care pot intra in examen
    public List<StudentMedie> getAllStudentiExamen() {
        List<StudentMedie> list = new ArrayList<>();
        Integer sumaPonderi = 0;
        for (Tema tema : getAllTema()) {
            sumaPonderi += (tema.getDeadlineWeek() - tema.getStartWeek());
        }
        for (Student student : getAllStudent()) {
            Float suma = 0f;
            Float medieFinala = 0f;
            Integer pondere;
            for (Nota nota : getAllNota()) {
                if (nota.getId().fst.equals(student.getId())) {
                    Tema tema = findOneTema(nota.getId().snd);
                    pondere = tema.getDeadlineWeek() - tema.getStartWeek();
                    suma += nota.getValoare() * pondere;
                }
            }
            if (suma != 0)
                medieFinala = suma / sumaPonderi;
            else
                medieFinala = 0f;
            medieFinala = round(medieFinala, 2);
            StudentMedie studentMedie = new StudentMedie(student.getId(), student.getNume(), student.getGrupa(), medieFinala);
            if (studentMedie.getMedie() >= 4) {
                list.add(studentMedie);
            }
        }
        return list.stream()
                .sorted((p1, p2) -> p2.getMedie().compareTo(p1.getMedie())).collect(Collectors.toList());
    }

    //cele mai grele teme
    public List<TemaMedie> getAllTemeMedie() {
        List<TemaMedie> list = new ArrayList<>();
        for (Tema tema : getAllTema()) {
            Float suma = 0f;
            Float media = 0f;
            Integer nr = 0;
            for (Nota nota : getAllNota()) {
                if (nota.getId().snd.equals(tema.getId())) {
                    suma += nota.getValoare();

                }
                nr += 1;
            }
            if (suma != 0)
                media = suma / nr;
            else
                media = 0f;
            media = round(media, 2);
            TemaMedie temaMedie = new TemaMedie(tema.getId(), tema.getDescriere(), media);
            list.add(temaMedie);
        }
        return list.stream()
                .sorted(Comparator.comparing(TemaMedie::getMedie)).collect(Collectors.toList());
    }

    //studentii care au predat toate temele la timp
    public List<Student> getAllStudentiSilitori() {
        List<Student> list = new ArrayList<>();
        for (Student student : getAllStudent()) {
            boolean laTimp = true;
            boolean macarUna = false;
            for (Nota nota : getAllNota()) {
                if (nota.getId().fst.equals(student.getId())) {
                    macarUna = true;
                    Tema tema = findOneTema(nota.getId().snd);
                    if (tema.getDeadlineWeek() < StructuraAnUniversitar.getInstance().getWeek(nota.getData(), StructuraAnUniversitar.getInstance().getSem1()))
                        laTimp = false;
                }
            }
            if (laTimp && macarUna)
                list.add(student);
        }
        return list;
    }
}
