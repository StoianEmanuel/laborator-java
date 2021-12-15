import java.util.*;

public class Curs implements OperatiiCurs{
    String nume;
    String descriere;
    Profesor profu;
    Set<Student> studenti;
    HashMap<Student,Integer> note_studenti;
    HashMap<Integer, ArrayList<Student>> grupe_studenti;

    public Curs(String nume, String descriere, Profesor profu, Set<Student> stud, Integer[] note) {
        this.nume = nume;
        this.descriere = descriere;
        this.profu = profu;
        this.studenti = stud;

        this.note_studenti = new HashMap<Student, Integer>();
        this.grupe_studenti = new HashMap<Integer,ArrayList<Student>>();
        ArrayList<Integer> grupe=new ArrayList<Integer>();
        int i = 0;
        if(note.length<=studenti.size()) {
            for (Student s : stud) {
                if (i < note.length)
                    note_studenti.put(s, note[i++]);
                else
                    note_studenti.put(s, 0);

                if (grupe.contains(s.getGrupa())) {
                    ArrayList<Student> studentii_grupei= new ArrayList<>();
                    studentii_grupei = grupe_studenti.get(s.getGrupa());
                    studentii_grupei.add(s);
                    //Collections.sort(studentii_grupei, Student::compareTo);
                    Comparator<Student> compara= Comparator.comparing(Student::getNume).thenComparing(Student::getPrenume).thenComparing(Student::getGrupa);
                    Collections.sort(studentii_grupei,compara);
                    grupe_studenti.replace(s.getGrupa(),studentii_grupei);
                }
                else{
                    ArrayList<Student> studentii_noii_grupe= new ArrayList<Student>();
                    studentii_noii_grupe.add(s);
                    grupe_studenti.put(s.getGrupa(),studentii_noii_grupe);
                }
            }
        }
    }

    public Curs(String nume, String descriere, Profesor profu, Set<Student> stud) {
        this.nume = nume;
        this.descriere = descriere;
        this.profu = profu;
        this.studenti = stud;

        this.note_studenti = new HashMap<Student, Integer>();
        this.grupe_studenti = new HashMap<Integer,ArrayList<Student>>();
        ArrayList<Integer> grupe=new ArrayList<Integer>();

        //pun nota 0 tuturor studentilor
        for (Student s : stud) {
            note_studenti.put(s, 0);
            if (grupe.contains(s.getGrupa())) {
                    ArrayList<Student> studentii_grupei= new ArrayList<>();
                    studentii_grupei = grupe_studenti.get(s.getGrupa());
                    studentii_grupei.add(s);
                    //Collections.sort(studentii_grupei, Student::compareTo);
                    Comparator<Student> compara= Comparator.comparing(Student::getNume).thenComparing(Student::getPrenume).thenComparing(Student::getGrupa);
                    Collections.sort(studentii_grupei,compara);
                    grupe_studenti.replace(s.getGrupa(),studentii_grupei);
                }
                else{
                    ArrayList<Student> studentii_noii_grupe= new ArrayList<Student>();
                    studentii_noii_grupe.add(s);
                    grupe_studenti.put(s.getGrupa(),studentii_noii_grupe);
                }
            }
    }

    public Curs() {
        this.nume=null;
        this.descriere=null;
        this.profu=null;
        this.studenti= null;
        this.note_studenti=new HashMap<Student,Integer>();
        this.grupe_studenti= new HashMap<>();
    }

    @Override
    public void UpdateProfesor(Profesor profu) {
        this.profu = profu;
    }

    @Override
    public void AddStudent(Student student)	{
        this.studenti.add(student);
        note_studenti.put(student,0);
        ArrayList<Student> studentii_grupei= new ArrayList<>();
        studentii_grupei=grupe_studenti.get(student.getGrupa());
        studentii_grupei.add(student);
        Comparator<Student> compara= Comparator.comparing(Student::getNume).thenComparing(Student::getPrenume).thenComparing(Student::getGrupa);
        Collections.sort(studentii_grupei,compara);
        grupe_studenti.	replace(student.getGrupa(),studentii_grupei);
    }

