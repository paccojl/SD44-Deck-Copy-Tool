import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
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

        add(new JLabel("ver: "+ details.ver));

        if(details.gamemode >=3)
        {
            add(new JLabel("Not multiplayer replay"));
            revalidate();
            return;
        }


        JLabel label = new JLabel("Map: "+details.mapname);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label);
        label = new JLabel("Mode: "+ details.gamemode );
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
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
            deckByte = Base64.getDecoder().decode(pl.deck.charAt(0)=='*'?pl.deck.substring(1,4):pl.deck.substring(0,3));
            String deckString ;
            deckString = String.format("%8s",Integer.toBinaryString(deckByte[0] & 0XFF)).replace(' ','0');
            deckString += String.format("%8s",Integer.toBinaryString(deckByte[1] & 0XFF)).replace(' ','0');
            if(pl.deck.charAt(0) == '*'){
                pl.division = Integer.parseInt(deckString.substring(0,10),2);
            } else
            {
                pl.division = Integer.parseInt(deckString.substring(0,8),2);
            }




            playerPanel.add(new JLabel(pl.nickname));

            playerPanel.add(Box.createHorizontalGlue());
            try {
                URL image = getClass().getResource("/res/"+ pl.division.toString() +".png");
                JLabel divIcon = new JLabel(new ImageIcon(ImageIO.read(image)));
                divIcon.setToolTipText(pl.division.toString());
                playerPanel.add(divIcon);
            } catch (Exception ex){
                ex.printStackTrace();

            }
            playerPanel.add(Box.createRigidArea(new Dimension(10,10)));
            playerPanel.add(button);
            playerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            if(pl.side == 0){
                teamonePane.add(playerPanel);
            } else {
                teamtwoPane.add(playerPanel);
            }

        }
        revalidate();

    }
}
