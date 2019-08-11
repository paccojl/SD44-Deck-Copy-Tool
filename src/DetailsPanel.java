import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;

public class DetailsPanel extends JPanel {

    public DetailsPanel() {
        super();
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        add(new JLabel("select replay"));
    }

    public void fill(Details details){
        removeAll();
        repaint();

        JLabel label = new JLabel("Server name: " + details.servername);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label);
        label = new JLabel("Map: " + details.mapname);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label);
        label = new JLabel("Mode: "+ Constants.gameModes[details.gamemode] );
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label);
        label = new JLabel("Start points: " + details.startmoney);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label);
        label = new JLabel("Income rate: " + details.income);
        label.setAlignmentX(LEFT_ALIGNMENT);
        add(label);
        label = new JLabel("Score limit: " + Constants.scoreLimit.get(details.scorelimit) + "("+details.scorelimit+")");
        label.setAlignmentX(LEFT_ALIGNMENT);
        add(label);
        label = new JLabel("Time limit: " + details.timelimit);
        label.setAlignmentX(LEFT_ALIGNMENT);
        add(label);
        label = new JLabel("Victory: " + Constants.victoryType[details.victory]);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label);
        label = new JLabel("Duration: " + details.duration);
        label.setAlignmentX(LEFT_ALIGNMENT);
        add(label);

        JPanel teamonePane = new JPanel();
        teamonePane.setBorder(new TitledBorder("Team One"));
        teamonePane.setLayout(new BoxLayout(teamonePane,BoxLayout.PAGE_AXIS));
        JPanel teamtwoPane = new JPanel();
        teamtwoPane.setBorder(new TitledBorder("Team Two"));
        teamtwoPane.setLayout(new BoxLayout(teamtwoPane,BoxLayout.PAGE_AXIS));
        add(teamonePane);
        add(teamtwoPane);

        for(Player pl : details.players){
            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.LINE_AXIS));
            playerPanel.setSize(getWidth(),15);


            JButton button = new JButton("Copy deck to clipboard");
            button.setToolTipText(pl.deck);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(pl.deck),null);
                }
            });

            byte[] deckByte;
            deckByte = Base64.getDecoder().decode(pl.deck);
            String deckString = "";
            for(int i = 0; i<deckByte.length;i++){
                deckString += String.format("%8s",Integer.toBinaryString(deckByte[i] & 0XFF)).replace(' ','0');
            }
            int[] header = new int[5];
            for(int i=0;i<5;i++){
                int count = Integer.parseInt(deckString.substring(0,5),2);
                header[i] = Integer.parseInt(deckString.substring(5,5+count),2);
                deckString = deckString.substring(5+count);
            }

            playerPanel.add(new JLabel(pl.nickname));

            playerPanel.add(Box.createHorizontalGlue());
            playerPanel.add(new JLabel(Constants.divisions.get(header[2])+"("+Constants.incomeTypes[header[4]]+")"));
            playerPanel.add(new JLabel());
            playerPanel.add(Box.createRigidArea(new Dimension(10,10)));
            playerPanel.add(button);
            playerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            if(pl.side == 0){
                teamonePane.add(playerPanel);
            } else {
                teamtwoPane.add(playerPanel);
            }

        }

        add(new JLabel("ver: "+ details.ver));

        revalidate();

    }
}
