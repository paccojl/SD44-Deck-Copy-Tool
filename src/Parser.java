import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {

    static final Pattern mapName = Pattern.compile("_\\dx\\d_(.+)_LD(_(\\dv\\d))?",Pattern.MULTILINE);

    private static String findJSONBlock(String line, int blockStart) throws IOException {
        int i = blockStart;
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for(;i<line.length();i++){
            char c = line.charAt(i);
            if(c =='{') counter++;
            if(c =='}') counter--;
            sb.append(c);
            if(counter==0) break;
        }
        return sb.toString();
    }

    public static Details Parse(File replay) throws ParseException,IOException {

        Details returnDetails = new Details();
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(replay),"UTF8"),5000);

        String line;
        int index;
        do{
            line = buff.readLine();
        } while ((index = line.indexOf("{\"game\":")) == -1);

        JSONObject full = (JSONObject) new JSONParser().parse(findJSONBlock(line,index));

        while ((index = line.indexOf("{\"result\":")) == -1){
            line = buff.readLine();
        }

        JSONObject result = (JSONObject) ((JSONObject) new JSONParser().parse(findJSONBlock(line,index))).get("result");

        JSONObject game = (JSONObject) full.get("game");


        if(game.containsKey("Map")){
            String fullMapname = (String) game.get("Map");
            Matcher m = mapName.matcher(fullMapname);
            if(m.find()){
                returnDetails.mapname = Constants.maps.get(m.group(1))+" "+m.group(3);
            }

        }
        if(game.containsKey("NbMaxPlayer")){
            returnDetails.maxPlayers = Integer.parseInt((String) game.get("NbMaxPlayer"));
        }
        if(game.containsKey("VictoryCond")){
            returnDetails.gamemode = Integer.parseInt((String) game.get("VictoryCond"));
        }
        if(game.containsKey("Version")){
            returnDetails.ver = Integer.parseInt((String) game.get("Version")) %100000;
        }
        if(game.containsKey("ServerName")){
            returnDetails.servername = (String) game.get("ServerName");
        } else
            returnDetails.servername = "";
        if(game.containsKey("InitMoney")){
            returnDetails.startmoney = Integer.parseInt((String) game.get("InitMoney"));
        }
        if(game.containsKey("IncomeRate")){
            returnDetails.income = Constants.incomeRates[Integer.parseInt((String) game.get("IncomeRate"))];
        }
        if(game.containsKey("ScoreLimit")){
            returnDetails.scorelimit = Integer.parseInt((String) game.get("ScoreLimit"));
        }
        if(game.containsKey("TimeLimit")){
            int time = Integer.parseInt((String) game.get("TimeLimit"));
            returnDetails.timelimit = time==0?"No Limit":String.format("%02d:%02d",((time%3600) / 60), (time % 60));
        }

        returnDetails.players = new LinkedList<>();
        for(Object key :  full.keySet().toArray()){
            if( ((String) key).contains("player") ){
                Player pl = new Player();
                JSONObject player = (JSONObject) full.get(key);
                pl.nickname = (String) player.get("PlayerName");
                pl.deck = (String) player.get("PlayerDeckContent");

                if(pl.deck.contains("@")){
                    pl.deck = pl.deck.substring(0,pl.deck.indexOf('@'));
                }

                pl.side = Integer.parseInt((String) player.get("PlayerAlliance"));
                returnDetails.players.add(pl);
            }
        }

        if(result.containsKey("Duration")) {
            int time = Integer.parseInt((String) result.get("Duration"));
            returnDetails.duration = String.format("%02d:%02d:%02d",(time/3600), ((time%3600) / 60), (time % 60));
        }
        if(result.containsKey("Victory")){
            returnDetails.victory = Integer.parseInt((String) result.get("Victory"));
        }



        Collections.sort(returnDetails.players);
        return returnDetails;
    }

}
