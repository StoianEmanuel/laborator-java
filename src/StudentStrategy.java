import javax.swing.*;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class StudentStrategy implements MenuStrategy{
    public Student student;
    public StudentStrategy() { }
    StudentStrategy(Student student) {
        this.student = student;
    }

    ArrayList<User> userList;

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

    public Student returnStudent(){
        return student;
    }

    @Override
    public UserAccountType getAccountType() {
        return UserAccountType.STUDENT;
    }

    @Override
    public HashMap<String, String> getAccountHolderInformation() {
        return new HashMap<>() {{
            put(student.nume, student.prenume);
        }};
    }

    User getUser(String nume, String prenume){
        HashMap<String,String> user_cautat=new HashMap<>();
        user_cautat.put(student.nume,student.prenume);
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
        String[] list = Application.getInstance().manager.SerchStudent(student);
        for(String s : Application.getInstance().currentUser.menuStrategy.getAccountHolderInformation().keySet()) {
            String prenume = Application.getInstance().currentUser.menuStrategy.getAccountHolderInformation().get(s);
            double medie = Application.getInstance().manager.MediaStudent(new Student(s,prenume));
                System.out.println("Media acestui an: " + medie);
        }
        System.out.println("Cursuri: ");
        int i = 1;
        for(String s : list)
        {
            if(s == null)   {
                continue;
            }
            else {
                System.out.println(i + ". " + s.split(":")[0] + ": " + s.split(":")[1]);
                i++;
            }
        }

        System.out.println("x. " + " Inchidere aplicatie");
        try (Scanner scanner = new Scanner(System.in)){
            char c=scanner.next().charAt(0);
            if(c == 'x' || c=='X') {
                System.out.println("\nProcesul s-a incheiat");
            }
        }
        catch (Exception e)  {
            System.out.println(e.getMessage());
        }
    }
}
