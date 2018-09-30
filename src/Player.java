
public class Player implements Comparable<Player>{
        String nickname;
        String deck;
        Integer side;
        Integer division;

    @Override
    public int compareTo(Player o) {
        return side - o.side;
    }
}


