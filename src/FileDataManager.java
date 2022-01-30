import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FileDataManager implements IDataLoader{
    @Override
    public Student[] createStudentsData() {
        // Settings.STUDENTS_PATH;
        try(FileInputStream fis = new FileInputStream("src/studenti.xml")) {
            XMLDecoder decoder = new XMLDecoder(fis);
            Student[] students = (Student[]) decoder.readObject();
            decoder.close();
            fis.close();
            return students;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Student[0];
    }

    @Override
    public Profesor[] createProfesorData() {
        // Settings.TEACHERS_PATH;
        try(FileInputStream fis = new FileInputStream("src/profesori.xml")) {
            XMLDecoder decoder = new XMLDecoder(fis);
            Profesor[] students = (Profesor[])decoder.readObject();
            decoder.close();
            fis.close();
            return students;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Profesor[0];
    }

    @Override
    public Curs[] createCoursesData() {
        // Settings.COURSES_PATH;
        try(FileInputStream fis = new FileInputStream("src/cursuri.xml")) {
            XMLDecoder decoder = new XMLDecoder(fis);
            Curs[] students = (Curs[]) decoder.readObject();
            decoder.close();
            fis.close();
            return students;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Curs[0];
    }

    public ManagerCursuri manager = new ManagerCursuri(createCoursesData());
    public Random rand = new Random();
    public int minimumRequiredStudents = 5;
    public Student[] dataSetOfStudent = createStudentsData();
    public Profesor[] dataSetOfProfesor = createProfesorData();

    public Set<Student> set_aleator_de_student() {
        int studentInscrisiLaCurs = minimumRequiredStudents + rand.nextInt(dataSetOfStudent.length - minimumRequiredStudents);
        Set<Student> setOfStudents = new HashSet<>();
        for (int i = 0; i < studentInscrisiLaCurs; i++) {
            int randomStudentIndex = rand.nextInt(dataSetOfStudent.length);
            setOfStudents.add(dataSetOfStudent[randomStudentIndex]);
        }
        return setOfStudents;
    }
}