import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.*;

public class Main {


    private ReplaysTableModel replaysTableModel = new ReplaysTableModel();
    private ReplaysTable replaysTable = new ReplaysTable(replaysTableModel);

    private DetailsPanel detailsPanel  = new DetailsPanel(); //Shows current replay info
    private JFrame frame = new JFrame(); //Main frame

    private Details details;//stores current replay info


    public void Run()
    {
        //Check for Steam root folder
        File steamHome = new File("C:\\Program Files (x86)\\Steam");
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
            }
        }
        try {
            replaysTableModel.fill(steamHome); //find replays
        } catch (Exception e)
        {
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

        //Main frame setup
        frame.setTitle("SD44 Deck Copy Tool");
        frame.setSize(1000, 500);
        frame.setResizable(true);
        frame.setLocation(100, 300);
        frame.setLayout(new GridLayout());
        frame.add(new JScrollPane(replaysTable));
        frame.add(detailsPanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String args[]) {
        Main main = new Main();
        main.Run();
    }


}
