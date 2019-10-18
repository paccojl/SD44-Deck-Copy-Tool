import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Preston on 10.08.2019.
 */
public class Constants {
    static  Map<Integer,String> divisions;
    static Map<String,String> maps;
    static Map<Integer,String> scoreLimit;
    static String[] incomeTypes = {"Balanced","Vanguard","Maverick","Juggernaut"};
    static String[] incomeRates = {"None","Very Low","Low","Normal","High","Very High"};
    static String[] gameModes = {"N/A","N/A", "Conquest","Closer Combat","N/A","Breakthrough"};
    static String[] victoryType = {"Total Defeat","Major Defeat","Minor Defeat","Draw","Minor Victory","Major Victory","Total Victory"};

    static {
        scoreLimit = new TreeMap<>();
        scoreLimit.put(1000,"Low");
        scoreLimit.put(2000,"Normal");
        scoreLimit.put(4000,"High");

        maps = new TreeMap<>();
        maps.put("Urban_River_Bobr","Bobr");
        maps.put("Ville_Centrale_Haroshaje","Haroshaje");
        maps.put("River_Swamp_Krupa","Krupa");
        maps.put("Lenina","Lenina");
        maps.put("Plateau_Central_Orsha_E","Orsha East");
        maps.put("Proto_levelBuild_Orsha_N","Orsha North");
        maps.put("Ostrowno","Ostrowno");
        maps.put("Shchedrin","Shchedrin");
        maps.put("Lacs_Sianno","Sianno");
        maps.put("Slutsk_E","Slutsk East");
        maps.put("Slutsk_W","Slutsk West");
        maps.put("Slutsk","Slutsk");
        maps.put("Foret_Tsel","Tsel");
        maps.put("Highway","Autobahn Zur Holle");
        maps.put("Beshankovichy","Beshankovichy");
        maps.put("West_Bobrujsk","Bobrujsk West");
        maps.put("Astrouna_Novka","Novka");
        maps.put("Veselovo","Veselovo");
        maps.put("East_Vitebsk","Vitebsk East");
        maps.put("Urban_roads_Krupki","Krupki");
        maps.put("Lipen","Lipen");
        maps.put("Lyakhavichy","Lyakhavichy");
        maps.put("Marecages_Naratch_lake","Naratch Lake");
        maps.put("Rivers_Pleshchenitsy_S","Pleshchenitsy South");
        maps.put("Bridges_Smolyany","Smolyany");
        maps.put("Siedlce", "Siedlce");
        maps.put("Zbuczyn", "Zbuczy");
        maps.put("Vistula_Gora_Kalwaria","Gora Kalwaria");
        maps.put("West_Brest", "Brest West");

        divisions = new TreeMap<>();
        divisions.put(132,"2-ya Gvard. Tankovy Korpus");
        divisions.put(260,"3-ya Gvard. Tankovy Korpus");
        divisions.put(134,"29-ya Tankovy Korpus");
        divisions.put(144,"3-ya Gvard. Mechanizi. Korpus");
        divisions.put(148,"Podv. Gruppa Tyurina");
        divisions.put(147,"Podv. Gruppa Bezuglogo");
        divisions.put(150,"9-ya Gvard. Kavalerii");
        divisions.put(143,"26-ya Gvard. Strelkovy");
        divisions.put(145,"44-ya Gvard. Strelkovy");
        divisions.put(142,"184-ya Strelkovy");
        divisions.put(70,"3rd US Armoured");
        divisions.put(153,"2e Blindée");
        divisions.put(152,"15th Infantry");
        divisions.put(66,"3rd Canadian Infantry");
        divisions.put(71,"5. Panzer");
        divisions.put(135,"20. Panzer");
        divisions.put(155,"21. Panzer");
        divisions.put(154,"116. Panzer");
        divisions.put(157,"Panzer Lehr");
        divisions.put(138,"Gruppe Harteneck");
        divisions.put(259,"1. Skijäger");
        divisions.put(137,"78. Sturm");
        divisions.put(149,"14. Infanterie");
        divisions.put(136,"28. Jäger");
        divisions.put(156,"352. Infantrie");
        divisions.put(139,"Korück 559");
        divisions.put(140,"1. Lovas");
        divisions.put(141,"12. Tartalék");
        divisions.put(270,"84-ya Gvard. Strelkovy");
        divisions.put(269, "25. Panzergrenadier");
        divisions.put(262, "5. SS-Panzer \"Wiking\"");
        divisions.put(263, "Fallschirm-Panzer \"Hermann Göring\"");
        divisions.put(255, "1 Piechoty \"Tadeusza Kościuszki\"");
        divisions.put(261, "Armia Krajowa");
    }
}
