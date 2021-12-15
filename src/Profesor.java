public class Profesor extends Persoana{

    @Override
    public String toString() {
        return "Profesor : {" + "nume=" + nume + ", prenume=" + prenume + '}';
    }

    public Profesor(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
    }

    public Profesor() {
        this.nume=null;
        this.prenume=null;
    }

    public String formatForDisplay() {
        return this.nume + " " + this.prenume;
    }

    public String GetNume() {
        return nume;
    }

    public String GetPrenume() {
        return prenume;
    }
}