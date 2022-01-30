import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private static Application singleInstance = null;
    private List<User> userList = new ArrayList<>();
    public User currentUser = null;
    public IDataLoader dataLoader = null;
    public IDisplayManager displayManager = null;
    public ManagerCursuri manager = new ManagerCursuri();

    static Application getInstance() {
        if ( singleInstance == null) {
            singleInstance = new Application();
        }
        return  singleInstance;
    }

    public void printUsers(){
        this.initUsers();
        for(User user:userList)
            System.out.println(user.toString());
    }

    private Application() {
        this.initUsers();
    }

    private void initUsers() {
        try (FileInputStream fis = new FileInputStream("src/users.xml")) {
            XMLDecoder decoder = new XMLDecoder(fis);
            this.userList = (ArrayList<User>)decoder.readObject();
            decoder.close();
            fis.close();
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
            Application.getInstance().dataLoader = Settings.dataloader.get(Settings.loadType);
            Application.getInstance().displayManager = Settings.displayloader.get(Settings.displayType);
            Application.getInstance().manager.setCurs(dataLoader.createCoursesData());
        } else {
            throw new Exception("Username-ul sau parola este gresita!");
        }
    }

    public void noi_utilizatori(String username, String password, MenuStrategy menuStrategy)
    {
        this.initUsers();
        userList.add(new User(username,password,menuStrategy));
        try(FileOutputStream fos = new FileOutputStream("src/users.xml")) {
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.writeObject(userList);
            encoder.close();
            fos.close();
        }
        catch (FileNotFoundException e)    {
            e.printStackTrace();
        }
        catch (IOException e)    {
            e.printStackTrace();
        }
    }
}