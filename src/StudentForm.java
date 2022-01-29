import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class StudentForm {
    private JPanel mainPanel;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JButton btnCursuri;
    private JButton btnNote;
    private JButton btnMedieAn;
    private JButton btnRestante;
    private JTextArea txtArea;
    private JFrame owner;
    private User user;
    Curs[] students;

    public static <K, V extends Comparable<V> > Map<K, V> valueSort(final Map<K, V> map)
    {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(K k1, K k2)   {
                int comp = map.get(k1).compareTo(map.get(k2));
                if (comp == 0)
                    return 1;
                else
                    return comp;
            }
        };
        Map<K, V> sorted = new TreeMap<K, V>(valueComparator);
        sorted.putAll(map);
        return sorted;
    }

    TreeMap<String,Integer> cursuri;
    TreeMap<String,Integer> note;
    Student student;

    void initiere(){
        cursuri=new TreeMap<String,Integer>();
        for(Curs curs:students) {
            System.out.println(curs.studenti);
            if (curs.ReturnStudent(student.nume, student.prenume) != null) {
                System.out.println(cursuri.size());
                cursuri.put(curs.nume, curs.an);
            }
        }

        note=new TreeMap<String,Integer>();
        for(Curs curs:students)
            if(curs.FindByNumeAndPrenume(student.nume,student.prenume)==true) {
                note.put(curs.nume, curs.nota_student(student.nume, student.prenume));
            }
    }

    public StudentForm(JFrame owner, User userul){
        this.owner=owner;
        this.user=userul;

        txtUsername.setText(user.userName);
        StudentStrategy menuStrategy= (StudentStrategy) user.menuStrategy;
        student=menuStrategy.returnStudent();

            try(FileInputStream fis = new FileInputStream("src/cursuri.xml")) {
                XMLDecoder decoder = new XMLDecoder(fis);
                students = (Curs[]) decoder.readObject();
                decoder.close();
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            initiere();
            if(cursuri.isEmpty())
                System.out.println("eroe");
            System.out.println("Initial Mappings are: "
                + cursuri);

        btnCursuri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtArea.setText(null);
                /*TreeMap<String,Integer> display=new TreeMap();
                for(Curs curs:students)
                    if(curs.FindByNumeAndPrenume(student.nume,student.prenume))
                        display.put(curs.nume,curs.an);*/

                Map<String,Integer> sortedMap = valueSort(cursuri);
                int anul=1;
                String s="Anul 1: \n";
                for (Map.Entry<String, Integer> entry1 : sortedMap.entrySet()) {
                    String key1= entry1.getKey();
                    Integer value1 = entry1.getValue();
                    if((int)value1!=anul){
                        s+="Anul "+ anul+":\n";
                        anul++;
                    }
                    s+=" "+key1+"\n";
                }

                txtArea.setText(s);
            }
        });

        btnNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtArea.setText("");
                /*TreeMap<String,Integer> display=new TreeMap(); for(Curs curs:students)
                    if(curs.FindByNumeAndPrenume(student.nume,student.prenume))    display.put(curs.nume,curs.nota_student(student.nume,student.prenume));*/

                Map<String, Integer> sortedMap= valueSort(cursuri);
                Map<String, Integer> map= valueSort(note);

                int anul=1;
                String s="Anul 1: \n";
                for (Map.Entry<String, Integer> entry1 : sortedMap.entrySet()) {
                    String key1 = entry1.getKey();
                    Integer value1 = entry1.getValue();
                    Integer value2 = map.get(key1);
                    if ((int) value1 != anul) {
                        s += "Anul " + anul + ":\n";
                        anul++;
                    }
                    s += " " + key1 + ": " + value2 + "\n";
                }

                txtArea.setText(s);
            }
        });

        btnMedieAn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, Integer> sortedMap= valueSort(cursuri);
                Map<String, Integer> map= valueSort(note);
                ArrayList<Float> medie_an= new ArrayList<>();

                int anul=1,suma=0,numar_cursuri=0;
                String s="";
                for (Map.Entry<String, Integer> entry1 : sortedMap.entrySet()) {
                    String key1 = entry1.getKey();
                    Integer value1 = entry1.getValue();
                    Integer value2 = map.get(key1);
                    suma+=value2;
                    numar_cursuri++;
                    if ((int) value1 != anul) {
                        anul++;
                        Float f= (float) suma / numar_cursuri;
                        medie_an.add(f);
                        suma=0;
                        numar_cursuri=0;
                    }
                }

                Float f= (float) suma / numar_cursuri;
                medie_an.add(f);

                anul=1;
                for (Float media:medie_an){
                    s+=" Anul "+anul+": Media"+ media+"\n";
                }

                txtArea.setText(s);
            }
        });

        btnRestante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtArea.setText("");
                Map<String, Integer> map= valueSort(note);

                int i=0;
                String s="Restante: ";
                for (Map.Entry<String, Integer> entry1 : map.entrySet()) {
                    String key1 = entry1.getKey();
                    Integer value1 = entry1.getValue();
                    if(value1<5)   {
                        i++;
                        s += " " + key1 + " ( nota: " +value1+ ")\n";
                    }
                }
                if(i==0)
                    s+=" nu exista";

                txtArea.setText(s);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}