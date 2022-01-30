import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SignUpDisplay {

    public Curs[] ReadFile(){
        try(FileInputStream fis= new FileInputStream("src/cursuri.xml")){
            XMLDecoder decoder = new XMLDecoder(fis);
            Curs[] curs= (Curs[]) decoder.readObject();
            decoder.close();
            fis.close();
            return curs;
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return new Curs[0];
    }

    private boolean creare_user;

    public boolean checkifuserIscreated(){
        return creare_user;
    }

    public SignUpForm(String username, String password, String nume, String prenume, String tip_cont) {
        //verific sa nu existe user-ul
        creare_user=false;
        List<User> userList = new ArrayList<>();
        File inputfile = new File("user.xml");
        try (FileInputStream fis = new FileInputStream(inputfile)) {
            XMLDecoder decoder = new XMLDecoder(fis);
            userList = (ArrayList<User>) decoder.readObject();
            decoder.close();
            fis.close();
            for (User user : userList)
                if (user.userName == username) {
                    System.out.println("Acest utilizator exista");
                    break;
                }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        //verific ca numele si prenumele utilizate sa apartina unui profesor sau student dintr-un fisier
        Curs[] cursuri = ReadFile();

        if (tip_cont=="S") {
            int ok = 1;
            Student student = new Student();
            for (Curs curs : cursuri) {
                if (curs.FindByNumeAndPrenume(nume, prenume)) {
                    ok = 0;
                    student = curs.ReturnStudent(nume, prenume);
                }
            }
            if (ok == 1)
                System.out.println("Datele introduse nu apartin unui student");
            else {
                File file = new File("src/users.xml");
                try (FileOutputStream fos = new FileOutputStream(file, true)) {
                    userList.add(new User(username, password, new StudentStrategy(student)));
                    XMLEncoder encoder = new XMLEncoder(fos);
                    encoder.writeObject(new User(username, password, new StudentStrategy(student)));
                    encoder.close();
                    fos.close();
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                creare_user=true;

                JFrame owner = new JFrame("LoginInterface");
                LoginForm loginForm = new LoginForm(owner);
                owner.setContentPane(loginForm.getMainPanel());
                owner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                owner.pack();
                owner.setVisible(true);
            }
        } else if (tip_cont=="P") {
            int ok = 0;
            Profesor profesor = new Profesor();
            for (Curs curs : cursuri)
                if (curs.profu.nume == nume && curs.profu.prenume == prenume) {
                    ok = 1;
                    profesor = new Profesor(nume, prenume);
                }
            if (ok == 0) {
                System.out.println("Datele introduse nu apartin unui profesor");
            } else {
                File file = new File("src/users.xml");
                try (FileOutputStream fos = new FileOutputStream(file, true)) {
                    userList.add(new User(username, password, new TeacherStrategy(profesor)));
                    XMLEncoder encoder = new XMLEncoder(fos);
                    encoder.writeObject(new User(username, password, new TeacherStrategy(profesor)));
                    encoder.close();
                    fos.close();
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                creare_user=true;
                JFrame owner = new JFrame("LoginInterface");
                LoginForm loginForm = new LoginForm(owner);
                owner.setContentPane(loginForm.getMainPanel());
                owner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                owner.pack();
                owner.setVisible(true);
            }
        } else {
            System.out.println("Nu s-a putut crea user-ul");
        }
    }
}
