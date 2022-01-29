import javax.swing.*;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

    //
    public Student returnStudent(){
        return student;
    }
    //

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
        JFrame owner= new JFrame("StudentInterface");
        StudentForm studentForm=new StudentForm(owner,getUser(student.nume,student.prenume));
        owner.setContentPane(studentForm.getMainPanel());
        owner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        owner.pack();
        owner.setVisible(true);
    }

    @Override
    public void previousMenuOption() {
        JFrame frame= new JFrame("LoginForm");
        LoginForm loginForm = new LoginForm(frame);
        frame.setContentPane(loginForm.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}