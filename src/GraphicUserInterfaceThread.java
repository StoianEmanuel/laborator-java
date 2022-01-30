import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GraphicUserInterfaceThread extends Thread{
    IDataLoader dataLoader = Settings.dataloader.get(Settings.loadType);
    ArrayList<Student> students = new ArrayList<>(Arrays.asList(dataLoader.createStudentsData()));
    ArrayList<Profesor> teachers = new ArrayList<>(Arrays.asList(dataLoader.createProfesorData()));
    JFrame frame = new JFrame("Main Page");

    @Override
    public void run() {
        frame.setContentPane(new WelcomeFormGUI(frame,students,teachers).getMainPanel());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
