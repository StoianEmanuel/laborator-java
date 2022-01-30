import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class StudentDisplay {
    private Curs[] students;
    private TreeMap<String,Integer> cursuri;
    private TreeMap<String,Integer> note;
    public Student student;
    private User user;

    StudentDisplay(User user){
        this.user=user;
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
    }

    public void printcursuri(){
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
        System.out.println(s);
    }

    public void printnote(){
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
        System.out.println(s);
    }

    public void printmedie(){
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
            anul++;
        }
    }

    public void printrestante(){
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
    }

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

}
