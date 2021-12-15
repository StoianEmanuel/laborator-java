import java.util.*;

public class ManagerCursuri implements OperatiiManagerCursuri{
    List<Curs> cursuri;

    public ManagerCursuri()	{
        cursuri = new ArrayList<Curs>();
    }

    public void AddCurs(Curs curs) {
        cursuri.add(curs);
    }

    public void UpdateCurs(Curs curs_v, Curs curs_n) {
        if(cursuri.remove(curs_v))	{
            cursuri.add(curs_n);
            sort_cursuri_dupa_nume();
        }
    }

    public void AfiseazaCursuriLaConsola()	{
        for(Curs c : cursuri)
            System.out.println(c);
    }

    public void DeleteCurs(Curs curs) {
        cursuri.remove(curs);
        sort_cursuri_dupa_nume();
    }

    public void UpdateProf(Profesor vechi, Profesor nou) {
        for(int i=0;i<numar_cursuri();i++)
            if(cursuri.get(i).profu==vechi)	{
                Curs curs=cursuri.get(i);
                curs.UpdateProfesor(nou);
                cursuri.set(i, curs);
            }
        sort_cursuri_dupa_nume();
    }

    public void UpdateNumeleCursului(String vechi, String nou) {
        for(int i=0;i<numar_cursuri();i++)
            if(cursuri.get(i).nume==vechi)	{
                Curs curs=cursuri.get(i);
                curs.UpdateNumeCurs(nou);
                cursuri.set(i, curs);
            }
        sort_cursuri_dupa_nume();
    }

    public void UpdateDescriereaCursului(String vechi, String nou) {
        for(int i=0;i<numar_cursuri();i++)
            if(cursuri.get(i).descriere==vechi)	{
                Curs curs=cursuri.get(i);
                curs.UpdateDescrCurs(nou);
                cursuri.set(i, curs);
            }
    }

    public void UpdateStudenti(Curs curs, Set<Student> studenti, Set<Student> studenti_modificati) {
        for(int i=0;i<numar_cursuri();i++)	{
            if(cursuri.get(i)==curs)  {
                if(studenti_modificati.size()<cursuri.get(i).numar_studenti())	{
                    Curs C=curs;
                    List<Student> stud = new ArrayList<Student>();
                    List<Student> stud_mod= new ArrayList<Student>();
                    stud.addAll(studenti);
                    stud_mod.addAll(studenti_modificati);
                    for(int j=0;j<stud_mod.size();j++)
                        C.UpdateStudent(stud.get(j), stud_mod.get(j));
                    cursuri.set(i, C);
                }
            }
        }
    }

    public void DeleteStudenti(Curs curs, Student []stersi) {
        for(int i=0;i<numar_cursuri();i++)
            if(cursuri.get(i)==curs) {
                if(stersi.length<cursuri.get(i).numar_studenti())	{
                    Curs C=cursuri.get(i);
                    for(int j=0;j<stersi.length;j++)
                        C.RemoveStudent(stersi[j]);
                    cursuri.set(i, C);
                }
            }
        sort_cursuri_dupa_nume();
    }

    public void AddStud(Student stud, Curs []lista_cursuri){
        for(int j=0;j<numar_cursuri();j++)	{
            int ok=0;
            Curs C=cursuri.get(j);
            for(int i=0;i<lista_cursuri.length && ok==0;i++)
                if(C==lista_cursuri[i])	{
                    C.AddStudent(stud);
                    ok=1;
                }
            if(ok==1)
                cursuri.set(j, C);
        }
        sort_cursuri_dupa_nume();
    }

    public void PrintStudentiCurs(Curs []lista_cursuri) {
        for(Curs c:lista_cursuri) {
            for(Curs curs: cursuri)
                if(c==curs)
                    c.PrintStud();
        }
    }

    public void AddNoteToStud(Curs curs, Set<Student> studenti, Integer []note) {
        for(int j=0;j<cursuri.size();j++) {
            if(cursuri.get(j)==curs)
                if(studenti.size()<=cursuri.get(j).numar_studenti()) {
                    Curs C=curs;
                    int i=0;
                    for(Student stud:studenti)
                        C.AddNotaToStud(stud, note[i++]);
                    cursuri.set(j, C);
                }
        }
    }

    public void RaportNote() {
        for(Curs curs:cursuri)
            curs.PrintStudAndNote();
    }

    public void RaportMediaNotelorCursuri() {
        for(Curs curs:cursuri)
            curs.print_media_stud();
    }

    public int NumarCursuri_Profesor(Profesor profesor) {
        int i=0;
        for(Curs c:cursuri)
            if(c.GetProf()==profesor)
                i++;
        return i;
    }

    public void RaportMediaProfesor(Profesor profesor) {
        System.out.println("Media notelor date de profesorul " + profesor.GetNume() + " " + profesor.GetPrenume()+ ": "+ RaportMediaProfesor_double(profesor));
    }

    public double RaportMediaProfesor_double(Profesor profesor) {
        int suma_note_cursuri=0, numar_studenti=0;
        for(Curs curs:cursuri)
            if(curs.GetProf()==profesor) {
                suma_note_cursuri += curs.suma_note_stud();
                numar_studenti += curs.numar_studenti();
            }
        return (double)suma_note_cursuri/numar_studenti;
    }

    public int numar_cursuri() 	{
        return cursuri.size();
    }

    public void sort_cursuri_dupa_nume(){
        //prioritate :1 nume curs, 2 nume profesor, 3 numarul de studenti
        Comparator<Curs> compara = Comparator.comparing(Curs::GetNume).thenComparing(Curs::GetNumeProf).thenComparing(Curs::numar_studenti);
        Collections.sort(cursuri,compara);
    }

    public void sort_cursuri_dupa_nume_prof(){
        //prioritate :1 nume profesor, 2 nume curs, 3 numarul de studenti
        Comparator<Curs> compara = Comparator.comparing(Curs::GetNumeProf).thenComparing(Curs::GetNume).thenComparing(Curs::numar_studenti);
        Collections.sort(cursuri,compara);
    }

    public void sort_cursuri_dupa_nr_stud(){
        //prioritate :1 nr studenti, 2 nume curs, 3 nume profesor
        Comparator<Curs> compara = Comparator.comparing(Curs::numar_studenti).thenComparing(Curs::GetNume).thenComparing(Curs::GetNumeProf);
        Collections.sort(cursuri,compara);
    }
}