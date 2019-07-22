import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.Date;

public class ReplaysTableModel extends DefaultTableModel {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0 : return String.class;
            case 1 : return Date.class;
            case 2 : return Boolean.class;
            default: return File.class;
        }

    }

    public ReplaysTableModel() {
        super(new String[]{"ReplayName","Date","Cloud",""},0);
    }

    /**
     * Filling table with replays
     */
    public void fill (File steamHome) throws Exception{

        String path = System.getProperty("user.home") + "\\Saved Games\\EugenSystems\\SteelDivision2";
        File localReplayFolder = new File(path);

        //Scan "local" replays
        if(localReplayFolder.exists() && localReplayFolder.isDirectory())
        {
            for (File replay: localReplayFolder.listFiles()
                    ) {
                if(replay.isFile() && replay.canRead() && replay.getName().contains(".rpl3"))
                    addRow(new Object[]{replay.getName(),new Date(replay.lastModified()),false,replay});
            }
        } else {
            throw new Exception("Local replays not found");
        }

        //Scanning "cloud" replays

        File userDataFolder = new File(steamHome,"userdata");

        //iterate every steam user(usually one user)
        if(userDataFolder.exists() && userDataFolder.isDirectory())
        {
            for (File cloudReplayFolder : userDataFolder.listFiles()) {
                if(cloudReplayFolder.isDirectory()){

                    cloudReplayFolder = new File(cloudReplayFolder,"1036860\\remote");
                    if(cloudReplayFolder.isDirectory() && cloudReplayFolder.exists()){
                        for (File replay: cloudReplayFolder.listFiles()) {
                            if(replay.isFile() && replay.canRead() && replay.getName().contains(".rpl3"))
                                addRow(new Object[]{replay.getName(),new Date(replay.lastModified()),true,replay});
                        }
                    } else {
                        throw new Exception("SD44 cloud data not found.\n\"cloud\" replays will not be availible");
                    }

                }
            }
        } else {
            throw new Exception("Steam userdata folder not found.\n\"cloud\" replays will not be availible");
        }




    }

}
