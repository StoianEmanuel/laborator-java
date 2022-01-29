import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private static Application single_instance = null;
    private List<User> userList = new ArrayList<>();
    public User currentUser = null;

    static Application getInstance() {
        if ( single_instance == null) {
            single_instance = new Application();
        }
        return  single_instance;
    }

    public void printUsers(){
        this.initUsers();
        for(User user:userList)
            System.out.println(user.toString());
    }

    private Application() {
         /* HardcodatDataManager dataManager = new HardcodatDataManager();
        var studenti = dataManager.dataSetOfStudent;
        var profesori = dataManager.dataSetOfProfesor;
        this.userList.add(new User("aaa", "aaa", new StudentStrategy( studenti[r.nextInt(studenti.length)])));
        this.userList.add(new User("bbb", "aaa", new TeacherStrategy( profesori[r.nextInt(profesori.length)])));
        this.userList.add(new User("ccc", "ccc", new StudentStrategy( studenti[r.nextInt(studenti.length)])));
        this.userList.add(new User("ddd", "ddd", new TeacherStrategy( profesori[r.nextInt(profesori.length)])));
        this.userList.add(new User("eee", "eee", new StudentStrategy( studenti[r.nextInt(studenti.length)])));
        try {
            FileOutputStream fos = new FileOutputStream("users.xml");
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.setExceptionListener(new ExceptionListener() {
                @Override
                public void exceptionThrown(Exception e) {
                    System.out.println("Exception:" + e.toString());
                }
            });
            encoder.writeObject(userList);
            encoder.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } */
        this.initUsers();
    }

    private void initUsers() {
        try (FileInputStream fis = new FileInputStream("src/users.xml")) {
            XMLDecoder decoder = new XMLDecoder(fis);
            this.userList = (ArrayList<User>)decoder.readObject();
            decoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(User user) throws Exception {
        int index = userList.indexOf(user);
        if ( index != -1 ) {
            Application.getInstance().currentUser = userList.get(index);
        } else {
            throw new Exception("Username sau parola este gresita!");
        }
    }
}