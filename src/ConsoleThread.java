import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ConsoleThread extends Thread {
    IDataLoader dataLoader = Settings.dataloader.get(Settings.loadType);

    ArrayList<Student> studenti = new ArrayList<>(Arrays.asList(dataLoader.createStudentsData()));
    ArrayList<Profesor> profesori = new ArrayList<>(Arrays.asList(dataLoader.createProfesorData()));

    @Override
    public void run() {
        try (Scanner cin = new Scanner(System.in)) {
            System.out.println("Username: ");
            String username = cin.nextLine();
            System.out.println("Parola: ");
            String password = cin.nextLine();
            Application.getInstance().login(new User(username, password));
            if (Application.getInstance().currentUser.menuStrategy.getAccountType() == UserAccountType.STUDENT) {
                Application.getInstance().currentUser.menuStrategy.menuOption();
            } else if (Application.getInstance().currentUser.menuStrategy.getAccountType() == UserAccountType.TEACHER) {
                for (String s : Application.getInstance().currentUser.menuStrategy.getAccountHolderInformation().keySet()) {
                    String p = Application.getInstance().currentUser.menuStrategy.getAccountHolderInformation().get(s);
                }
                Application.getInstance().currentUser.menuStrategy.menuOption();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
