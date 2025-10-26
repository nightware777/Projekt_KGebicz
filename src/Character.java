public class Character extends Statistics{
    String characterName;
    String characterElement;
    String weaponType;
    int weaponTypeId;
    int characterId;
    int characterLvl;

    public Character(String characterName, String characterElement, String weaponType, int weaponTypeId, int characterLvl, int characterId, int dmg, int def, int hp){
        this.characterName = characterName;
        this.characterElement = characterElement;
        this.weaponType = weaponType;
        this.weaponTypeId = weaponTypeId;
        this.characterId = characterId;
        this.characterLvl = characterLvl;
        this.dmg = dmg;
        this.def = def;
        this.hp = hp;
    }
}


