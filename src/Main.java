import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Random;

//NOTATKA: cała gra jest po angielsku, bo mi tak wygodniej
//P.S. niektóre mechanizmy są dość ubogie
//z góry chciałabym przeprosić za tyle linijek w main, myślałam, aby porozdzielać każdą funkcję do innego pliku, ale dla mnie byłoby to dużo mniej czytelne i bym się chyba zgubiła


public class Main {

    //przyznawanie użytkownikowi funduszy

    static double ar = 1.0; // ar, czyli adventure rank to poziom gracza w grze, zwiększa się po każdej walce i maksymalny poziom to 60
    static int mora = 1000; // monety używane do podnoszenia poziomu postaci lub broni
    static int primogems = 320; // waluta używana do losowania postaci lub broni
    static Scanner answer = new Scanner(System.in);
    static  Random generateRandom = new Random();

    //ustawienie kolorów
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String BLUE = "\033[0;34m";
    public static final String RESET = "\033[0m";

    //menu akcji
    static String[] actionsMenu = {"Fight", "Level Up Your Character", "Level Up Your Weapon", "Check Statistics", "List All Characters", "List All Opponents", "List All Weapons", "Wish For Any Item", "Shut Down"};

    public static void main(String[] args)  throws InterruptedException {


        while (true) {

            //ładny nagłówek, bo tak (mam nadzieję, że chociaż czytelny)
            System.out.println(CYAN + "  ____  ____   ____    ______   ____  __ _   _ _        _  _____ ___  ____  ");
            System.out.println(" |  _ \\|  _ \\ / ___|  / ___\\ \\ / /  \\/  | | | | |      / \\|_   _/ _ \\|  _ \\ ");
            System.out.println(BLUE + " | |_) | |_) | |  _   \\___ \\\\ V /| |\\/| | | | | |     / _ \\ | || | | | |_) |");
            System.out.println(" |  _ <|  __/| |_| |   ___) || | | |  | | |_| | |___ / ___ \\| || |_| |  _ < ");
            System.out.println(PURPLE + " |_| \\_\\_|    \\____|  |____/ |_| |_|  |_|\\___/|_____/_/   \\_\\_| \\___/|_| \\_\\" + RESET);

            System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║ Your current mora status is " + (CYAN + mora) + RESET);
            System.out.println("║ Your current primogems status is " + (BLUE + primogems) + RESET);
            System.out.println("║ Your current Adventure Rank (AR) is " + (PURPLE + ar) + RESET);
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");


            //sprawdzanie poziomu gracza, bo za nowy poziom są nagrody itd
            if(ar % 1.0 == 0 && ar != 1.0){
                System.out.println("You've reached " + ar + " Adventure Rank level!");
                for(int i=0; i<OpponentsList.opponents.length; i++){
                    OpponentsList.opponents[i].opponentLvl++;
                    OpponentsList.opponents[i].dmg += 100;
                    OpponentsList.opponents[i].def += 10;
                    OpponentsList.opponents[i].hp += 1000;
                }
            }

            //to się przydaje tylko pod koniec gry
            if(ar == 60){
                bossFight();
            }

            //podstawowe menu wyboru akcji
            System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║ " + CYAN + "⋆｡°✩ Choose an action (1-6):" +
                    RESET);
            for (int i = 0; i < actionsMenu.length; i++) {
                System.out.println("║ " + (i + 1) + ". " + actionsMenu[i]);
            }
            int pickedAction;
            do {
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.print("║" + PURPLE + " * Select Action: " + RESET);
                pickedAction = answer.nextInt();
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

                switch (pickedAction) {
                    case 1 -> fight();
                    case 2 -> levelUpCharacter();
                    case 3 -> levelUpWeapon();
                    case 4 -> checkStatistics();
                    case 5 -> listAllCharacters();
                    case 6 -> listAllOpponents();
                    case 7 -> listAllWeapons();
                    case 8 -> wishForItems();
                    case 9 -> System.exit(0);
                    default -> System.out.println("Something went wrong, please try again.");
                }
                TimeUnit.SECONDS.sleep(2);
            } while (pickedAction >= actionsMenu.length || pickedAction < 0);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void fight() throws InterruptedException {

        //WYBIERANIE POSTACI DO WALKI

        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");

        int index = 1;

        System.out.println("║" + BLUE + " ⋆｡°✩ Pick an available character:" + RESET);
        for(int i=0; i<AvailableCharactersList.characters.size(); i++){
            System.out.println("║ " + (index * (i+1)) + ". " + AvailableCharactersList.characters.get(i).characterName + " - Weapon Type: " + AvailableCharactersList.characters.get(i).weaponType);
        }
        int pickedCharacter;
        do{
            System.out.print("║" + PURPLE + " * Select ID: " + RESET);
            pickedCharacter = answer.nextInt() -1;
            if(pickedCharacter < AvailableCharactersList.characters.size() && pickedCharacter >= 0){
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ You've picked character named " + AvailableCharactersList.characters.get(pickedCharacter).characterName);
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
            }else{
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ There is no such a character, please try again.");
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
            }
        }while(pickedCharacter >= AvailableCharactersList.characters.size() || pickedCharacter < 0);


        //WYBIERANIE BRONI DO WALKI

        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");

        System.out.println("║" + BLUE + " ⋆｡°✩ Pick an available weapon:" + RESET);
        for(int i=0; i<AvailableWeaponsList.weapons.size(); i++){
            System.out.println(("║ " + index * (i+1)) + ". " + AvailableWeaponsList.weapons.get(i).weaponName + " - Weapon Type: " + AvailableWeaponsList.weapons.get(i).weaponType);
        }
        int pickedWeapon;
        do{
            System.out.print("║" + PURPLE + " * Select ID: " + RESET);
            pickedWeapon = answer.nextInt() -1;
            //TYP BRONI, SIĘ MUSI ZGADZAĆ Z TYM, KTÓRY JEST PRZYPISANY DO POSTACI DLATEGO TAKI DŁUGI WARUNEK

            if(pickedWeapon < AvailableWeaponsList.weapons.size() && pickedWeapon >= 0 && AvailableCharactersList.characters.get(pickedCharacter).weaponTypeId == AvailableWeaponsList.weapons.get(pickedWeapon).weaponTypeId){
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ You've picked weapon named " + AvailableWeaponsList.weapons.get(pickedWeapon).weaponName);
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
            }else{
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ There is no such a weapon or the weapon types do not match, please try again.");
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
            }
        }while(pickedWeapon >= AvailableWeaponsList.weapons.size() || pickedWeapon < 0 || AvailableCharactersList.characters.get(pickedCharacter).weaponTypeId != AvailableWeaponsList.weapons.get(pickedWeapon).weaponTypeId);



        //WYBIERANIE OPONENTA DO WALKI

        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");

        System.out.println("║" + BLUE + "⋆｡°✩ Pick an available opponent:" + RESET);
        for(int i=0; i<OpponentsList.opponents.length; i++){
            System.out.println("║ " + OpponentsList.opponents[i].opponentId + ". " + OpponentsList.opponents[i].opponentName);
        }
        int pickedOpponent;
        do{
            System.out.print("║" + PURPLE + " * Select ID: " + RESET);
            pickedOpponent = answer.nextInt() -1;
            if(pickedOpponent < OpponentsList.opponents.length && pickedOpponent >= 0){
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ You've picked opponent named " + OpponentsList.opponents[pickedOpponent].opponentName);
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
            }else{
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ There is no such a opponent, please try again.");
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
            }
        }while(pickedOpponent >= OpponentsList.opponents.length || pickedOpponent < 0);

        // USTAWIANIE STATYSTYK POSTACI GRACZA
        int finalDmg = AvailableCharactersList.characters.get(pickedCharacter).dmg + AvailableWeaponsList.weapons.get(pickedWeapon).dmg;
        int finalDef = AvailableCharactersList.characters.get(pickedCharacter).def + AvailableWeaponsList.weapons.get(pickedWeapon).def;
        int finalHp = AvailableCharactersList.characters.get(pickedCharacter).hp + AvailableWeaponsList.weapons.get(pickedWeapon).hp;


        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ ⋆｡°✩ Your statistics:");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║DMG (Damage): " + CYAN + finalDmg + RESET);
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║DEF (Defensive): " + BLUE + finalDef + RESET);
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║HP (Health Points): " + PURPLE + finalHp + RESET);
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");


        //WALKA (NIESTETY GRACZ NIE MA ZBYTNIO WPŁYWU NA JEJ PRZEBIEG, BO ATAKI SĄ GENEROWANE LOSOWO, DLATEGO JEDYNY WPŁYW GRACZA TO POZIOM, STATYSTYKI ITD.)
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Let the fight begin!");
        for(int i=0; i<3; i++){
            System.out.println("║" + (i+1));
        }
        System.out.println("║ Go!");


        //NAWET TO, KTO ZACZYNA, JEST LOSOWE, TROCHĘ JAK W SZACHACH, BO TAM TEŻ JEST TAKI TRYB
        int whoStarts = generateRandom.nextInt(2);

        int characterCurrentHp = finalHp;
        int characterDef = AvailableCharactersList.characters.get(pickedCharacter).def + AvailableWeaponsList.weapons.get(pickedWeapon).def;
        int opponentCurrentHp = OpponentsList.opponents[pickedOpponent].hp;
        int opponentDef = OpponentsList.opponents[pickedOpponent].def;
        int characterAttack;
        int opponentAttack;

        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");//A JAK NIE, TO NIC SIĘ NIE DZIEJE
        if(whoStarts == 0){
            System.out.println("║ * Your turn first!");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

            while(characterCurrentHp > 0 && opponentCurrentHp > 0){

                //PRZEBIEG WALKI
                characterAttack = generateRandom.nextInt(1, AvailableCharactersList.characters.get(pickedCharacter).dmg);
                if(characterAttack > opponentCurrentHp){
                    opponentCurrentHp = 0;
                }else{
                    opponentCurrentHp -= characterAttack - opponentDef;
                }
                TimeUnit.SECONDS.sleep(2);
                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║ " + AvailableCharactersList.characters.get(pickedCharacter).characterName + " dealt " + CYAN + characterAttack + " damage!" + RESET);
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ Your Current HP: " + characterCurrentHp + PURPLE + " ║ " + RESET + "Opponent Current HP: " + opponentCurrentHp);
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

                opponentAttack = generateRandom.nextInt(1, OpponentsList.opponents[pickedOpponent].dmg);
                if(opponentAttack > characterCurrentHp){
                    characterCurrentHp = 0;
                }else{
                characterCurrentHp -= opponentAttack - characterDef;
                }

                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║ " + OpponentsList.opponents[pickedOpponent].opponentName + " took " + BLUE + opponentAttack + " of your hp!" + RESET);
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ Your Current HP: " + characterCurrentHp + PURPLE + " ║ " + RESET + "Opponent Current HP: " + opponentCurrentHp);
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                //NOTATKA: POLEGA TO MNIEJ WIĘCEJ NA TYM, ŻE ATAKI OBU STRON SA GENEROWANE, ALE TAK BY ICH MAKSYMALNA WARTOŚĆ NIE PRZEKRACZAŁA TEJ W STATYSTYKACH, DLATEGO JAK JUŻ WCZEŚNIEJ NAPISAŁAM — WIĘKSZOŚĆ ODBYWA SIĘ NA ZASADZIE LOSOWOŚCI

            }

            //JEŚLI GRACZ WYGRA ZOSTAJE OBDAROWANY


        }else{
            //TUTAJ TO SAMO TYLE, ŻE OPONENT ZACZYNA
            System.out.println("║ * Your opponent's turn first!");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

            while(characterCurrentHp > 0 && opponentCurrentHp > 0){

                opponentAttack = generateRandom.nextInt(1, OpponentsList.opponents[pickedOpponent].dmg);
                if(opponentAttack > characterCurrentHp){
                    characterCurrentHp = 0;

                }else{
                    characterCurrentHp -= opponentAttack - characterDef;
                }
                TimeUnit.SECONDS.sleep(2);
                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║ " + OpponentsList.opponents[pickedOpponent].opponentName + " took " + BLUE + opponentAttack + " of your hp!" + RESET);
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ Your Current HP: " + characterCurrentHp + PURPLE + " ║ " + RESET + "Opponent Current HP: " + opponentCurrentHp);
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                characterAttack = generateRandom.nextInt(1, AvailableCharactersList.characters.get(pickedCharacter).dmg);
                if(characterAttack > opponentCurrentHp){
                    opponentCurrentHp = 0;
                }else {
                    opponentCurrentHp -= characterAttack - opponentDef;
                }
                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║ " + AvailableCharactersList.characters.get(pickedCharacter).characterName + " dealt " + CYAN + characterAttack + " damage!" + RESET);
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ Your Current HP: " + characterCurrentHp + PURPLE + " ║ " + RESET + "Opponent Current HP: " + opponentCurrentHp);
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
            }

        }
        if(characterCurrentHp > opponentCurrentHp){
            System.out.println(CYAN + "╔═══════════════════════════════════════════════════════════════════════════════════╗" + RESET);
            System.out.println(BLUE + "║ You won! (+100 Mora, +0.2 AR EXP)" + RESET);
            System.out.println(PURPLE + "╚═══════════════════════════════════════════════════════════════════════════════════╝" + RESET);
            mora += 100;
            ar += 0.2;
            ar = Math.round(ar * 10) / 10.0;
            //A JAK NIE, TO NIC SIĘ NIE DZIEJE
        } else {
            System.out.println(PURPLE + "╔═══════════════════════════════════════════════════════════════════════════════════╗" + RESET);
            System.out.println(BLUE + "║ Your opponent won!" + RESET);
            System.out.println(CYAN + "╚═══════════════════════════════════════════════════════════════════════════════════╝" + RESET);
        }
        TimeUnit.SECONDS.sleep(2);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void levelUpCharacter(){

        //NO ZWYKLE PODNOSZENIE POZIOMU WYBRANEJ POSTACI

        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║" + BLUE + " ⋆｡°✩ Pick an character to Level Up:" + RESET);
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
        int index = 1;
        for(int i=0; i<AvailableCharactersList.characters.size(); i++){
            System.out.println("║ " + index * (i+1) + ". " + AvailableCharactersList.characters.get(i).characterName);
        }

        int pickedCharacter;

        do{
            System.out.print("║" + PURPLE + " * Select ID: " + RESET);
            pickedCharacter = answer.nextInt() -1;
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
            if(pickedCharacter < AvailableCharactersList.characters.size() && pickedCharacter >= 0) {
                Character pickedCharacterReference = AvailableCharactersList.characters.get(pickedCharacter);
                String lvlUp;

            System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║ Do you want to level this character up from " + pickedCharacterReference.characterLvl + "LVL for 500 Mora? (yes/no)");
                System.out.print("║" + PURPLE + "* " + RESET);
            do{
                lvlUp = answer.next();
                if (lvlUp.equals("yes")) {
                    if(mora >= 500) {
                        mora -= 500;
                        //PODNOSZENIE POZIOMU KOSZTUJE, ALE DOSTAJE SIĘ TROCHE PUNKTÓW DO STATYSTYK
                        pickedCharacterReference.dmg += 50;
                        pickedCharacterReference.def += 5;
                        pickedCharacterReference.hp += 500;
                        pickedCharacterReference.characterLvl++;
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ You Leveled Up your character to " + pickedCharacterReference.characterLvl + "LVL.");
                        System.out.println("║ DMG increased to: " + CYAN + pickedCharacterReference.dmg + RESET);
                        System.out.println("║ DEF increased to: " + BLUE + pickedCharacterReference.def + RESET);
                        System.out.println("║ HP increased to: " + PURPLE + pickedCharacterReference.hp + RESET);
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                    }else{
                        //A JAK SIĘ NIE MA KASY TO UNLUCKY, BO TRZEBA ZNOWU WALCZYĆ
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ You do not have enough mora. Your current Mora status is " + mora);
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                    }
                } else if (lvlUp.equals("no")) {
                    System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                    System.out.println("║ You've canceled this action.");
                    System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                } else {
                    System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                    System.out.println("║ ⋆｡°✩ Choose answer between yes or no.");
                }
            }while(!lvlUp.equals("yes") && !lvlUp.equals("no"));


            }else{
                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║ There is no such a character, please try again.");
            }
        }while(pickedCharacter >= AvailableCharactersList.characters.size() || pickedCharacter < 0);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void levelUpWeapon() throws InterruptedException {

        //TO SAMO CO WYŻEJ TYLKO, ŻE TYM RAZEM O BRONIACH (NIE CHCE MI SIĘ PISAĆ KOMENTARZY, BO TA FUNKCJA WYGLĄDA NIEMAL IDENTYCZNIE)



        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║" + BLUE + " ⋆｡°✩ Pick a weapon to Level Up:" + RESET);
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");

        int index = 1;
        for(int i=0; i<AvailableWeaponsList.weapons.size(); i++){
            System.out.println("║ " + index * (i+1) + ". " + AvailableWeaponsList.weapons.get(i).weaponName);
        }
        int pickedWeapon;

        do{
            System.out.print("║ " + PURPLE + " * Select ID: " + RESET) ;
            pickedWeapon = answer.nextInt() -1;
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
            if(pickedWeapon < AvailableWeaponsList.weapons.size() && pickedWeapon >= 0) {
                Weapon pickedWeaponReference = AvailableWeaponsList.weapons.get(pickedWeapon);
                String lvlUp;

                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║ Do you want to level this weapon up from " + pickedWeaponReference.weaponLvl + "LVL for 500 Mora? (yes/no)");
                System.out.print("║ " + PURPLE + "* " + RESET);
                do{
                    lvlUp = answer.next();
                    if (lvlUp.equals("yes")) {
                        if(mora >= 500) {
                            mora -= 500;
                            pickedWeaponReference.weaponLvl++;
                            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                            System.out.println("║ You Leveled Up your weapon to " + pickedWeaponReference.weaponLvl + "LVL.");
                            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                        }else{
                            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                            System.out.println("║ You do not have enough mora. Your current Mora status is " + mora);
                            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                        }
                    } else if (lvlUp.equals("no")) {
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ You've canceled this action.");
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                    } else {
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ ⋆｡°✩ Choose answer between yes or no.");
                    }
                }while(!lvlUp.equals("yes") && !lvlUp.equals("no"));


            }else{
                System.out.println("There is no such a weapon, please try again.");
            }
        }while(pickedWeapon >= AvailableWeaponsList.weapons.size() || pickedWeapon < 0);
        TimeUnit.SECONDS.sleep(2);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void checkStatistics() throws InterruptedException {

        //JAK GRACZ WPADNIE NA POMYSŁ, ŻE W SUMIE TO CHCE WIEDZIEĆ JAKIE STATYSTYKI MA KONKRETNA POSTAĆ TO TUTAJ

        int categoryToCheck;
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║ " + BLUE + "⋆｡°✩ Which category you wanna check out?" + RESET);
            System.out.println("║ 1. Characters");
            System.out.println("║ 2. Weapons");
        do {
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
            System.out.print("║ " + PURPLE + "* Select Number: " + RESET);
            categoryToCheck = answer.nextInt();
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

            if(categoryToCheck == 1){

                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║ " + BLUE + "⋆｡°✩ Pick an available character:" + RESET);
                int index = 1;
                for(int i=0; i<AvailableCharactersList.characters.size(); i++){
                    System.out.println("║ " + index * (i+1) + ". " + AvailableCharactersList.characters.get(i).characterName);
                }
                int pickedCharacter;
                do{
                    System.out.print("║" + PURPLE + " * Select ID: " + RESET);
                    pickedCharacter = answer.nextInt() -1;
                    if(pickedCharacter < AvailableCharactersList.characters.size() && pickedCharacter >= 0){
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                        System.out.println("║ " + AvailableCharactersList.characters.get(pickedCharacter).characterName);
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ " + AvailableCharactersList.characters.get(pickedCharacter).characterElement);
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ " + AvailableCharactersList.characters.get(pickedCharacter).weaponType);
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ " + "DMG: " + CYAN + AvailableCharactersList.characters.get(pickedCharacter).dmg + RESET);
                        System.out.println("║ " + "DEF: " + BLUE + AvailableCharactersList.characters.get(pickedCharacter).def + RESET);
                        System.out.println("║ " + "HP: " + PURPLE + AvailableCharactersList.characters.get(pickedCharacter).hp + RESET);
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                    }else{
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ There is no such a character, please try again.");
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                    }
                }while(pickedCharacter >= AvailableCharactersList.characters.size() || pickedCharacter < 0);

            }else if(categoryToCheck == 2){

                //TU TO SAMO, ALE DO BRONI
                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║ " + BLUE + "⋆｡°✩ Pick a weapon character:" + RESET);
                int index = 1;
                for(int i=0; i<AvailableWeaponsList.weapons.size(); i++){
                    System.out.println(index * (i+1) + ". " + AvailableWeaponsList.weapons.get(i).weaponName);
                }
                int pickedWeapon;
                do{
                    System.out.print(" * Select ID: ");
                    pickedWeapon = answer.nextInt() -1;
                    if(pickedWeapon < AvailableWeaponsList.weapons.size() && pickedWeapon >= 0){
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                        System.out.println("║ " + AvailableCharactersList.characters.get(pickedWeapon).characterName);
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ " + AvailableCharactersList.characters.get(pickedWeapon).characterElement);
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ " + AvailableCharactersList.characters.get(pickedWeapon).weaponType);
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ " + "DMG: " + CYAN + AvailableCharactersList.characters.get(pickedWeapon).dmg + RESET);
                        System.out.println("║ " + "DEF: " + BLUE + AvailableCharactersList.characters.get(pickedWeapon).def + RESET);
                        System.out.println("║ " + "HP: " + PURPLE + AvailableCharactersList.characters.get(pickedWeapon).hp + RESET);
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                    }else{
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ There is no such a weapon, please try again.");
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                    }
                }while(pickedWeapon >= AvailableWeaponsList.weapons.size() || pickedWeapon < 0);
            }else{
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ Pick between 1 and 2.");
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
            }

        }while(categoryToCheck < 0 || categoryToCheck > 2);
        TimeUnit.SECONDS.sleep(2);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void listAllCharacters() throws InterruptedException {

        //NO, A JAK GRACZ ZECHCE ZOBACZYĆ JAKIE POSTACI POSIADA NO TO TEŻ MOŻNA, WŁAŚNIE TUTAJ

        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
        int index = 1;

        for(int i=0; i<AvailableCharactersList.characters.size(); i++){
            System.out.println("║ " + (index * (i+1)) + ". " + AvailableCharactersList.characters.get(i).characterName);
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
        }

        System.out.println("║ That's all.");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
        TimeUnit.SECONDS.sleep(2);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void listAllOpponents() throws InterruptedException {

        //MOŻNA TEZ SPRAWDZIĆ JACY OPONENCI WYSTĘPUJĄ W GRZE

        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
        int index = 1;

        for(int i=0; i< OpponentsList.opponents.length; i++){
            System.out.println(("║ " + index * (i+1)) + ". " + OpponentsList.opponents[i].opponentName + " * DMG: " + OpponentsList.opponents[i].dmg + " * DEF: " + OpponentsList.opponents[i].def + " * HP: " + OpponentsList.opponents[i].hp);
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
        }

        System.out.println("║ That's all.");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
        TimeUnit.SECONDS.sleep(2);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void listAllWeapons() throws InterruptedException {

        //NO I OCZYWIŚCIE LISTOWANIE BRONI POSIADANYCH PRZEZ GRACZA

        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
        int index = 1;


        for(int i=0; i< AvailableWeaponsList.weapons.size(); i++){
            System.out.println(("║ " + index * (i+1)) + ". " + AvailableWeaponsList.weapons.get(i).weaponName + " * Weapon Type: " + AvailableWeaponsList.weapons.get(i).weaponType + " * DMG: " + AvailableWeaponsList.weapons.get(i).dmg + " * DEF: " + AvailableWeaponsList.weapons.get(i).def + " * HP: " + AvailableWeaponsList.weapons.get(i).hp);
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
        }

        System.out.println("║ That's all.");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
        TimeUnit.SECONDS.sleep(2);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void wishForItems() throws InterruptedException {
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");

        int categoryToWish;
        System.out.println("║ " + BLUE + "⋆｡°✩ What would you like to wish for?" + RESET);
        System.out.println("║ 1. Characters");
        System.out.println("║ 2. Weapons");

        do {
            System.out.print("║ " + PURPLE + "* Select Number: " + RESET);
            categoryToWish = answer.nextInt();
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");

            if (categoryToWish == 1) {
                String wish;
                do {

                    System.out.println("║ Do you want to wish for new character for 160 primogems? (yes/no)");
                    System.out.print("║ " + PURPLE + "* " + RESET);
                    wish = answer.next();

                    if (wish.equalsIgnoreCase("yes")) {
                        if (primogems >= 160) {
                            primogems -= 160;
                            int characterRandomId = generateRandom.nextInt(WishCharacterList.characters.length);
                            Character wishedCharacter = WishCharacterList.characters[characterRandomId];
                            boolean characterAlreadyOwned = false;

                            for (Character c : AvailableCharactersList.characters) {
                                if (c.characterId == wishedCharacter.characterId) {
                                    characterAlreadyOwned = true;
                                    break;
                                }
                            }

                            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                            if (characterAlreadyOwned) {
                                System.out.println("║ You already have that character, you're gifted with bonus statistics increase!");
                                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                                primogems += 80;
                                mora += 100;
                                wishedCharacter.dmg += 100;
                                wishedCharacter.def += 10;
                                wishedCharacter.hp += 1000;
                            } else {
                                System.out.println("║ Congrats! You own new character named " + wishedCharacter.characterName);
                                AvailableCharactersList.characters.add(wishedCharacter);
                                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                            }
                        } else {
                            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                            System.out.println("║ You do not have enough Primogems.");
                            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                        }
                    } else if (wish.equalsIgnoreCase("no")) {
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ You've canceled this action.");
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

                    } else {
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ Choose answer between yes or no.");
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                    }
                } while (!wish.equals("yes") && !wish.equals("no"));

            } else if (categoryToWish == 2) {
                String wish;
                do {
                    System.out.println("║ Do you want to wish for new weapon for 160 primogems? (yes/no)");
                    System.out.print("║ " + PURPLE + "* " + RESET);
                    wish = answer.next();

                    if (wish.equalsIgnoreCase("yes")) {
                        if (primogems >= 160) {
                            primogems -= 160;
                            int weaponRandomId = generateRandom.nextInt(WishWeaponList.weapons.length);
                            Weapon wishedWeapon = WishWeaponList.weapons[weaponRandomId];
                            boolean weaponAlreadyOwned = false;

                            for (Weapon w : AvailableWeaponsList.weapons) {
                                if (w.weaponId == wishedWeapon.weaponId) {
                                    weaponAlreadyOwned = true;
                                    break;
                                }
                            }

                            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                            if (weaponAlreadyOwned) {
                                System.out.println("║ You already have that weapon, you're gifted with bonus statistics increase!");
                                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                                primogems += 20;
                                mora += 50;
                                wishedWeapon.dmg += 50;
                                wishedWeapon.def += 2;
                                wishedWeapon.hp += 10;
                            } else {
                                System.out.println("║ Congrats! You own new weapon named " + wishedWeapon.weaponName);
                                AvailableWeaponsList.weapons.add(wishedWeapon);
                                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                            }
                        } else {
                            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                            System.out.println("║ You do not have enough Primogems.");
                            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                        }
                    } else if (wish.equalsIgnoreCase("no")) {
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ You've canceled this action.");
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                    } else {
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ Choose answer between yes or no.");
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                    }
                } while (!wish.equals("yes") && !wish.equals("no"));

            } else {
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ Pick between 1 or 2.");
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
            }
        } while (categoryToWish != 1 && categoryToWish != 2);
        TimeUnit.SECONDS.sleep(2);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static void bossFight() throws InterruptedException {
        //KOŃCOWA WALKA Z LOSOWYM BOSSEM, ALE TRZEBA BY CHYBA GRAĆ Z 2-3H, ŻEBY TUTAJ DOTRZEĆ ALBO JESZCZE DŁUŻEJ (NIE SPRAWDZAŁAM)

        int finalBossRandomId = generateRandom.nextInt(1, FinalBossesList.finalBosses.length);

        String finalBoss = FinalBossesList.finalBosses[finalBossRandomId].finalBossName;

        int finalBossDmg = 0;
        int finalBossDef = 0;
        int finalBossHp = 0;

        //BOSS MA STATYSTYKI RÓWNE SUMIE PODSTAWOWYCH STATYSTYK WSZYSTKICH POSTACI W GRZE
        for(int i=0; i<WishCharacterList.characters.length; i++){
            finalBossDmg += AvailableCharactersList.characters.get(i).dmg;
            finalBossDef += AvailableCharactersList.characters.get(i).def;
            finalBossHp += AvailableCharactersList.characters.get(i).hp;
        }


        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Congrats! You have completed this game and reached 60 level of Adventure Rank!");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("║ At this point you have to face the greatest opponent!");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("║ There is only one chance to fight is, if you resign you're not gonna be able to do it again in this session...");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("║ Do you want to fight it? (yes/no)");
        System.out.print("║ " + PURPLE + "* " + RESET);
        String fightBoss = answer.next();

        do {
            if (fightBoss.equals("yes")) {
                String playersCharacterName;
                int playersCharacterWeaponType;
                int playersCharacterDmg = 0;
                int playersCharacterDef = 0;
                int playersCharacterHp = 0;

                for(int i=0; i<AvailableCharactersList.characters.size(); i++){
                    playersCharacterDmg += (AvailableCharactersList.characters.get(i).dmg + AvailableWeaponsList.weapons.get(i).dmg);
                    playersCharacterDef += (AvailableCharactersList.characters.get(i).def + AvailableWeaponsList.weapons.get(i).def);
                    playersCharacterHp += (AvailableCharactersList.characters.get(i).hp + AvailableWeaponsList.weapons.get(i).hp);
                }

                //TUTAJ GRACZ MOŻE SIĘ WYKAZAĆ, BO MUSI STWORZYĆ WŁASNĄ POSTAĆ
                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║ Here you're about to make your own character...");
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.print("║ ⋆｡°✩ Name: ");
                playersCharacterName = answer.next();
                System.out.print("║ ⋆｡°✩ Weapon Type: ");
                System.out.print("║ 1. Bow");
                System.out.print("║ 2.Catalyst");
                System.out.print("║ 3. Claymore");
                System.out.print("║ 4. Polearm");
                System.out.println("║ 5. Sword");
                System.out.print("║ " + PURPLE + "* " + RESET);
                playersCharacterWeaponType = answer.nextInt();
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");


                //WYBÓR BRONI

                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║" + BLUE + " ⋆｡°✩ Pick an available weapon:" + RESET);

                for(int i=0; i<AvailableWeaponsList.weapons.size(); i++){
                    System.out.println("║ " + AvailableWeaponsList.weapons.get(i).weaponId + ". "
                            + AvailableWeaponsList.weapons.get(i).weaponName
                            + " - Weapon Type: " + AvailableWeaponsList.weapons.get(i).weaponType);
                }
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

                int pickedWeapon;
                do{
                    System.out.print("║" + PURPLE + " * Select ID: " + RESET);
                    pickedWeapon = answer.nextInt() - 1;

                    if(pickedWeapon < AvailableWeaponsList.weapons.size()
                            && pickedWeapon >= 0
                            && playersCharacterWeaponType == AvailableWeaponsList.weapons.get(pickedWeapon).weaponTypeId){

                        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                        System.out.println("║ You've picked weapon named "
                                + BLUE + AvailableWeaponsList.weapons.get(pickedWeapon).weaponName + RESET);
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

                    } else {
                        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                        System.out.println("║" + PURPLE + " There is no such a weapon or the weapon types do not match, please try again." + RESET);
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                    }

                } while(pickedWeapon >= AvailableWeaponsList.weapons.size() || pickedWeapon < 0 || playersCharacterWeaponType != AvailableWeaponsList.weapons.get(pickedWeapon).weaponTypeId);


                //WYPISANIE TEJ POSTACI STWORZONEJ PRZEZ GRACZA
                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║ Your character's statistics equal the sum of every statistics of your possessed characters.");
                System.out.println("║ Character's name: " + playersCharacterName);
                System.out.println("║ Character's weapon: " + pickedWeapon);
                System.out.println("║ Character's damage: " + CYAN + playersCharacterDmg + RESET);
                System.out.println("║ Character's defensive: " + BLUE + playersCharacterDef + RESET);
                System.out.println("║ Character's health points: " + PURPLE + playersCharacterHp + RESET);
                System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                System.out.println("║ You're about to fight final boss named " + finalBoss + ", good luck Traveler!");
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");


                //WALKA OSTATECZNA (TO SAMO CO W FUNKCJI FIGHT)

                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║ Let the fight begin!");
                for(int i=0; i<3; i++){
                    System.out.println("║ " + (i+1));
                }
                System.out.println("║ Go!");
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

                System.out.println(" ");
                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                System.out.println("║ Preparing the battlefield...");
                System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                System.out.println(" ");

                int whoStarts = generateRandom.nextInt(2);

                System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                if(whoStarts == 0){
                    System.out.println("║ * Your turn first!");
                    System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

                    while(playersCharacterHp > 0 && finalBossHp > 0){

                        // PRZEBIEG WALKI
                        int playersAttack = generateRandom.nextInt(1, playersCharacterDmg);
                        if(playersAttack > finalBossHp){
                            finalBossHp = 0;
                        } else {
                            finalBossHp -= (playersAttack - finalBossDef);
                        }
                        TimeUnit.SECONDS.sleep(2);


                        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                        System.out.println("║ You dealt " + CYAN + playersAttack + RESET + " damage!");
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ Your Current HP: " + PURPLE + playersCharacterHp + RESET + " ║ Opponent Current HP: " + PURPLE + finalBossHp + RESET);
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

                        int finalBossAttack = generateRandom.nextInt(1, finalBossDmg);
                        if(finalBossAttack > playersCharacterHp){
                            playersCharacterHp = 0;
                        } else {
                            playersCharacterHp -= (finalBossAttack - playersCharacterDef);
                        }

                        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                        System.out.println("║ Opponent hit you for " + BLUE + finalBossAttack + RESET + " damage!");
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ Your Current HP: " + PURPLE + playersCharacterHp + RESET + " ║ Opponent Current HP: " + PURPLE + finalBossHp + RESET);
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                    }

                } else {
                    // OPPONENT ZACZYNA
                    System.out.println("║ * Your opponent's turn first!");
                    System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

                    while(playersCharacterHp > 0 && finalBossHp > 0){

                        int finalBossAttack = generateRandom.nextInt(1, finalBossDmg);
                        if(finalBossAttack > playersCharacterHp){
                            playersCharacterHp = 0;
                        } else {
                            playersCharacterHp -= (finalBossAttack - playersCharacterDef);
                        }
                        TimeUnit.SECONDS.sleep(2);

                        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                        System.out.println("║ Opponent hit you for " + BLUE + finalBossAttack + RESET + " damage!");
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ Your Current HP: " + PURPLE + playersCharacterHp + RESET + " ║ Opponent Current HP: " + PURPLE + finalBossHp + RESET);
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

                        int playersAttack = generateRandom.nextInt(1, playersCharacterDmg);
                        if(playersAttack > finalBossHp){
                            finalBossHp = 0;
                        } else {
                            finalBossHp -= (playersAttack - finalBossDef);
                        }

                        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
                        System.out.println("║ You dealt " + CYAN + playersAttack + RESET + " damage!");
                        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════╣");
                        System.out.println("║ Your Current HP: " + PURPLE + playersCharacterHp + RESET + " ║ Opponent Current HP: " + PURPLE + finalBossHp + RESET);
                        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");
                    }
                }

// WYNIK WALKI
                if(playersCharacterHp > finalBossHp){
                    System.out.println(CYAN + "╔═══════════════════════════════════════════════════════════════════════════════════╗" + RESET);
                    System.out.println(BLUE + "║ Congrats! You won your final battle, that's the end of our journey! Bye!" + RESET);
                    System.out.println(PURPLE + "╚═══════════════════════════════════════════════════════════════════════════════════╝" + RESET);
                    TimeUnit.SECONDS.sleep(2);
                    System.exit(0);
                } else {
                    System.out.println(PURPLE + "╔═══════════════════════════════════════════════════════════════════════════════════╗" + RESET);
                    System.out.println(BLUE + "║ Unfortunately you lost that last battle, but that's the end of our journey and it was a pleasure to join you during it! Bye!" + RESET);
                    System.out.println(CYAN + "╚═══════════════════════════════════════════════════════════════════════════════════╝" + RESET);
                }

            } else if (fightBoss.equals("no")) {
                System.out.println(CYAN + "╔═══════════════════════════════════════════════════════════════════════════════════╗" + RESET);
                System.out.println(BLUE + "║ Alright, It was a pleasure to have this journey with you!" + RESET);
                System.out.println(PURPLE + "╚═══════════════════════════════════════════════════════════════════════════════════╝" + RESET);
                System.exit(0);
            } else {
                System.out.println(PURPLE + "╔═══════════════════════════════════════════════════════════════════════════════════╗" + RESET);
                System.out.println(BLUE + "║ You need to choose between yes or no." + RESET);
                System.out.println(CYAN + "╚═══════════════════════════════════════════════════════════════════════════════════╝" + RESET);
            }
        } while(fightBoss.equals("yes"));

    }
}