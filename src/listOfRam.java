
import static MyTools.MyTool.sc;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Iterator;
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
public class listOfRam extends ArrayList<Ram> {

    //-------------------------Tools------------------------------------
    public static final Scanner scString = new Scanner(System.in);

    public static final Scanner scInt = new Scanner(System.in);

    public boolean checkDate(String d) throws ParseException {

        d = MyTools.MyTool.normalizeDateStr(d);

        SimpleDateFormat SDF = new SimpleDateFormat("MM/yyyy");
        SDF.setLenient(true);

        try {
            String[] checkDate = d.split("/");

            if (Integer.parseInt(checkDate[0]) < 1 || Integer.parseInt(checkDate[0]) > 12) {
                return false;
            }

            Date inputDate = SDF.parse(d);
            Date dateNow = new Date();

            Date startDate = SDF.parse(d);

            if (inputDate.before(startDate) || inputDate.after(dateNow)) {
                System.out.println("Date is out of range!");
                return false;
            }
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

//------------------------Add Item-----------------------------------
    public void addItem(String fileName, String fileBinary) throws IOException, ParseException {
        String cont = "y";
        String Type, Bus, Brand, Quantity, ProductionMonthYear;
        do {
            System.out.println("---Add a new Item---");
            do {
                System.out.printf("Enter Item's type: ");
                Type = scString.nextLine();
                if (Type.isEmpty()) {
                    System.out.println("The type is Empty!!!");
                    cont = "t";
                } else {
                    cont = "f";
                }
            } while (MyTools.MyTool.parseBoolean(cont));
            cont = "t";

            do {
                System.out.printf("Enter Item's bus: ");
                Bus = scString.nextLine();
                if (Bus.isEmpty()) {
                    System.out.println("The bus is Empty!!!");
                    System.out.println("Please re-enter!");
                    cont = "t";
                } else if (Bus.matches("\\d+")) {
                    cont = "f";
                } else {
                    System.out.println("Error bus!!!");
                    System.out.println("The bus must be a number!");
                    System.out.println("Please re-enter!");
                    cont = "t";
                }
            } while (MyTools.MyTool.parseBoolean(cont));
            cont = "t";

            do {
                System.out.printf("Enter Item's brand: ");
                Brand = scString.nextLine();
                if (Brand.isEmpty()) {
                    System.out.println("The brand is Empty!!!");
                    System.out.println("Please re-enter!");
                    cont = "t";
                } else if (MyTools.MyTool.checkSpecialCharacter(Brand)) {
                    Brand = MyTools.MyTool.formatName(Brand);
                    cont = "f";
                } else {
                    System.out.println("Not mactch any brand or We don't update yet!!!\nTry Again!!!");
                    System.out.println("Please re-enter!");
                    cont = "t";
                }
            } while (MyTools.MyTool.parseBoolean(cont));
            cont = "t";

            do {
                System.out.printf("Enter Item's quantity: ");
                Quantity = scString.nextLine();
                if (Quantity.isEmpty()) {
                    System.out.println("The quantity is Empty!!!");
                    System.out.println("Please re-enter!");
                    cont = "t";
                } else if (Quantity.matches("\\d+")) {
                    cont = "f";
                } else {
                    System.out.println("Error quantity!!!");
                    System.out.println("The quantity must be a number!");
                    System.out.println("Please re-enter!");
                    cont = "t";
                }
            } while (MyTools.MyTool.parseBoolean(cont));
            cont = "t";

            do {
                System.out.printf("Enter Item's production_month_year: ");
                ProductionMonthYear = MyTools.MyTool.normalizeDateStr(scString.nextLine());
                if (ProductionMonthYear.isEmpty()) {
                    System.out.println("The productionMonthYear is Empty!!!");
                    cont = "t";
                } else if (!ProductionMonthYear.matches("^[0-9/.-]+$")) {
                    System.out.println("The year must be a number!");
                    System.out.println("Please re-enter!");
                    cont = "t";
                } else if (!checkDate(ProductionMonthYear)) {
                    System.out.println("Error productionMonthYear!!!");
                    System.out.println("The Date must be like sample below:\n02/2024 or 02-2004 or 02.2004");
                    System.out.println("Please re-enter!");
                    cont = "t";
                } else {
                    cont = "f";
                }
            } while (MyTools.MyTool.parseBoolean(cont));
            cont = "t";

            String code = "RAM" + Type + "_" + (this.size() + 1);
            for (Ram r : this) {
                if (r.getCode().equals(code) && r.getType().equals(Type) && r.getBrand().equals(Brand) && r.getProductionMonthYear().equals(ProductionMonthYear) && r.getBus() == Integer.parseInt(Bus)) {
                    System.out.println("Item already exists.\nAdd fail!!!");
                    break;
                }
                if (!checkDate(ProductionMonthYear)) {
                    break;
                }
            }

            Ram newRam = new Ram(code, Type, Integer.parseInt(Bus), Brand, Long.parseLong(Quantity), ProductionMonthYear);
            this.add(newRam);
            System.out.println("Added new Item!!!");
            this.saveBinaryData(fileBinary);
            this.writeToFile(fileName);
            System.out.println("Add Item anymore(Y/N)?");
            cont = scString.nextLine();

        } while (MyTools.MyTool.parseBoolean(cont));
        System.out.println(
                "Added!!!");
    }

    public void displayAll(String fileName, String fileBinary) throws IOException {
        sortListOfRam();
        System.out.printf("%-75s %-12s %-75s\n", "", "LIST OF RAM", "");
        System.out.printf("%-25s %-25s %-25s %-25s %-25s %-25s %-25s\n", "Code", "Type", "Bus", "Brand", "Quantity", "Date of manufacture", "Active");
        for (Ram r : this) {
            System.out.printf("%-25s %-25s %-25s %-25s %-25s %-25s %-25s\n", r.getCode(), r.getType(), r.getBus(), r.getBrand(), r.getQuantity(), r.getProductionMonthYear(), r.isActive());
        }
    }

//---------------------------------------------------------------------------------------
//------------------------ Sort --------------------------------------------
    public void sortListOfRam() {
        Collections.sort(this, (Ram o1, Ram o2) -> {
            int cmp = o1.getBrand().compareTo(o2.getBrand());
            int cmp1 = o1.getType().compareTo(o2.getType());
            int cmp2 = o1.getBus() - o2.getBus();
            if (cmp != 0) {
                return cmp;
            } else if (cmp1 != 0) {
                return cmp1;
            }
            return cmp2;
        });
    }
//-----------------------------------------------------------------------------

//-----------------------Search Item-------------------------------------
    public Ram searchCode() {
        Ram result = new Ram();
        System.out.println("Enter code to search: ");
        String findCode = scString.nextLine();
        for (Ram t : this) {
            if (t.getCode().contains(findCode)) {
                result = t;
            }
        }
        return result;
    }

    public Ram searchCode(String code) {
        Ram result = new Ram();
        for (Ram thi : this) {
            if (thi.getCode().contains(code)) {
                result = thi;
            }
        }
        return result;
    }

    public void searchType() {
        ArrayList<Ram> result = new listOfRam();
        System.out.printf("Enter type to search: ");
        String findType = scString.nextLine();
        for (Ram t : this) {
            if (t.getType().contains(findType)) {
                result.add(t);
            }
        }
        if (result.isEmpty()) {
            System.out.println("Type doesn't exit!");
        } else {
            for (Ram ram : result) {
                System.out.println(ram);
            }
        }
        System.out.println("--------------------------------");
    }

    public void searchBus() {
        ArrayList<Ram> result = new listOfRam();
        boolean validInput = false;
        int findBus = 0;

        do {
            try {
                System.out.print("Enter bus to search: ");
                findBus = scInt.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number!");
                scInt.nextLine();
            }
        } while (!validInput);

        for (Ram t : this) {
            if (t.getBus() == findBus) {
                result.add(t);
            }
        }

        if (result.isEmpty()) {
            System.out.println("Bus doesn't exit!");
        } else {
            for (Ram ram : result) {
                System.out.println(ram);
            }
        }
        System.out.println("--------------------------------");
    }

    public void searchBrand() {
        ArrayList<Ram> result = new ArrayList<>();
        String findBrand;
        boolean found = false;

        do {
            System.out.print("Enter brand to search: ");
            findBrand = scString.nextLine().trim();

            for (Ram t : this) {
                if (t.getBrand().equalsIgnoreCase(findBrand)) {
                    result.add(t);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Brand doesn't exit!");
                break;
            }

        } while (!found);

        for (Ram ram : result) {
            System.out.println(ram);
        }
        System.out.println("--------------------------------");
    }

    public Ram searchRamByCode(String code) {
        Ram result = new Ram();
        result = searchCode(code);
        return result;
    }
//--------------------------------------------------------------------------------

//-----------------------Update Item--------------------------------------------- 
    public boolean updateRam(String fileName, String fileBinary) throws IOException, ParseException {

        String cont = "y";
        Ram update = new Ram();
        String Code, Type, Bus, Brand, Quantity, ProductionMonthYear;
        if (this.size() == 0) {
            System.out.println("Nothing data!!!");
            return false;
        }
        do {
            do {
                displayAll(fileName, fileBinary);
                System.out.printf("Enter code to Update: ");
                Code = scString.nextLine();
                update = searchRamByCode(Code);
                if (update.getType() == null && update.getBus() == 0 && update.getBrand() == null && update.getQuantity() == 0 && update.getProductionMonthYear() == null) {
                    System.out.println("Code does not exist!");
                    System.out.println("Here is your code: " + Code);
                    cont = "t";
                } else {
                    cont = "f";
                }
            } while (MyTools.MyTool.parseBoolean(cont));
            cont = "y";

            do {
                System.out.println("Old type RAM: " + update.getType());
                System.out.println("Press 'Enter' to re-enter the old value.");
                System.out.printf("Enter new type RAM: ");
                Type = scString.nextLine().toUpperCase();
                if (Type.isEmpty()) {
                    update.setType(update.getType());
                    cont = "f";
                } else {
                    update.setType(Type);
                    cont = "f";
                }
            } while (MyTools.MyTool.parseBoolean(cont));
            cont = "t";

            do {
                System.out.println("Old bus RAM: " + update.getBus());
                System.out.println("Press 'Enter' to re-enter the old value.");
                System.out.printf("Enter new bus RAM: ");
                Bus = scString.nextLine();
                if (Bus.isEmpty()) {
                    update.setBus(update.getBus());
                    cont = "f";
                } else if (Bus.matches("\\d+")) {
                    update.setBus(Integer.parseInt(Bus));
                    cont = "f";
                } else {
                    System.out.println("Error bus!!!");
                    System.out.println("Please re-enter!");
                    cont = "t";
                }
            } while (MyTools.MyTool.parseBoolean(cont));
            cont = "y";

            do {
                System.out.println("Old brand RAM: " + update.getBrand());
                System.out.println("Press 'Enter' to re-enter the old value.");
                System.out.printf("Enter new brand RAM: ");
                Brand = scString.nextLine();
                if (Brand.isEmpty()) {
                    update.setBrand(update.getBrand());
                    cont = "f";
                } else if (MyTools.MyTool.checkSpecialCharacter(Brand)) {
                    Brand = MyTools.MyTool.formatName(Brand);
                    update.setBrand(Brand);
                    cont = "f";
                } else {
                    System.out.println("Error brand!!!");
                    System.out.println("Please re-enter!");
                    cont = "f";
                }
            } while (MyTools.MyTool.parseBoolean(cont));
            cont = "y";

            do {
                System.out.println("Old quantity RAM: " + update.getQuantity());
                System.out.println("Press 'Enter' to re-enter the old value.");
                System.out.printf("Enter new quantity RAM: ");
                Quantity = scString.nextLine();
                if (Quantity.isEmpty()) {
                    update.setQuantity(update.getQuantity());
                    cont = "f";
                } else if (Quantity.matches("\\d+")) {
                    update.setQuantity(Long.parseLong(Quantity));
                    cont = "f";
                } else {
                    System.out.println("Error quantity!!!");
                    System.out.println("Please re-enter!");
                    cont = "t";
                }
            } while (MyTools.MyTool.parseBoolean(cont));
            cont = "y";

            do {
                System.out.println("Old productionMonthYear RAM: " + update.getProductionMonthYear());
                System.out.println("Press 'Enter' to re-enter the old value.");
                System.out.printf("Enter new productionMonthYear RAM [MM/yyyy]: ");
                ProductionMonthYear = MyTools.MyTool.normalizeDateStr(scString.nextLine());
                if (ProductionMonthYear.isEmpty()) {
                    update.setProductionMonthYear(update.getProductionMonthYear());
                    cont = "f";
                } else if (!ProductionMonthYear.matches("^[0-9/.-]+$")) {
                    System.out.println("The year must be a number!");
                    System.out.println("Please re-enter!");
                    cont = "t";
                } else if (checkDate(ProductionMonthYear)) {
                    update.setProductionMonthYear(ProductionMonthYear);
                    cont = "f";
                } else {
                    System.out.println("Error productionMonthYear!!!");
                    System.out.println("The productionMonthYear you entered: " + ProductionMonthYear);
                    System.out.println("Please re-enter!");
                    cont = "t";
                }
            } while (MyTools.MyTool.parseBoolean(cont));

            if (Type.isEmpty()) {
                update.setCode("RAM" + update.getType() + "_" + (this.size() + 1));
            } else {
                update.setCode("RAM" + Type + "_" + (this.size() + 1));
            }
        } while (MyTools.MyTool.parseBoolean(cont));

        this.saveBinaryData(fileBinary);
        this.writeToFile(fileName);
        return true;
    }

//-----------------------Delete Item--------------------------------------------
    public void deleteRamByCode(String fileName, String fileBinary) throws IOException {

        System.out.println("Current RAM modules:");
        displayAll(fileName, fileBinary);

        System.out.println("Enter RAM code to delete: ");
        String idSearch = sc.nextLine().trim();

        Ram ramToDelete = searchRAMByCode(idSearch);

        if (ramToDelete == null) {
            System.out.println("RAM module does not exist");
        } else {
            System.out.println("Do you want to delete this RAM module?");
            String input = MyTools.MyTool.checkDelete("Enter Y = Yes or N = No: ");

            if (input.trim().equalsIgnoreCase("Y")) {
                ramToDelete.setActive("Inactive");
                System.out.println("Delete successfully!");
                System.out.println("Here is your new list!");

                updateLists();

                this.saveBinaryData(fileBinary);
                this.writeToFile(fileName);

            } else {
                System.out.println("Deletion cancelled");
                System.out.println("--------------------------------");
            }
        }
    }

//    public listOfRam deleteRamByActive() {
//        listOfRam result = new listOfRam();
//        for (Ram r : this) {
//            if (r.isActive()) {
//                result.add(r);
//            }
//        }
//        System.out.println("Deleted Items.");
//        return result;
//    }
    private Ram searchRAMByCode(String code) {
        for (Ram r : this) {
            if (code.equals(r.getCode()) && r.isActive()) {
                return r;
            }
        }
        return null;
    }

    private void updateLists() {
        Iterator<Ram> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isActive()) {
                iterator.remove();
            }
        }
    }

//----------------------Read and Write files -------------------------------------
    public void readToFile(String fileName) throws IOException {
        File f = new File(fileName);
        if (!f.exists()) {
            f.createNewFile();
        }
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        try {
            String s = null;
            while (true) {
                s = br.readLine();
                if (s == null) {
                    break;
                } else {
                    String[] arr = s.split(",");
                    String type = arr[0];
                    int bus = Integer.parseInt(arr[1]);
                    String brand = arr[2];
                    int quantity = Integer.parseInt(arr[3]);
                    String date = arr[4];
                    String code = "RAM" + type + "_" + (this.size() + 1);
                    Ram result = new Ram(code, type, bus, brand, quantity, date);
                    this.add(result);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeToFile(String fileName) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fileName);
            for (Ram r : this) {
                pw.print(r.getType() + "," + r.getBus() + "," + r.getBrand() + "," + r.getQuantity() + "," + r.getProductionMonthYear() + "\n");
                pw.flush();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error writing file!!!");
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
            } catch (Exception e) {
                System.out.println("Error closing file!!!");
            }
        }
    }

    public void loadBinaryData(String fileName) {
        FileInputStream f = null;
        ObjectInputStream ob = null;
        try {
            File file = new File(fileName);
            if (file.exists()) {
                f = new FileInputStream(fileName);
                ob = new ObjectInputStream(f);
                this.addAll((ArrayList<Ram>) ob.readObject());
            }
        } catch (Exception e) {
            System.out.println("The list is empty, users can add more using the add RAM function.");
        } finally {
            try {
                if (f != null) {
                    f.close();
                }
                if (ob != null) {
                    ob.close();
                }
            } catch (Exception e) {
                System.out.println("Error closing file!!!");
            }
        }
    }

    public void saveBinaryData(String fileName) throws IOException {
        File fileBinary = new File(fileName);
        if (!fileBinary.exists()) {
            fileBinary.createNewFile();
        }
        FileOutputStream f = null;
        ObjectOutputStream oo = null;
        try {
            f = new FileOutputStream(fileName);
            oo = new ObjectOutputStream(f);
            oo.writeObject(this);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error saving file!!!");
        } finally {
            try {
                if (f != null) {
                    f.close();
                }
                if (oo != null) {
                    oo.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
