public class FileDisplay implements IDisplayManager{
    @Override
    public void displayStudents(Student[] students) {
        System.out.println("Se implementeaza scriere in fisier");
    }

    @Override
    public void displayTeachers(Profesor[] profesors) {
        System.out.println("Se implementeaza scriere in fisier");
    }

    @Override
    public void displayCourses(Curs[] cursuri) {
        System.out.println("Se implementeaza scriere in fisier");
    }
}