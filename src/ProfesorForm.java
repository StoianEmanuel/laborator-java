import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProfesorForm {
    private JLabel lblUsername;
    private JPanel mainPanel;
    private JTextField txtUsername;
    private JButton btnNoteaza;
    private JButton btnStudenti;
    private JList listCursuri;
    private JList listStudenti;
    private JLabel lblStudenti;
    private JSpinner spinnerNota;
    private JLabel lblNota;
    private JLabel lblCursuri;
    private JFrame owner;

    public ProfesorForm(JFrame owner){
        this.owner=owner;
        txtUsername.setText(Application.getInstance().currentUser.userName);
        int min = 1;
        int max = 10;
        int step = 1;
        int t = 1;
        SpinnerModel spinner = new SpinnerNumberModel(t,min,max,step);
        spinnerNota=new JSpinner(spinner);

        String[] list = new String[Application.getInstance().manager.getCursuri().size()];
        for (String s : Application.getInstance().currentUser.menuStrategy.getAccountHolderInformation().keySet()) {
            String prenume = Application.getInstance().currentUser.menuStrategy.getAccountHolderInformation().get(s);
            int i = 0;
            for (Curs c : Application.getInstance().manager.getCursuri()) {
                if (c.profu.nume.equals(s) && c.profu.prenume.equals(prenume)) {
                    list[i] = c.nume;
                    i++;
                }
            }
        }
        listCursuri.setListData(list);

        btnStudenti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnStudenti) {
                    if (listCursuri.getSelectedIndex() != -1) {
                        lblStudenti.setText("Studentii din " + listCursuri.getSelectedValue());
                        try {
                            int i = 0;
                            Curs c = Application.getInstance().manager.search(new Curs((String) listCursuri.getSelectedValue()));
                            String[] list = new String[c.studenti.size()];

                            for (Student s : c.studenti) {
                                String nota;
                                if(c.note_studenti.get(s)==null)
                                    nota=null;
                                else
                                    nota=c.note_studenti.get(s).toString();
                                list[i] = s.nume + " " + s.prenume + " nota: " + nota;
                                i++;
                            }
                            listStudenti.setListData(list);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                }
            }
        });

        btnNoteaza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnNoteaza) {
                    if (listStudenti.getSelectedIndex() != -1 && listCursuri.getSelectedIndex() != -1) {
                        String data = (String) listStudenti.getSelectedValue();
                        for (Curs c : Application.getInstance().manager.getCursuri()) {
                            if (c.equals(new Curs((String) listCursuri.getSelectedValue()))) {
                                try {
                                    c.AddNotaToStud(new Student((String) ((String) listStudenti.getSelectedValue()).split(" ")[0], (String) ((String) listStudenti.getSelectedValue()).split(" ")[1]), Integer.parseInt((String) spinnerNota.getValue()));
                                    try {
                                        Curs curs = Application.getInstance().manager.search(new Curs((String) listCursuri.getSelectedValue()));
                                        String[] list = new String[curs.studenti.size()];

                                        int nr = 0;
                                        for (Student s : curs.studenti) {
                                            String nota="";
                                            if(curs.note_studenti.get(s)!=null)
                                                nota=curs.note_studenti.get(s).toString();
                                            else
                                                nota+=' ';
                                            list[nr++] = s.nume + " " + s.prenume + " nota: " + nota;
                                        }
                                        listStudenti.setListData(list);
                                    } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(null, ex.getMessage());
                                    }
                                    Application.getInstance().displayManager.displayCourses(Application.getInstance().manager.getCursuriArray());
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, ex.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
