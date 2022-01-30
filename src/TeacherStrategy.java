import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TeacherStrategy implements MenuStrategy {
    public Profesor profesor;
    public TeacherStrategy() { }

    ArrayList<User> userList;

    TeacherStrategy(Profesor p) {
        this.profesor = p;
    }
    @Override
    public UserAccountType getAccountType() {
        return UserAccountType.TEACHER;
    }

    public Profesor returnProfesor(){
        return profesor;
    }

    @Override
    public HashMap<String, String> getAccountHolderInformation() {
        return new HashMap<>() {{
            put(profesor.nume, profesor.prenume);
        }};
    }

    void update_userList() {
        try (FileInputStream fis = new FileInputStream("src/users.xml")) {
            XMLDecoder decoder = new XMLDecoder(fis);
            this.userList = (ArrayList<User>) decoder.readObject();
            decoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    User return_user(String username, String password){
        for(User user:userList)
            if(user.userName==username && user.password==password)
                return user;
        return null;
    }

    User getUser(String nume, String prenume){
        HashMap<String,String> user_cautat=new HashMap<>();
        user_cautat.put(profesor.nume,profesor.prenume);
        for(User user:userList)
            if(user.menuStrategy.getAccountHolderInformation()==user_cautat)
                return user;
            return null;
    }

    @Override
    public String[] getAccountMenuOptions() {
        return new String[0];
    }

    @Override
    public void nextMenuOption() {
    }

    @Override
    public void previousMenuOption() {
    }

    @Override
    public void menuOption() {
        System.out.println("Cursuri: ");
        ArrayList<String> list = Application.getInstance().manager.cursuri_profesor(profesor);
        int index,x, i;
        boolean ok = false;
        try (Scanner cin = new Scanner(System.in)) {
            do {
                if (!ok) {
                    ok = true;
                }
                else {
                    System.out.println("Cursuri: ");
                    Application.getInstance().manager.cursuri_profesor(profesor);
                }
                index = cin.nextInt();
                if (index != list.size() + 1) {
                    String nume_curs = list.get(index - 1);
                    ArrayList<String> listStudents = Application.getInstance().manager.StudentiCurs(new Curs(nume_curs));
                    int index_curs = Application.getInstance().manager.getCursuri().indexOf(new Curs(nume_curs));
                    do {
                        x = cin.nextInt();
                        i = 1;
                        if (x != listStudents.size() + 1) {
                            Student s = new Student(listStudents.get(x - 1).split(" ")[1], listStudents.get(x - 1).split(" ")[2]);
                            System.out.println("Noteaza studentul ( numere intregi ):");
                            x = cin.nextInt();
                            if (x != 11)
                                Application.getInstance().manager.getCursuri().get(index_curs).AddNotaToStud(s, x);
                            for (Student student : Application.getInstance().manager.getCursuri().get(index_curs).studenti) {
                                System.out.println(i + ". " + student.nume + " " + student.prenume + ": " + Application.getInstance().manager.getCursuri().get(index_curs).note_studenti.get(student));
                            }
                            System.out.println(listStudents.size() + 1 + ". " + "Intoarecere la vizualizare cursuri");
                        }
                    } while (x != listStudents.size() + 1);
                }
            }
            while (index != list.size() + 1);
            Application.getInstance().displayManager.displayCourses(Application.getInstance().manager.getCursuriArray());
            if(index == list.size() + 1)  {
                System.out.println("\nProcesul s-a incheiat");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}