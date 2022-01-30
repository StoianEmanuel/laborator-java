import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class LoginForm {
    private JPanel mainPanel;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblPassword;
    private JButton btnLogin;
    private JButton btnSignUp;
    private JFrame owner;

    private ArrayList<User> userList;

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

    public LoginForm(JFrame owner) {
        this.owner = owner;
        this.update_userList();
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( e.getSource() == btnLogin) {
                    try {
                        Application.getInstance().login(new User(txtUsername.getText(), new String(txtPassword.getPassword())));
                        JOptionPane.showMessageDialog(null, "Login successfully!");
                        mainPanel.setVisible(false);
                        User user= return_user(txtUsername.getText(),txtPassword.getPassword().toString());
                        if(user.menuStrategy.getClass().getName()=="TeacherStrategy") {
                            owner.setContentPane(new ProfesorForm(owner, user).getMainPanel());
                        }
                        else {
                            owner.setContentPane(new StudentForm(owner, user).getMainPanel());
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });

        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame owner= new JFrame("SignUpInterface");
                SignUpForm signUpForm= new SignUpForm(owner);
                owner.setContentPane(signUpForm.getMainPanel());
                owner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                owner.pack();
                owner.setVisible(true);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