    @Override
    public void UpdateStudent(Student student_v, Student student_n)	{
        if(numar_studenti()!=0)
        {
            Set<Student> aux = new HashSet<Student>();
            for(Student student:studenti)
            {
                if(student==student_v)
                    aux.add(student_n);
                else
                    aux.add(student);
            }
            this.studenti= aux;
            Integer nota=note_studenti.get(student_v);
            note_studenti.remove(student_v,nota);
            note_studenti.put(student_n,nota);

            if(student_n.grupa!=student_v.grupa) {
                ArrayList<Student> studentii_grupei= new ArrayList<>();// sterg studentul din fosta grupa din care facea parte
                studentii_grupei=grupe_studenti.get(student_v.getGrupa());
                studentii_grupei.remove(student_v);
                grupe_studenti.replace(student_v.getGrupa(),studentii_grupei);

                studentii_grupei=new ArrayList<>(); //adaug studentul in noua sa grupa si sortez grupa
                studentii_grupei=grupe_studenti.get(student_n.getGrupa()); //preiau studentii din grupa
                studentii_grupei.add(student_n);
                Comparator<Student> compara= Comparator.comparing(Student::getNume).thenComparing(Student::getPrenume).thenComparing(Student::getGrupa);
                Collections.sort(studentii_grupei,compara);
                grupe_studenti.	replace(student_n.getGrupa(),studentii_grupei);
            }
        }
    }

    @Override
    public void UpdateCurs(String nume, String descriere) {
        this.nume=nume;
        this.descriere=descriere;
    }

    @Override
    public void RemoveStudent(Student student){
        if(numar_studenti()!=0)		{
            studenti.remove(student);
            note_studenti.remove(student);
            ArrayList<Student> studentii_grupei= new ArrayList<>();// sterg studentul din grupa din care facea parte
            studentii_grupei=grupe_studenti.get(student.getGrupa());
            studentii_grupei.remove(student);
            grupe_studenti.replace(student.getGrupa(),studentii_grupei);
        }
    }

    public int numar_studenti() {
        return studenti.size();
    }

    public void DeleteProf() {
        this.profu=null;
    }

    public void AddProf(Profesor profu) {
        this.profu=profu;
    }

    public void PrintStudAndNote() {
        System.out.println("Notele studentilor la cursul "+ nume+" :\n");
        for(Student student: note_studenti.keySet())
            System.out.println(student.getNume()+" "+student.getPrenume()+ " -- Nota: "+ note_studenti.get(student));
    }

    public void PrintStud() {
        System.out.println("Studentii inscrisi la cursul de " + nume +" :\n");
        for(Student student:studenti)
            System.out.println(" "+ student);
    }

    @Override
    public String toString() {
        String str = "Curs : " + "nume = " + nume + ", descriere = " + descriere + ",\nprofu = " + profu +",\nstudenti:\n" ;
        for(Student s : studenti)
            str += s + "\n";
        return str;
    }

    public void UpdateNumeCurs(String nume)	{
        this.nume=nume;
    }

    public void UpdateDescrCurs(String nume) {
        this.descriere=nume;
    }

    public void AddNotaToStud(Student student, Integer nota)	{
        if(numar_studenti()!=0)	{
            if(note_studenti.containsKey(student))
                note_studenti.replace(student, nota);
        }
    }

    public double media_stud_double() {
        return (double)suma_note_stud()/numar_studenti();
    }

    public void print_media_stud() {
        System.out.println("Media notelor studentilor de la cursul de "+ nume+ "este "+ media_stud_double());
    }

    public int suma_note_stud() {
        int suma=0;
        for(Integer i:note_studenti.values())
            suma+=i;
        return suma;
    }

    public void PrintStudPeGrupe(){
        System.out.println("Lista cu grupele studentilor de la cursul de "+ nume);
        for(Integer g: grupe_studenti.keySet())
            System.out.println("Grupa " +g+": "+ grupe_studenti.get(g));
    }

    public Profesor GetProf() {	return profu;	}
    public String GetNumeProf() { return  profu.GetNume(); }
    public String GetNume(){ return nume; }
}
