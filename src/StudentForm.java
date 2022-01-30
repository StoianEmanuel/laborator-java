import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

public class StudentForm {
    private JPanel mainPanel;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JButton btnMedieAn;
    private JList listCursuri;
    private JFrame owner;

    public StudentForm(JFrame owner){
        this.owner=owner;
        for(String s : Application.getInstance().currentUser.menuStrategy.getAccountHolderInformation().keySet()) {
            String prenume = Application.getInstance().currentUser.menuStrategy.getAccountHolderInformation().get(s);
            String[] list = Application.getInstance().manager.SerchStudent(new Student(s,prenume));
            listCursuri.setListData(list);
        }
        txtUsername.setText(Application.getInstance().currentUser.userName);

        btnMedieAn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == btnMedieAn)
                {
                    for(String s : Application.getInstance().currentUser.menuStrategy.getAccountHolderInformation().keySet()) {
                        String prenume = Application.getInstance().currentUser.menuStrategy.getAccountHolderInformation().get(s);
                        JOptionPane.showMessageDialog(null,"Media pe acest an universitar este: " + Application.getInstance().manager.MediaStudent(new Student(s,prenume)));
                    }
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}