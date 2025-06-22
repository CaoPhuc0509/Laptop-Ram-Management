/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyTools;

import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Cao Phuc
 */
public class MyTool {
    public static final Scanner sc = new Scanner(System.in);
    
    public static boolean parseBoolean(String input) {
        input = input.trim().toUpperCase();
        char c = input.charAt(0);
        return c == 'T' || c == '1' || c == 'Y';
    }
    
    public static boolean checkSpecialCharacter(String name) {
        if (name.matches("^[a-zA-Z\\s]+$")) {
            return true;
        } else {
            return false;
        }
    }
    
    public static String formatName(String input) {
        input = input.trim();
        input = input.toLowerCase();
        String[] words = input.split("\\s+");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                formattedName.append(Character.toUpperCase(word.charAt(0)));
                formattedName.append(word.substring(1));
                formattedName.append(" ");
            }
        }
        return formattedName.toString().trim();
    }
        
    public static String normalizeDateStr(String dateStr) {
        String result = dateStr.replaceAll("[\\s]+", "");
        result = result.replaceAll("[./-]+", "/");
        return result;
    }
    
        public static String checkDelete(String str){
        String value;
        while (true) {
            try {
                System.out.print(str);
                value = sc.nextLine();
                if (value.trim().isEmpty()) {
                    throw new Exception("Please input value!");
                }
                if (value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("N")) {
                    return value;
                } else {
                    throw new Exception("Please input Y = Yes Or N = No!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
