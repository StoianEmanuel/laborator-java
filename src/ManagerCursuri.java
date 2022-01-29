import java.util.*;

public class ManagerCursuri implements OperatiiManagerCursuri{
    List<Curs> cursuri;

    public ManagerCursuri()	{
        cursuri = new ArrayList<Curs>();
    }

    public void AddCurs(Curs curs) {
        cursuri.add(curs);
    }

    public void UpdateCurs (Curs curs_v, Curs curs_n) throws Exception{
        if (!this.cursuri.remove(curs_v)) {
            throw new Exception("Cursul " + curs_v + " nu poate fi sters pentru ca nu se regaseste in programa scolara");
        }
        else
        {
            cursuri.remove(curs_v);
            cursuri.add(curs_n);
            sort_cursuri_dupa_nume();
        }
    }

    public void AfiseazaCursuriLaConsola()	{
        for(Curs c : cursuri)
        {
            System.out.println(c.nume + "  "+c.descriere+":\n");
            try{
                c.PrintStud();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println();
        }
    }

    public void DeleteCurs(Curs curs) throws Exception{
        if (!this.cursuri.remove(curs)) {
            throw new Exception("Cursul " + curs.nume + " nu poate fi sters pentru ca nu se regaseste in programa scolara");
        }
    }

    public void UpdateProf(Profesor vechi, Profesor nou) {
        for(int i=0;i<numar_cursuri();i++)
            if(cursuri.get(i).profu==vechi)	{
                Curs curs=cursuri.get(i);
                curs.UpdateProfesor(nou);
                cursuri.set(i, curs);
            }
    }

    public void UpdateNume_Curs(String vechi, String nou) {
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
        try {
            if (search(curs) == curs) {
                if (studenti_modificati.size() < curs.numar_studenti()) {
                    Curs C = curs;
                    List<Student> stud = new ArrayList<Student>();
                    List<Student> stud_mod = new ArrayList<Student>();
                    stud.addAll(studenti);
                    stud_mod.addAll(studenti_modificati);
                    for (int j = 0; j < stud_mod.size(); j++)
                        C.UpdateStudent(stud.get(j), stud_mod.get(j));
                    cursuri.remove(curs);
                    cursuri.add(C);
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void DeleteStudenti(Curs curs, Student []stersi) {
        try {
            if (search(curs)==curs) {
                if (stersi.length < curs.numar_studenti()) {
                    Curs C = curs;
                    for (int j = 0; j < stersi.length; j++)
                        C.RemoveStudent(stersi[j]);
                    cursuri.remove(curs);
                    cursuri.add(C);
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void AddStud(Student stud, Curs curs) {
        try {
            if (search(curs) == curs) {
                Curs C = curs;
                C.AddStudent(stud);
                cursuri.remove(curs);
                cursuri.add(C);
                sort_cursuri_dupa_nume();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void PrintStudentiCurs(Curs curs) throws Exception {
        Curs c = this.search(curs);
        c.PrintStud();
    }

    public void AddNoteToStud(Curs curs, Set<Student> studenti, Integer []note) {
            try {
                if (search(curs) == curs) {
                    if (studenti.size() <= curs.numar_studenti()) {
                        Curs C = curs;
                        int i = 0;
                        for (Student stud : studenti)
                            C.AddNotaToStud(stud, note[i++]);
                        cursuri.remove(curs);
                        cursuri.add(C);
                        sort_cursuri_dupa_nume();
                    }
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
    }

    public void RaportNoteCurs(Curs curs) {
        try {
            if (search(curs) == curs) {
                System.out.println("Notele studentilor la cursul " + curs.nume + ":\n");
                curs.PrintStudAndNote();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void RaportNoteCursuri(){
        for(Curs c:cursuri)
            RaportNoteCurs(c);
    }

    public void RaportMediaNoteCurs(Curs curs) {
        try{
            if(search(curs)==curs){
                System.out.print("\nMedia notelor studenilor la cursul "+curs.nume+": "+curs.media_stud_double());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void RaportMediaNoteCursuri(){
        for(Curs c:cursuri)
            RaportMediaNoteCurs(c);
    }

    public int NumarCursuri_Profesor(Profesor profesor) {
        int i=0;
        for(Curs c:cursuri)
            if(c.GetProf()==profesor)
                i++;
        return i;
    }

    public void MediaProfesor(Profesor profesor) {
        double suma=0;
        int nr=0;
        for(Curs curs:cursuri)
            if(curs.GetProf()==profesor) {
                suma+=curs.media_stud_double();
                nr++;
            }
        double media = nr == 0 ? 0 : suma / (double)nr;
        System.out.println("Mediat notelor date de profesorul " + profesor.formatForDisplay() + " este: " + media );
    }

    public int numar_cursuri() 	{
        return cursuri.size();
    }

    public void sort_cursuri_dupa_nume(){
        //prioritate :1 nume curs, 2 nume profesor, 3 numarul de studenti
        Comparator<Curs> compara = Comparator.comparing(Curs::GetNume).thenComparing(Curs::GetNumeProf).thenComparing(Curs::numar_studenti);
        Collections.sort(cursuri,compara);
    }

    public void sort_cursuri_dupa_prof(){
        //prioritate :1 nume profesor, 2 nume curs, 3 numarul de studenti
        Comparator<Curs> compara = Comparator.comparing(Curs::GetNumeProf).thenComparing(Curs::GetNume).thenComparing(Curs::numar_studenti);
        Collections.sort(cursuri,compara);
    }

    public void sort_cursuri_dupa_nr_stud(){
        //prioritate :1 nr studenti, 2 nume curs, 3 nume profesor
        Comparator<Curs> compara = Comparator.comparing(Curs::numar_studenti).thenComparing(Curs::GetNume).thenComparing(Curs::GetNumeProf);
        Collections.sort(cursuri,compara);
    }

    private Curs search(Curs unCurs) throws Exception {
        int i = cursuri.indexOf(unCurs);
        if ( i != -1 ) {
            return cursuri.get(i);
        }
        else {
            throw new Exception("Cursul " + unCurs + " nu se se regaseste in programa scolara");
        }
    }

    public List<Curs> getCursuri_for_a_student(Student student){
        List<Curs> lista_cursuri= new ArrayList<Curs>();
        for(Curs curs:cursuri)
            if(curs.FindStudent(student)){
                lista_cursuri.add(curs);
            }
        return lista_cursuri;
    }
}