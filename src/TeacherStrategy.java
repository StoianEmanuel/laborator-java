import javax.swing.*;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
        JFrame owner= new JFrame("ProfesorInterface");
        ProfesorForm profesorForm=new ProfesorForm(owner,getUser(profesor.nume,profesor.prenume));
        owner.setContentPane(profesorForm.getMainPanel());
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