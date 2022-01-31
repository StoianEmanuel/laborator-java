import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WelcomeFormGUI implements ActionListener{

    public WelcomeFormGUI(JFrame owner, ArrayList<Student> students, ArrayList<Profesor> teachers)   {
        this.owner = owner;
        this.students = students;
        this.teachers = teachers;
        btnLogin.addActionListener(this);
        btnSignUp.addActionListener(this);
    }

    public WelcomeFormGUI(JFrame owner)  {
        this.owner = owner;
        btnLogin.addActionListener(this);
        btnSignUp.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSignUp)  {
            mainPanel.setVisible(false);
            owner.setContentPane(new SignUpForm(owner).getMainPanel());
            owner.setTitle("SignUpInterface");
        }
        else
            if(e.getSource() == btnLogin)   {
            mainPanel.setVisible(false);
            owner.setContentPane(new LoginForm(owner).getMainPanel());
            owner.setTitle("LoginInterface");
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JPanel mainPanel;
    private JButton btnLogin;
    private JButton btnSignUp;
    private JFrame owner;
    public static ArrayList<Student> students;
    public static ArrayList<Profesor> teachers;
}