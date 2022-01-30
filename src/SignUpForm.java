import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SignUpForm {
    private JButton btnSignUp;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JRadioButton rbStudent;
    private JRadioButton rbProfesor;
    private JLabel lblCont;
    private JPanel mainPanel;
    private JTextField txtNume;
    private JTextField txtPrenume;
    private JLabel lblNume;
    private JLabel lblPrenume;
    private JLabel lblEroare;
    private JFrame owner;

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

    public SignUpForm(JFrame owner){
        this.owner=owner;
        btnSignUp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //verific sa nu existe user-ul
                    List<User> userList= new ArrayList<>();
                    File inputfile= new File("user.xml");
                    try(FileInputStream fis = new FileInputStream(inputfile)){
                        XMLDecoder decoder= new XMLDecoder(fis);
                        userList= (ArrayList<User>)decoder.readObject();
                        decoder.close();
                        fis.close();
                        for(User user:userList)
                            if(user.userName==txtUsername.getText())
                            {
                                lblEroare.setText("Acest username exista deja");
                                break;
                            }
                    }
                    catch (FileNotFoundException exception){
                        exception.printStackTrace();
                    }
                    catch (IOException exception){
                        exception.printStackTrace();
                    }

                    //verific ca numele si prenumele utilizate sa apartina unui profesor sau student dintr-un fisier
                    Curs[] cursuri= ReadFile();

                    if(rbStudent.isSelected()) {
                        int ok=1;
                        Student student= new Student();
                        for(Curs curs:cursuri) {
                            if (curs.FindByNumeAndPrenume(txtNume.getText(), txtPrenume.getText())) {
                                ok = 0;
                                student = curs.ReturnStudent(txtNume.getText(),txtPrenume.getText());
                            }
                        }
                        if(ok==1)
                            lblEroare.setText("Datele introduse nu apartin unui student");
                        else {
                            File file= new File("src/users.xml");
                            try (FileOutputStream fos = new FileOutputStream(file, true)) {
                                char[] password = txtPassword.getPassword();
                                userList.add(new User(txtUsername.getText(), new String(password), new StudentStrategy(student)));
                                XMLEncoder encoder = new XMLEncoder(fos);
                                encoder.writeObject(new User(txtUsername.getText(), new String(password), new StudentStrategy(student)));
                                encoder.close();
                                fos.close();
                            } catch (FileNotFoundException exception) {
                                exception.printStackTrace();
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                            JFrame owner= new JFrame("LoginInterface");
                            LoginForm loginForm=new LoginForm(owner);
                            owner.setContentPane(loginForm.getMainPanel());
                            owner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            owner.pack();
                            owner.setVisible(true);
                        }
                    }
                    else
                        if (rbProfesor.isSelected()) {
                            int ok=0;
                            Profesor profesor= new Profesor();
                            for (Curs curs : cursuri)
                                if (curs.profu.nume == txtNume.getText() && curs.profu.prenume == txtPrenume.getText()) {
                                    ok = 1;
                                    profesor = new Profesor(txtNume.getText(), txtPrenume.getText());
                                }
                                if(ok==0){
                                    lblEroare.setText("Datele introduse nu apartin unui profesor");
                                }
                                else {
                                    File file= new File("src/users.xml");
                                    try (FileOutputStream fos = new FileOutputStream(file, true)) {
                                        char[] password = txtPassword.getPassword();
                                        userList.add(new User(txtUsername.getText(), new String(password), new TeacherStrategy(profesor)));
                                        XMLEncoder encoder = new XMLEncoder(fos);
                                        encoder.writeObject(new User(txtUsername.getText(), new String(password), new TeacherStrategy(profesor)));
                                        encoder.close();
                                        fos.close();
                                    } catch (FileNotFoundException exception) {
                                        exception.printStackTrace();
                                    } catch (IOException exception) {
                                        exception.printStackTrace();
                                    }
                                    JFrame owner= new JFrame("LoginInterface");
                                    LoginForm loginForm=new LoginForm(owner);
                                    owner.setContentPane(loginForm.getMainPanel());
                                    owner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    owner.pack();
                                    owner.setVisible(true);
                                }
                        }
                        else{
                            lblEroare.setText("Alegeti un tip de utilizator");
                        }
                }
            });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
