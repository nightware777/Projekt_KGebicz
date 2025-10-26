public class Opponent extends Statistics{
    String opponentName;
    String opponentElement;
    int opponentId;
    int opponentLvl;

    public Opponent(String opponentName, String opponentElement, int opponentId, int opponentLvl, int dmg, int def, int hp){
        this.opponentName = opponentName;
        this.opponentElement = opponentElement;
        this.opponentId = opponentId;
        this.opponentLvl = opponentLvl;
        this.dmg = dmg;
        this.def = def;
        this.hp = hp;
    }
}
