
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Cao Phuc
 */
public class RamManager {

    public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException, ParseException {

        listOfRam LOR = new listOfRam();
        int choice = 0;
        String fileName = "RAM.txt";
        String fileBinary = "RAMBianry01.bin";
        boolean ValidChoice;
        boolean continueMenu = true;

        File f = new File(fileBinary);
        if (f.exists()) {
            LOR.loadBinaryData(fileBinary);
        } else {
            LOR.readToFile(fileName);
        }

        do {
            ValidChoice = false;
            do {
                try {
                    choice = menu("Add an Item.", "Search SubMenu.", "Update Item Information.", "Delete Item.", "Show All Item.", "Store Data to Files.", "Quit Menu.");
                    if (choice < 1 || choice > 6) {
                        System.out.println("Thank you!\nGood Bye!");
                        continueMenu = false;
                        break;
                    } else {
                        ValidChoice = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("INVALID CHOICE! PLEASE CHOOSE AGAIN!\n--------------------------------");
                }
            } while (!ValidChoice);

            if (!continueMenu) {
                break;
            }

            switch (choice) {
                case 1:
                    LOR.addItem(fileName, fileBinary);
                    break;
                case 2:
                    int choice2 = 0;
                    boolean exitSearchSubMenu = false;

                    do {
                        ValidChoice = false;
                        do {
                            try {
                                choice2 = menu("Search by type.", "Search by bus.", "Search by brand.", "Quit Menu_Search.");
                                if (choice2 < 1 || choice2 > 3) {
                                    System.out.println("--------------Return to main menu!--------------------");
                                    exitSearchSubMenu = true;
                                    break;
                                } else {
                                    ValidChoice = true;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("PLEASE ENTER A NUMBER!");
                                System.out.println("--------------------------------");
                            }
                        } while (!ValidChoice);

                        switch (choice2) {
                            case 1:
                                LOR.searchType();
                                break;
                            case 2:
                                LOR.searchBus();
                                break;
                            case 3:
                                LOR.searchBrand();
                                break;
                        }
                    } while (!exitSearchSubMenu);
                    break;

                case 3:
                    if (LOR.updateRam(fileName, fileBinary)) {
                        System.out.println("Updated!");
                    } else {
                        System.out.println("No update!!!");
                    }
                    System.out.println("--------------------------------");
                    break;
                case 4:
                    LOR.deleteRamByCode(fileName, fileBinary);
                case 5:
                    LOR.displayAll(fileName, fileBinary);
                    System.out.println("--------------------------------");
                    break;
                case 6:
                    LOR.saveBinaryData(fileBinary);
                    LOR.writeToFile(fileName);
                    System.out.println("File saved successfully");
                    System.out.println("--------------------------------");
                    break;
                default:
                    System.out.println("Bye!");
                    break;
            }

        } while (continueMenu);

        sc.close();
    }

    public static int menu(Object... options) {
        int L = options.length;
        for (int i = 0; i < L; i++) {
            System.out.println((i + 1) + "-" + options[i].toString());
        }
        System.out.printf("Choose (1.." + L + "): ");
        return Integer.parseInt(sc.nextLine());
    }
}
