import java.util.ArrayList;

public class AvailableWeaponsList {
    public static ArrayList<Weapon> weapons = new ArrayList<>();

    //LISTA POCZĄTKOWYCH BRONI
    static{
        weapons.add(new Weapon("Hunter's Path", "Bow", 1, 5,  1, 50, 2, 10));
        weapons.add(new Weapon("Aquila Favonia", "Sword", 5, 40, 1, 50, 2, 10));
        weapons.add(new Weapon("Jadefall's Splendor", "Catalyst", 2, 17, 1, 50, 2, 10));
        weapons.add(new Weapon("Wolf's Gravestone", "Claymore", 3, 20, 1, 50, 2, 10));
        weapons.add(new Weapon("Staff of Homa", "Polearm", 4, 30, 1, 50, 2, 10));
    }
}

//weaponTypeId
// Bow = 1
// Catalyst = 2
// Claymore = 3
// Polearm = 4
// Sword = 5