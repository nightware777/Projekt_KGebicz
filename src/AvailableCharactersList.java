import java.util.ArrayList;

public class AvailableCharactersList {
    public static ArrayList<Character> characters = new ArrayList<>();

    //LISTA POCZĄTKOWYCH POSTACI
    static {
        characters.add(new Character("Amber", "Pyro", "Bow", 1, 1, 2, 100, 10, 1000));
        characters.add(new Character("Kaeya", "Cryo", "Sword", 5, 1, 70, 100, 10, 1000));
        characters.add(new Character("Lisa", "Electro", "Catalyst", 2, 1, 82, 100, 10, 1000));
        characters.add(new Character("Barbara", "Hydro", "Catalyst", 2, 1, 52, 100, 10, 1000));
    }
}

//weaponTypeId
// Bow = 1
// Catalyst = 2
// Claymore = 3
// Polearm = 4
// Sword = 5
