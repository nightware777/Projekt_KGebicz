public class Weapon extends Statistics{
    String weaponName;
    String weaponType;
    int weaponTypeId;
    int weaponId;
    int weaponLvl;

    public Weapon(String weaponName, String weaponType, int weaponTypeId, int weaponId, int weaponLvl, int dmg, int def, int hp){
        this.weaponName = weaponName;
        this.weaponType = weaponType;
        this.weaponTypeId = weaponTypeId;
        this.weaponId = weaponId;
        this.weaponLvl = weaponLvl;
        this.dmg = dmg;
        this.def = def;
        this.hp = hp;
    }
}
