import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm {
    private JPanel mainPanel;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lblPassword;
    private JButton btnLogin;
    private JFrame owner;

    public LoginForm(JFrame owner) {
        this.owner = owner;

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( e.getSource() == btnLogin) {
                    if(txtUsername.getText()!=null && txtPassword.getPassword()!=null)
                    try {
                        Application.getInstance().login(new User(txtUsername.getText(), new String(txtPassword.getPassword())));
                        if(Application.getInstance().currentUser.menuStrategy.getAccountType() == UserAccountType.STUDENT)
                        {
                            mainPanel.setVisible(false);
                            owner.setContentPane(new StudentForm(owner).getMainPanel());
                        }
                        else
                            if(Application.getInstance().currentUser.menuStrategy.getAccountType()==UserAccountType.TEACHER) {
                                mainPanel.setVisible(false);
                                owner.setContentPane(new ProfesorForm(owner).getMainPanel());
                                owner.setTitle("Welcome: " + Application.getInstance().currentUser.menuStrategy.getAccountHolderInformation().keySet().toString() + " " + Application.getInstance().currentUser.menuStrategy.getAccountHolderInformation().values().toString());
                            }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage(),"Campuri goale",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
