import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
}