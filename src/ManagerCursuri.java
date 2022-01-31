import java.util.*;

public class ManagerCursuri implements OperatiiManagerCursuri{
    List<Curs> cursuri;

    public ManagerCursuri()	{
        cursuri = new ArrayList<Curs>();
    }

    public ManagerCursuri(Curs[] cursuri){
        this.cursuri = Arrays.asList(cursuri);
    }

    public void AddCurs(Curs curs) {
        cursuri.add(curs);
    }

    public void setCurs(Curs[] c)    {
        this.cursuri = new ArrayList<>(Arrays.asList(c));
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

    public void MedieCurs(Curs curs)
    {
        Curs c;
        try        {
            c = this.search(curs);
            System.out.println("Media studentilor la cursul: " + c.nume + " este: " + c.media_stud_double());
        }
        catch (Exception e)      {
            System.out.println("Cursul nu a fost gasit ");
        }
    }

    public List<Curs> getCursuri() {
        return cursuri;
    }

    public void AfiseazaCursuriLaConsola()	{
        for(Curs c : cursuri)    {
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

    public void media_notelor_date_de_profesor(Profesor p) {
        int i = 0;
        double medie = 0;
        for(Curs c : this.cursuri)
        {
            if(c.GetProf().equals(p))
            {
                medie += c.media_stud_double();
                i++;
            }
        }
        double media = i == 0 ? 0 : medie/(double) i;
        System.out.println("Media notelor data de profesorul " + p.nume + p.prenume + ": " + media);
    }

    public void DeleteCurs(Curs curs) throws Exception{
        if (!this.cursuri.remove(curs)) {
            throw new Exception("Cursul " + curs.nume + " nu poate fi sters pentru ca nu se regaseste in programa scolara");
        }
    }

    public ArrayList<String> cursuri_profesor(Profesor profesor)
    {
        int i = 1;
        ArrayList<String> list = new ArrayList<>();
        for(Curs c : cursuri)
        {
            if(c.profu.equals(profesor))
            {
                System.out.println(i + ". " + c.nume);
                list.add(c.nume);
                i++;
            }
        }
        return list;
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
                curs.UpdateDescriereCurs(nou);
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

    public ArrayList<String> StudentiCurs(Curs curs) {
        int index = -1, pos = 0;
        for (Curs c : cursuri) {
            index++;
            if (c.compareTo(curs) == 0) {
                pos = index;
            }
        }
        return cursuri.get(pos).studenti_de_la_curs();
    }

    public Curs search(Curs unCurs) throws Exception {
        int i = cursuri.indexOf(unCurs);
        if ( i != -1 ) {
            return cursuri.get(i);
        }
        else {
            throw new Exception("Cursul " + unCurs + " nu se se regaseste in programa scolara");
        }
    }

    public List<Curs> cursuri_student(Student student){
        List<Curs> lista_cursuri= new ArrayList<Curs>();
        for(Curs curs:cursuri)
            if(curs.FindStudent(student)){
                lista_cursuri.add(curs);
            }
        return lista_cursuri;
    }

    public Curs[] getCursuriArray() {
        Curs[] c = new Curs[cursuri.size()];
        c = cursuri.toArray(c);
        return c;
    }

    public String anul_studentului(Student student)
    {
        for(Curs curs : cursuri)   {
            if(curs.FindStudent(student))
            {
                Student[] students = curs.studenti.toArray(new Student[curs.studenti.size()]);
                ArrayList<Student> s = new ArrayList<>(Arrays.asList(students));
                String an = String.valueOf(s.get(s.indexOf(student)).anul);
                return an;
            }
        }
        return null;
    }

    public String[] SerchStudent(Student student)
    {
        String[] line;
        line = new String[cursuri.size()];

        String restanta = "";
        for (Curs c : cursuri)
        {
            if(c.FindStudent(student))
            {
                if(c.note_studenti.get(student) == null)     {
                    restanta = "";
                }
                else
                if(c.note_studenti.get(student) < 5)    {
                    restanta = c.note_studenti.get(student).toString() + " Restanta";
                }
                else
                {
                    restanta = c.note_studenti.get(student).toString();
                }
                line[cursuri.indexOf(c)] = c.nume + ": " + restanta;
            }
        }
        return line;
    }

    public double MediaStudent(Student student)
    {
        double medie = 0;
        int nr = 0;
        for(Curs curs : cursuri)
        {
            if(curs.FindStudent(student))     {
                medie += curs.note_studenti.get(student) != null ? curs.note_studenti.get(student) : 0;
                nr++;
            }
        }
        if(nr!=0)
            return medie/nr;
        else
            return 0;
    }
}