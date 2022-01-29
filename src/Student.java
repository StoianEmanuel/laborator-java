import java.util.*;

public class Student extends Persoana implements Comparator<Student>{
    public int grupa;
    public int anul;

    public Student(String nume, String prenume, int grupa, int anul) {
        this.nume = nume;
        this.prenume = prenume;
        this.grupa = grupa;
        this.anul=anul;
    }

    public Student() {
        this.nume=null;
        this.prenume=null;
        this.grupa=0;
        this.anul=0;
    }

    Student(String nume, String prenume){
        this.nume=nume;
        this.prenume=prenume;
        this.anul=0;
        this.grupa=0;
    }

    Student(ArrayList<String> properties) throws Exception {
        if ( properties.size() != 4 ) {
            throw new Exception("Invalid number of properties! The student cannot be created!");
        } else {
            this.nume = properties.get(0);
            this.prenume = properties.get(1);
            this.grupa = Integer.parseInt(properties.get(2));
            this.anul=Integer.parseInt(properties.get(3));
        }
    }

    @Override
    public String toString() {
        return "Student : {" + "nume= " + nume + ", prenume= " + prenume + ", grupa= " + grupa + ", anul= " + anul + " } ";
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public int getGrupa() {
        return grupa;
    }

    public int getAnul(){
        return anul;
    }

    @Override
    public int hashCode() {
        final int prim=31;
        int rezultat=1;
        rezultat= prim*rezultat +grupa;
        if(nume==null)
            rezultat=rezultat*prim;
        else
            rezultat=rezultat*prim+nume.hashCode();
        if(prenume==null)
            rezultat=rezultat*prim;
        else
            rezultat=rezultat*prim+prenume.hashCode();
        return rezultat;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public int compareTo(Student o){
        if(this.nume.compareTo(o.nume)>0)
            return 1;
        else
        if(this.nume.compareTo(o.nume)<0)
            return -1;
        else
        {
            if(this.prenume.compareTo(o.prenume)>0)
                return 1;
            else
                return -1;
        }
    }

    @Override
    public int compare(Student o1, Student o2){
        if(o1.nume.compareTo(o2.nume)>0)
            return 1;
        else
        if(o1.nume.compareTo(o2.nume)<0)
            return -1;
        else
        {
            if(o1.prenume.compareTo(o2.prenume)>0)
                return 1;
            else
                return -1;
        }
    }

    public boolean CompareByNameAndPrenume(Student student){
        if(this.nume==student.nume && this.prenume==student.prenume)
            return true;
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Student other = (Student) obj;
        if(this.grupa!=other.grupa)
            return false;

        if(this.nume==null) {
            if(other.nume!=null)
                return false;	}
        else
        if(!this.nume.equals(other.nume))
            return false;

        if(this.prenume==null) {
            if(other.prenume!=null)
                return false;	}
        else
        if(!prenume.equals(other.prenume))
            return false;
        return true;
    }
}