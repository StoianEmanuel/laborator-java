import javax.swing.*;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.io.File;

enum LOAD_TYPE {
    HARDCODAT, KEYBOARD, FILE
}

enum DISPLAY_TYPE  {
    CONSOLA, FISIER, GUI
}

public class Main {

    public static void main(String[] args) {/*IDisplayManager displayManager = Settings.displayHashMap.get(Settings.displayType);
        IDataLoader dataManager = Settings.dataLoaderHashMap.get(Settings.loadType);
        displayManager.displayStudents(dataManager.createStudentsData());*/


        /*JFrame frame = new JFrame("Graphic user interface");
		LoginForm loginForm = new LoginForm(frame);
		frame.setContentPane(loginForm.getMainPanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);*/


        /*ManagerCursuri managerCursuri= new ManagerCursuri();
        Set<Student> studentSet= new HashSet<>();
        studentSet.add(new Student("Ion", "Andrei", 4702, 2));
        studentSet.add(new Student("Ionescu", "Andrei", 4702, 2));
        Curs curs=new Curs("nume","descriere", new Profesor("Popescu","Ion"),studentSet,2);
        managerCursuri.AddCurs(curs);


        System.out.println(curs.FindStudent(new Student("Ion", "Andrei", 4702, 2)));
        System.out.println(curs.toString());*/

        Set<Student> students= new HashSet<Student >();
        students.add(new Student("Ion","Andrei",4,2));
        students.add(new Student("Ionescu","Andrei",3,2));
        students.add(new Student("Florin","Andrei",4,2));
        Curs c= new Curs("nume","descriere",new Profesor("Andrei","Gheorghe"),students,2);
        System.out.println(c.ReturnStudent("Ion","Andrei"));


        Scanner sc = new Scanner(System.in);  ///
        System.out.println("Username = ");
        var username = sc.next();
        System.out.println("Password = ");
        var password = sc.next();


        try {
            Application.getInstance().login(new User(username, password));
            System.out.println(Application.getInstance().currentUser);
            System.out.println(Application.getInstance().currentUser.menuStrategy.getAccountHolderInformation());
            System.out.println(Application.getInstance().currentUser.menuStrategy.getAccountType());
            JFrame owner= new JFrame("StudentInterface");
            StudentForm studentForm=new StudentForm(owner,Application.getInstance().currentUser);
            owner.setContentPane(studentForm.getMainPanel());
            owner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            owner.pack();
            owner.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}