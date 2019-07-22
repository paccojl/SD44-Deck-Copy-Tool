import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;


public class Parser {

    public static Details Parse(File replay) throws ParseException,IOException {

        Details returnDetails = new Details();
        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(replay),"UTF8"),5000);
        String firstBlock;
        String secondBlock;
        String str="";
        try {
            while (!str.contains("{\"game\":{\""))
                str = buff.readLine();
            firstBlock = str.substring(str.indexOf("{\"game\":{\""),str.indexOf("}}")+2);
            while (!str.contains("{\"result\":"))
                str = buff.readLine();
            secondBlock = str.substring(str.indexOf("{\"result\":{"),str.indexOf("}}")+2);
        } catch (IOException e){
            throw e;
        } finally {
            buff.close();
        }

        JSONObject full = (JSONObject) new JSONParser().parse(firstBlock);
        JSONObject game = (JSONObject) full.get("game");

        JSONObject result = (JSONObject) new JSONParser().parse(secondBlock);
        result = (JSONObject) result.get("result");

        if(game.containsKey("Map")){
            returnDetails.mapname = (String) game.get("Map");
        }
        if(game.containsKey("NbMaxPlayer")){
            returnDetails.maxPlayers = Integer.parseInt((String) game.get("NbMaxPlayer"));
        }
        if(game.containsKey("GameMode")){
            returnDetails.gamemode = Integer.parseInt((String) game.get("GameMode"));
        }
        if(game.containsKey("Version")){
            returnDetails.ver = Integer.parseInt((String) game.get("Version")) %100000;
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

        if(result.containsKey("Score")){
            returnDetails.score = Integer.parseInt((String) result.get("Score"));
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
