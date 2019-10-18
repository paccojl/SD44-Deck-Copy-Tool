import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.prefs.Preferences;

public class Main {


    private ReplaysTableModel replaysTableModel = new ReplaysTableModel();
    private ReplaysTable replaysTable = new ReplaysTable(replaysTableModel);

    private DetailsPanel detailsPanel  = new DetailsPanel(); //Shows current replay info
    private JFrame frame = new JFrame(); //Main frame

    private Details details;//stores current replay info


    public void Run()
    {

        Preferences prefs = Preferences.userRoot().node("sd44 deck copy tool");

        //Check for Steam root folder

        File steamHome = new File(prefs.get("steam home","C:\\Program Files (x86)\\Steam"));
        if(!steamHome.exists() || !steamHome.isDirectory()){
            steamHome = new File("C:\\Program Files\\Steam");
        }
        if(!steamHome.exists() || !steamHome.isDirectory()){
            JOptionPane.showMessageDialog(frame,"C:\\Program Files (x86)\\Steam not found.\nPlease specify Steam root folder.\nIt is needed to read \"cloud\" replays.");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setDialogTitle("Specify Steam root");
            if(fileChooser.showDialog(frame,"OK")== JFileChooser.APPROVE_OPTION){
                steamHome = fileChooser.getSelectedFile();
                prefs.put("steam home",steamHome.toString());//Save steam home in prefs so user dont need to find it again
            }
        }

        java.util.List<File> cloudFolders = new ArrayList<>(2);
        try {
            replaysTableModel.fill(new File(System.getProperty("user.home") + "\\Saved Games\\EugenSystems\\SteelDivision2"),false);
            File userdata = new File(steamHome,"userdata");
            if(userdata.exists() && userdata.isDirectory()){
                for (File currentUserdata : userdata.listFiles()) {
                    File sd2remoteFolder = new File(currentUserdata,"919640\\remote");
                    if(sd2remoteFolder.exists() && sd2remoteFolder.isDirectory()){
                        if(replaysTableModel.fill(sd2remoteFolder,true)){
                            cloudFolders.add(sd2remoteFolder);
                        }
                    }
                }
            }
        } catch (Exception e)
        {
            if(e.getMessage().contains("cloud")){
                prefs.remove("steam home"); //remove steam home from prefs if it isn't correct
            }
            JOptionPane.showMessageDialog(frame,e.getMessage());
        }

        //on click: fill details and show them on detailsPanel
        replaysTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    try {
                        details = Parser.Parse((File) replaysTableModel.getValueAt(replaysTable.getRowSorter().convertRowIndexToModel(replaysTable.getSelectedRow()), 3));
                    } catch (Exception exc)
                    {
                        exc.printStackTrace();
                    }

                    detailsPanel.fill(details);
                }
            }
        });
        JButton openLocalButton = new JButton("Open local folder");
        openLocalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {try {
                    Desktop.getDesktop().open(new File(System.getProperty("user.home") + "\\Saved Games\\EugenSystems\\SteelDivision2"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        List<JButton> cloudButtons = new ArrayList<>(2);
        for (File cloudFolder : cloudFolders) {
            JButton button = new JButton("Open cloud folder");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().open(cloudFolder);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            cloudButtons.add(button);
        }


        //Main frame setup
        frame.setTitle("SD2 Deck Copy Tool");
        frame.setSize(prefs.getInt("window width",1000),prefs.getInt("window height",500));
        frame.setResizable(true);
        frame.setLocation(prefs.getInt("window pos x",100), prefs.getInt("window pos y",300));
        frame.setLayout(new BorderLayout());
        JPanel commandPane = new JPanel();
        commandPane.setLayout(new BoxLayout(commandPane,BoxLayout.LINE_AXIS));
        commandPane.add(openLocalButton);
        for (JButton cloudButton : cloudButtons) {
            commandPane.add(cloudButton);
        }
        commandPane.add(Box.createRigidArea(new Dimension(10,0)));
        commandPane.setBorder(new EmptyBorder(2,5,2,5));
        commandPane.add(Box.createHorizontalGlue());
        frame.add(commandPane, BorderLayout.NORTH);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.0);
        splitPane.add(new JScrollPane(replaysTable));
        splitPane.add(new JScrollPane(detailsPanel));
        frame.add(splitPane);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                //Saving window position and size prefs


                if(frame.getExtendedState() == Frame.NORMAL){
                    prefs.putInt("window pos x",(int) frame.getLocation().getX());
                    prefs.putInt("window pos y",(int) frame.getLocation().getY());
                    prefs.putInt("window height",(int) frame.getSize().getHeight());
                    prefs.putInt("window width",(int) frame.getSize().getWidth());
                }
            }
        });
    }

    public static void main(String args[]) {
        Main main = new Main();
        main.Run();
    }


}
