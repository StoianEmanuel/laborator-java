import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.util.Arrays;
import java.util.List;

public class SignUpForm {
    private JButton btnSignUp;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JPanel mainPanel;
    private JTextField txtNume;
    private JTextField txtPrenume;
    private JLabel lblNume;
    private JLabel lblPrenume;
    private JFrame owner;

    public SignUpForm(JFrame owner) {
        this.owner = owner;
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtUsername.getText() != null && txtPassword.getPassword() != null && txtPrenume.getText() != null && txtNume.getText() != null) {
                    if (WelcomeFormGUI.students.indexOf(new Student(txtNume.getText(), txtPrenume.getText())) != -1) {
                        {
                            try {
                                MenuStrategy menuStrategy = new StudentStrategy(new Student(txtNume.getText(), txtPrenume.getText(), WelcomeFormGUI.students.get(WelcomeFormGUI.students.indexOf(new Student(txtNume.getText(), txtPrenume.getText()))).grupa));
                                Application.getInstance().noi_utilizatori(txtUsername.getText(), Arrays.toString(txtPassword.getPassword()), menuStrategy);
                                JOptionPane.showMessageDialog(null, "Inscrierea a fost realizata cu succes");
                                mainPanel.setVisible(false);
                                owner.setContentPane(new WelcomeFormGUI(owner).getMainPanel());
                                owner.setTitle("WelcomeInterface");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Nu s-a putut crea contul de utilizator");
                            }
                        }
                    } else if (WelcomeFormGUI.teachers.indexOf(new Profesor(txtNume.getText(), txtPrenume.getText())) != -1) {
                        try {
                            MenuStrategy menuStrategy = new TeacherStrategy(new Profesor(txtNume.getText(), txtPrenume.getText()));
                            Application.getInstance().noi_utilizatori(txtUsername.getText(), Arrays.toString(txtPassword.getPassword()), menuStrategy);
                            JOptionPane.showMessageDialog(null, "Inscrierea a fost realizata cu succes");
                            mainPanel.setVisible(false);
                            owner.setContentPane(new WelcomeFormGUI(owner).getMainPanel());
                            owner.setTitle("WelcomeInterface");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Nu s-a putut crea contul de utilizator");
                        }
                    } else
                        JOptionPane.showMessageDialog(null, "Datele introduse nu apartin vreunui student sau profesor", "Informatii gresite", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
