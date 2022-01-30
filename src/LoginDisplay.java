import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class LoginDisplay {
    private ArrayList<User> userList;

    void update_userList() {
        try (FileInputStream fis = new FileInputStream("src/users.xml")) {
            XMLDecoder decoder = new XMLDecoder(fis);
            this.userList = (ArrayList<User>) decoder.readObject();
            decoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    User return_user(String username, String password){
        for(User user:userList)
            if(user.userName==username && user.password==password)
                return user;
        return null;
    }

    private UserAccountType userAccountType;

    public LoginDisplay(String username, String password) {
        this.update_userList();
        try {
            Application.getInstance().login(new User(username, password));
            System.out.println("Login successfully!");

            User user = return_user(username, password);

            if (user.menuStrategy.getClass().getName() == "TeacherStrategy") {
                userAccountType=UserAccountType.TEACHER;
            }
            else {
                userAccountType=UserAccountType.STUDENT;
            }

        } catch (Exception ex) {
            System.out.println("Login failed! " + ex.getMessage());
        }
    }
}
