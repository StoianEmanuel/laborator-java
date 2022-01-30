import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ProfesorForm {
    private JLabel lblUsername;
    private JPanel mainPanel;
    private JTextField txtUsername;
    private JButton btnCursuri;
    private JButton btnNote;
    private JButton btnStudenti;
    private JTextArea txtArea;
    private JButton btnNotare;
    private JTextArea txtNume;
    private JLabel lblNume;
    private JLabel lblNota;
    private JSpinner SpinNota;
    private JTextArea txtPrenume;
    private JLabel lblPrenume;
    private JTextArea txtCurs;
    private JLabel lblCurs;
    private JFrame owner;
    private User user;
    private ArrayList<Curs> cursuri_profesor;
    private ArrayList<Curs> toate_cursurile;
    public Profesor profesor;

    void visible_invisible(boolean b){
        SpinNota.setVisible(b);
        lblNume.setVisible(b);
        lblPrenume.setVisible(b);
        txtNume.setVisible(b);
        txtPrenume.setVisible(b);
        lblNota.setVisible(b);
        btnNotare.setVisible(b);
        lblCurs.setVisible(b);
        txtCurs.setVisible(b);
    }

    void noteaza_student(Student student, String nota){
        try {
            FileOutputStream fos = new FileOutputStream("src/cursuri.xml");
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.setExceptionListener(new ExceptionListener() {
                @Override
                public void exceptionThrown(Exception e) {
                    System.out.println(e);
                }
            });
            encoder.writeObject(toate_cursurile);
            encoder.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ProfesorForm(JFrame owner, User userul){
        this.owner=owner;
        this.user=userul;
        txtUsername.setText(user.userName);
        TeacherStrategy menuStrategy= (TeacherStrategy) user.menuStrategy;
        profesor=menuStrategy.returnProfesor();

        try(FileInputStream fis = new FileInputStream("src/cursuri.xml")) {
            XMLDecoder decoder = new XMLDecoder(fis);
            cursuri_profesor = (ArrayList<Curs>) decoder.readObject();
            decoder.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        toate_cursurile=cursuri_profesor;
        ArrayList<Curs> curs= new ArrayList<>();
        for(Curs c:cursuri_profesor)
            if(c.profu==profesor)
                curs.add(c);
        cursuri_profesor=curs;

        btnCursuri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtArea.setText("");
                visible_invisible(false);
                String s="";
                for(Curs c:cursuri_profesor){
                    s+=" "+c.nume+", descrierea cursului: "+c.descriere+"\nStudenti: ";
                    int i=0;
                    for(Student student:c.studenti){
                        s+=student.toString();
                        i++;
                        if(i%2==0 && i!=0)
                            s+="\n";
                    }
                }
                txtArea.setText(s);
            }
        });

        btnNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visible_invisible(true);
                SpinnerModel value = new SpinnerNumberModel(10, 1, 10, 1);
                SpinNota= new JSpinner(value);
            }
        });

        btnStudenti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtArea.setText("");
                visible_invisible(false);
                String s="";
                for(Curs c:cursuri_profesor){
                    s+=" "+c.nume+"\nStudenti: ";
                    for(Student student:c.studenti){
                        s+=student.toString()+", nota: "+c.note_studenti.get(student)+"\n";
                    }
                }
                txtArea.setText(s);
            }
        });

        btnNotare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index=0;
                for(;index<toate_cursurile.size();index++){
                    Curs c=toate_cursurile.get(index);
                    if(c.nume==txtCurs.getText()){
                        if(c.FindByNumeAndPrenume(txtNume.getText(),txtPrenume.getText())){
                            for(Student s:c.studenti)
                                if(s.nume==txtNume.getText() && s.prenume==txtPrenume.getText())
                                    if(c.note_studenti.get(s)==0) {
                                            String nota=SpinNota.getValue().toString();
                                            c.note_studenti.replace(s,Integer.parseInt(nota));
                                            toate_cursurile.set(index,c);
                                            noteaza_student(s,SpinNota.getValue().toString());
                                        }
                                    }
                        }
                    }
                visible_invisible(false);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
