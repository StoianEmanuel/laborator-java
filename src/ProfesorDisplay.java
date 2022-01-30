import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ProfesorDisplay {
    public Profesor profesor;
    private User user;
    private ArrayList<Curs> cursuri_profesor;
    private ArrayList<Curs> toate_cursurile;

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

    ProfesorDisplay(User user){
        this.user=user;
        TeacherStrategy menuStrategy= (TeacherStrategy) user.menuStrategy;
        Profesor profesor=menuStrategy.returnProfesor();

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
    }

    public void displaycursuri(){
        String s="";
        for(Curs c:cursuri_profesor){
            s+=" "+c.nume+", descrierea cursului: "+c.descriere+" anul"+ c.an+ "\nStudenti: ";
            int i=0;
            for(Student student:c.studenti){
                s+=student.toString();
                i++;
                if(i%2==0 && i!=0)
                    s+="\n";
            }
        }
        System.out.println(s);
    }

    public void noteaza_student(String nume_curs, String nume, String prenume, int nota){
        int index=0;
        for(;index<toate_cursurile.size();index++){
            Curs c=toate_cursurile.get(index);
            if(c.nume==nume_curs){
                if(c.ReturnStudent(nume,prenume)!=null){
                    for(Student s:c.studenti)
                        if(s.nume==nume && s.prenume==prenume)
                            if(c.note_studenti.get(s)==0) {
                                c.note_studenti.replace(s,nota);
                                toate_cursurile.set(index,c);
                                noteaza_student(s, Integer.toString(nota));
                            }
                }
            }
        }
    }
}
