
package com.pgmacdesign.utils;

import com.pgmacdesign.internal.datamodels.CustomException;
import com.pgmacdesign.internal.datamodels.ErrorModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

    public static void printStuff(String str){
        if(str == null){
            return;
        }
        if(str.isEmpty()){
            return;
        }
        System.out.println(str);
    }


    public static final String REGEX_WEB_URL_ENCODING = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    public static final String REGEX_PASSWORD_PATTERN = "^\\S*(?=\\S*[a-zA-Z])(?=\\S*[0-9])\\S*$";


    public static final long ONE_SECOND = (1000);
    public static final long ONE_MINUTE = (1000*60);
    public static final long ONE_HOUR = (1000*60*60);
    public static final long ONE_DAY = (1000*60*60*24);

    public static final int DATE_MM_DD_YYYY = 4405;
    public static final int DATE_MM_DD_YY = 4406;

    /**
     * Keep numbers (0-9) only. Remove anything else
     * @param s Charsequence to analyze
     * @return String, containing only numbers
     */
    public static String keepNumbersOnly(CharSequence s) {
        try {
            return s.toString().replaceAll("[^0-9]", "");
        } catch (Exception e){
            return null;
        }
    }

    /**
     * Keep letters (A-Z & a-z) only. Remove anything else. Note, this will
     * remove letters like: ä, ñ, ж or λ, too, which, depending on who you ask, are letters too
     * @param s
     * @return
     */
    public static String keepLettersOnly(CharSequence s) {
        try {
            return s.toString().replaceAll("[^A-Za-z]", "");
        } catch (Exception e){
            return null;
        }
    }

    /**
     * Shortcut to check for equals ignore case where the strings passed may or may not be null
     * @param str1 String 1 to compare
     * @param str2 String 2 to compare
     * @return True if they are equal
     */
    public static boolean doesEqual(String str1, String str2){
        if(Utilities.isNullOrEmpty(str1) || Utilities.isNullOrEmpty(str2)){
            return false;
        }
        return str1.equals(str2);
    }

    /**
     * Shortcut to check for equals where the strings passed may or may not be null
     * @param str1 String 1 to compare
     * @param posStart int start position to check the strings for comparison
     * @param posEnd int end position to check the Strings for comparison
     * @param str2 String 2 to compare. This is the one that will be substringed for comparison
     * @return True if they are equal
     */
    public static boolean doesEqual(String str1, int posStart, int posEnd, String str2){
        if(Utilities.isNullOrEmpty(str1) || Utilities.isNullOrEmpty(str2)){
            return false;
        }
        String s2 = null;
        try {
            s2 = str2.substring(posStart, posEnd);
        } catch (Exception e){}
        return Utilities.doesEqual(str1, s2);
    }

    /**
     * Shortcut to check for equals ignore case where the strings passed may or may not be null
     * @param str1 String 1 to compare
     * @param str2 String 2 to compare
     * @return True if they are equal (ignoring case)
     */
    public static boolean doesEqualIgnoreCase(String str1, String str2){
        if(Utilities.isNullOrEmpty(str1) || Utilities.isNullOrEmpty(str2)){
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }

    /**
     * Shortcut to check for equals ignore case where the strings passed may or may not be null
     * @param str1 String 1 to compare
     * @param posStart int start position to check the strings for comparison
     * @param posEnd int end position to check the Strings for comparison
     * @param str2 String 2 to compare. This is the one that will be substringed for comparison
     * @return True if they are equal
     */
    public static boolean doesEqualIgnoreCase(String str1, int posStart, int posEnd, String str2){
        if(Utilities.isNullOrEmpty(str1) || Utilities.isNullOrEmpty(str2)){
            return false;
        }
        String s2 = null;
        try {
            s2 = str2.substring(posStart, posEnd);
        } catch (Exception e){}
        return Utilities.doesEqualIgnoreCase(str1, s2);
    }

    /**
     * Simple checker whether the first string contains the second string within positions passed
     * @param originalString String to check: IE, Patrick
     * @param posStart POS to start: IE, 0
     * @param posEnd POS to end: IE, 4
     * @param otherStringToCheckFor String: IE, Pat
     * @return true if it does contain it within those bounds, else false. If wanting to check
     * equality of those strings, see {@link Utilities#doesEqual(String, int, int, String)}
     */
    public static boolean doesStringContain(String originalString, int posStart,
                                            int posEnd, String otherStringToCheckFor){
        if(Utilities.isNullOrEmpty(originalString) ||
                Utilities.isNullOrEmpty(otherStringToCheckFor)){
            return false;
        }
        if(originalString.length() < posEnd){
            return false;
        }
        try {
            String s1 = otherStringToCheckFor.substring(posStart, posEnd);
            return originalString.contains(s1);
        } catch (Exception e){}
        return false;
    }

    public static boolean doesStringContain(String originalString,
                                            String otherStringToCheckFor){
        return doesStringContain(originalString, 0, (originalString.length()), otherStringToCheckFor);
    }


    /**
     * This will format a String passed in (7145551234) and convert it into standard US phone
     * number formatting ((714) 555-1234)
     * @param str String to be converted
     * @return Converted String
     */
    public static String formatStringLikePhoneNumber(String str){
        if(Utilities.isNullOrEmpty(str)){
            return str;
        }
        //Format out everything else
        str = str.trim();
        str = Utilities.keepNumbersOnly(str);
        if(Utilities.isNullOrEmpty(str)){
            return str;
        }

        if(str.length() < 3){
            return str;
        }

        // >=3 && < 7
        if(str.length() >= 3 && str.length() < 7){
            try {
                String phoneRawString = str;

                if(str.length() >= 4 ){
                    //Length 4 - 7
                    java.text.MessageFormat phoneMsgFmt = new java.text.MessageFormat("({0}) {1}");
                    String[] phoneNumArr = {
                            phoneRawString.substring(0, 3),
                            phoneRawString.substring(3)};

                    String formatted = phoneMsgFmt.format(phoneNumArr);
                    return formatted;
                } else {
                    //Length 3
                    return str; //This is to prevent 'stuck states' when they backspace
                }

            } catch (Exception e){}
        }

        // >=7 && < 10
        if(str.length() >= 7 && str.length() <= 10){
            try {
                String phoneRawString = str;
                String formatted = null;
                java.text.MessageFormat phoneMsgFmt = new java.text.MessageFormat("({0}) {1} - {2}");
                String[] phoneNumArr = {
                        phoneRawString.substring(0, 3),
                        phoneRawString.substring(3,6),
                        phoneRawString.substring(6)};
                formatted = phoneMsgFmt.format(phoneNumArr);
                return formatted;

            } catch (Exception e){}
        }

        // >=3 && < 7
        if(str.length() > 10){
            //check if the first number is one, if it is, sub out the first number
            String testFirst = str.substring(0, 1);
            if(testFirst.equalsIgnoreCase("1")){
                try {
                    String phoneRawString = str;
                    java.text.MessageFormat phoneMsgFmt=new java.text.MessageFormat("+{0}({1}) {2}-{3}");
                    String[] phoneNumArr={
                            phoneRawString.substring(0, 1),
                            phoneRawString.substring(1, 4),
                            phoneRawString.substring(4,7),
                            phoneRawString.substring(7)};

                    String formatted = phoneMsgFmt.format(phoneNumArr);
                    return formatted;
                } catch (Exception e){}
            } else {
                try {
                    String phoneRawString = str;
                    java.text.MessageFormat phoneMsgFmt=new java.text.MessageFormat("({0}) {1} - {2}");
                    String[] phoneNumArr={
                            phoneRawString.substring(0, 3),
                            phoneRawString.substring(3,6),
                            phoneRawString.substring(6)};

                    String formatted = phoneMsgFmt.format(phoneNumArr);
                    return formatted;
                } catch (Exception e){}
            }
        }
        return str;
    }

    /**
     * Convert String to title case. Sample:
     * "Bob went to the store" --> "Bob Went To The Store"
     * @param str String to convert
     * @return Converted String. If null or empty passed, passes back same
     */
    public static String toTitleCase(String str){
        if(isNullOrEmpty(str)){
            return str;
        }

        String[] arr = str.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    /**
     * Takes in a String and converts it to a number matching a phone number type. Example would be
     * you send int "ABC", it will return "222" as on a phone dial screen, ABC are all on the 2 key.
     * @param str String to parse
     * @return String of numbers to match converted number
     */
    public static String convertNameToPhoneNumber(String str){
        if(Utilities.isNullOrEmpty(str)){
            return null;
        }

        StringBuilder sb = new StringBuilder();
        int strLen = str.length();
        for (int currCharacter = 0; currCharacter < strLen; currCharacter++){
            String currentLetter = null;
            char ch = str.charAt(currCharacter);
            switch(ch)
            {
                case 'A' : case 'B' : case 'C' : currentLetter = "2"; break;
                case 'D' : case 'E' : case 'F' : currentLetter = "3"; break;
                case 'G' : case 'H' : case 'I' : currentLetter = "4"; break;
                case 'J' : case 'K' : case 'L' : currentLetter = "5"; break;
                case 'M' : case 'N' : case 'O' : currentLetter = "6"; break;
                case 'P' : case 'Q' : case 'R' : case 'S' : currentLetter = "7"; break;
                case 'T' : case 'U' : case 'V' : currentLetter = "8"; break;
                case 'W' : case 'X' : case 'Y' : case 'Z' : currentLetter = "9"; break;
            }
            if(currentLetter != null){
                sb.append(currentLetter);
            }
        }

        return sb.toString();
    }

    /**
     * Takes in a String and converts it to a string list matching a query using a phone keypad. An
     * example would be, send in 728 and it would return a list containing: {"PQRS", "ABC", "TUV"}
     * @param str String to parse into an integer. If it fails int parsing, returns null
     * @param includeBothCases Boolean, if true, it will add both upper and lower cases to string
     *                         list. IE, 728 would return: {"PpQqRrSs","AaBbCc","TtUuVv"}
     * @return List of Strings to match converted number
     */
    public static List<String> convertNumberToStringList(String str, boolean includeBothCases){
        if(Utilities.isNullOrEmpty(str)){
            return null;
        }

        List<String> toReturn = new ArrayList<>();
        int strLen = str.length();
        Integer intx = null;
        try {
            intx = Integer.parseInt(str);
        } catch (Exception e){}
        if(intx == null){
            return null;
        }
        for (int currCharacter = 0; currCharacter < strLen; currCharacter++){
            char ch = str.charAt(currCharacter);
            int currentNum = Character.getNumericValue(ch);
            switch(currentNum)
            {
                case 2:
                    if(includeBothCases) {
                        toReturn.add("AaBbCc");
                    } else {
                        toReturn.add("ABC");
                    }
                    break;

                case 3:
                    if(includeBothCases) {
                        toReturn.add("DdEeFf");
                    } else {
                        toReturn.add("DEF");
                    }
                    break;

                case 4:
                    if(includeBothCases) {
                        toReturn.add("GgHhIi");
                    } else {
                        toReturn.add("GHI");
                    }
                    break;

                case 5:
                    if(includeBothCases) {
                        toReturn.add("JjKkLl");
                    } else {
                        toReturn.add("JKL");
                    }
                    break;

                case 6:
                    if(includeBothCases) {
                        toReturn.add("MmNnOo");
                    } else {
                        toReturn.add("MNO");
                    }
                    break;

                case 7:
                    if(includeBothCases) {
                        toReturn.add("PpQqRrSs");
                    } else {
                        toReturn.add("PQRS");
                    }
                    break;

                case 8:
                    if(includeBothCases) {
                        toReturn.add("TtUuVv");
                    } else {
                        toReturn.add("TUV");
                    }
                    break;

                case 9:
                    if(includeBothCases) {
                        toReturn.add("WwXxYyZz");
                    } else {
                        toReturn.add("WXYZ");
                    }
                    break;

                case 0:
                case 1:
                default:
                    break;
            }
        }

        return toReturn;
    }

    /**
     * Checks a String to see if it can be easilly converted to a boolean. If it can be, it will
     * be returned as true or false, else, it will return null.
     * @param str String to check
     * @return Boolean, true or false, null if it cannot be parsed
     */
    public static Boolean convertStringToBoolean(String str){
        if(str == null){
            return null;
        }
        if(str.length() == 0){
            return null;
        }
        str = str.trim();
        //Checking for simple response, like T or F or 1, 0 in binary
        if(str.length() == 1){
            if(str.equalsIgnoreCase("t") || str.equalsIgnoreCase("1")){
                return true;
            } else if(str.equalsIgnoreCase("f") || str.equalsIgnoreCase("0")){
                return false;
            } else {
                return null;
            }
        } else {
            //Check for full words now
            if (str.equalsIgnoreCase("true")) {
                return true;
            } else if (str.equalsIgnoreCase("false")) {
                return false;
            } else {
                return null;
            }
        }
    }
    /**
     * Checks if a string passed in is numeric (IE pass in "2" and it will return true)
     * @param str String to check against
     * @return Return true if it is numeric, false if it is not
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }

        final int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
    /**
     * Increments a String (IE, converts a to b)
     * @param str String to convert
     * @return Returns a converted String
     */
    public static String incrementString(String str){
        if(Utilities.isNullOrEmpty(str)){
            return str;
        }
        if(str.equalsIgnoreCase("#")){
            return "A";
        }
        StringBuilder sb = new StringBuilder();
        for(char c: str.toCharArray()){
            sb.append(++c);
        }
        return sb.toString();
    }

    /**
     * Decrement a String (IE, converts b to a)
     * @param str String to convert
     * @return Returns a converted String
     */
    public static String decrementString(String str){
        if(Utilities.isNullOrEmpty(str)){
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for(char c: str.toCharArray()){
            sb.append(--c);
        }
        return sb.toString();
    }

    /**
     * Convert a String to a Java URI (Not Uri)
     * @param path String path to convert
     * @return converted java.net.URI
     */
    public static java.net.URI convertStringToJavaUri(String path){
        try {
            java.net.URI toReturn = new java.net.URI(path);
            return toReturn;
        } catch (URISyntaxException e){
            return null;
        } catch (Exception e){
            return null;
        }
    }

    /**
     * Convert a Java URI to a String (Not Uri)
     * @param uri java.net.URI to convert
     * @return String
     */
    public static String convertJavaUriToString(java.net.URI uri){
        return uri.toString();
    }

    /**
     * Shortens a String to X characters or less
     * @param str String to shorten
     * @param cutAt
     * @return If string is <= 100 in length, returns that, else, shortens to 100 and returns
     */
    public static String shortenToXChar(String str, int cutAt){
        //Check Params first
        if(str == null){
            return null;
        }
        int x = -1;
        x = cutAt;
        if(x == -1){
            return str;
        }

        String return_str = str;
        if(return_str.length() <= cutAt){
            return_str = return_str.trim(); //Cut out whitespace at end or beginning
            return return_str;
        } else {
            str = str.trim(); //Cut out whitespace
            return_str = str.substring(0, cutAt) + "..."; //Shorten to 100 characters and add ellipsis
            return return_str;
        }
    }

    public static String removeSpaces(String str){
        if(isNullOrEmpty(str)){
            return null;
        }
        str.replace(" ", "");
        str = str.trim();
        return  str;
    }

    /**
     * This checks if the password they entered contains an uppercase, lowercase, and a number
     * @param password The password being checked
     * @return Boolean, true if it is complicated enough, false if it is not
     */
    public static boolean checkForComplicatedPassword(String password){
        //Valid passed variable
        if(password == null || password.equalsIgnoreCase("")){
            return false;
        }
        password = password.trim(); //Trim it first

        if(password.length() < 6){
            return false; //Needs to be longer than or = to 6 characters
        } else {
            //Check for letters, then numbers, then special characters
            Matcher matcher;
            Pattern pattern;
            pattern = Pattern.compile(Utilities.REGEX_PASSWORD_PATTERN);
            matcher = pattern.matcher(password);
            boolean hasLetterAndNumber = matcher.matches();

            if(hasLetterAndNumber){
                //It is ok, has letters and numbers
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Encode a URI String using a regex
     * @param uriToEncode The String URI to encode
     * @return Returns an encoded URI String
     */
    public static String encodeURIStringWithRegex(String uriToEncode){
        try {
            //TEST
            uriToEncode = uriToEncode.replaceAll(Utilities.REGEX_WEB_URL_ENCODING, uriToEncode);

            return uriToEncode;
        } catch (Exception e){
            return null;
        }
    }

    /**
     * Encode a URI using a regex
     * @param uriToEncode The java.net.URI to encode
     * @return Returns an encoded URI String
     */
    public static String encodeURIStringWithRegex(java.net.URI uriToEncode){
        //Convert to String and pass to overloaded method
        try {
            return encodeURIStringWithRegex(convertJavaUriToString(uriToEncode));
        } catch (Exception e){
            return null;
        }
    }
    /**
     * Simple method to remove all spaces, parentheses, and hyphens
     * @param input String to adjust
     * @return formatted String
     */
    public static String formatPhoneRemoveFormatting(String input){
        if(input == null){
            return null;
        }
        input = input.replace("(", "");
        input = input.replace(")", "");
        input = input.replace("-", "");
        input = input.replace(" ", "");
        input = input.trim();
        return input;
    }

    /**
     * Formats by adding a hyphen for every 4 numbers (IE like a credit card)
     * @param s Charsequence being altered.
     * @return Return an altered String with hyphens in it
     */
    public static String formatNumbersAsCreditCard(CharSequence s) {
        int groupDigits = 0;
        String tmp = "";
        for (int i = 0; i < s.length(); ++i) {
            tmp += s.charAt(i);
            ++groupDigits;
            if (groupDigits == 4) {
                if(groupDigits == 16){
                } else {
                    tmp += "-";
                }
                groupDigits = 0;
            }
        }
        if(tmp.length() == 20){
            tmp = tmp.substring(0, tmp.length()-1); //Get rid of last digit
        }
        return tmp;
    }

    /**
     * Formats by adding forward slash every 2 numbers (IE like a credit card expiration date)
     * @param s Charsequence being altered.
     * @return Return an altered String with hyphens in it
     */
    public static String formatNumbersAsCreditCardExpiration(CharSequence s) {
        int groupDigits = 0;
        String tmp = "";
        for (int i = 0; i < s.length(); ++i) {
            tmp += s.charAt(i);
            ++groupDigits;
            if (groupDigits == 2) {
                tmp += "/";
                groupDigits = 0;
            }
        }
        if(tmp.length() > 5){
            tmp = tmp.substring(0, tmp.length()-1); //Get rid of last digit
        }
        return tmp;
    }

    /**
     * Checks if the passed String is null or empty
     * @param t object to check
     * @return boolean, true if it is null or empty, false if it is not.
     */
    public static <T> boolean isNullOrEmpty(T t){
        if(t == null){
            return true;
        }
        String str = t.toString();
        if(str.isEmpty()){
            return true;
        }
        if(str.length() == 0){
            return true;
        }
        return false;
    }

    public static String toUpperCase(String str){
        if(str == null){
            return str;
        }
        if(str.length() <= 0){
            return str;
        }
        str = str.toUpperCase();
        return str;
    }

    /**
     * Checks if the passed String is null or empty
     * @param str String to check
     * @return Boolean, true if it is null or empty, false if it is not.
     */
    public static boolean isNullOrEmpty(String str){
        if(str == null){
            return true;
        }
        if(str.isEmpty()){
            return true;
        }
        if(str.length() == 0){
            return true;
        }
        if(str.equalsIgnoreCase(" ")){
            return true;
        }
        return false;
    }

    /**
     * Builds a String via the passed String array. If the individual String is null or empty,
     * it skips it. If the delimiter is empty, skips that too
     * @param args String array to use
     * @param delimiter Delimiter to use (IE , or a space or _ or / or | )
     * @return Fully completed and written String. Example:
     *         String str = buildAStringFromUnknowns(new String[]{"2016", "07", "04"}, "/");
     *         str would print as: "2016/07/04"
     */
    public static String buildAStringFromUnknowns(String[] args, String delimiter){
        StringBuilder sb = new StringBuilder();
        sb.append(""); //So that it will always return something
        try {
            for (int i = 0; i < args.length; i++) {
                String str = args[i];

                //Boot out nulls and blanks
                if (str == null) {
                    continue;
                }
                if (str.equalsIgnoreCase("")) {
                    continue;
                }

                sb.append(str);
                if (i < args.length - 1) {
                    boolean checkNext = true;
                    try {
                        String str1 = args[(i+1)];
                        if(str1 == null){
                            checkNext = false;
                        } else {
                            if (str1.isEmpty()) {
                                checkNext = false;
                            }
                        }
                    } catch (Exception e){

                    }
                    if(checkNext) {
                        //Format via delimiter
                        if (delimiter != null) {
                            sb.append(delimiter);
                        }
                    }
                }
            }
        } catch (Exception e){}

        return sb.toString();
    }

    /**
     * Convert an input stream to a STring
     * @param is InputStream to convert
     * @return
     */
    public static String convertInputStreamToString(InputStream is){
        if(is == null){
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder out = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                out.append(line);
                out.append("\r\n");
            }
            return out.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks if any of the Strings passed via an array are null
     * @param args String array to check
     * @return boolean, true if any are null
     */
    public static boolean anyNullsInStrings(String[] args){
        for(String str : args){
            if(str == null){
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if any of the Strings passed via an array are null or are empty ("");
     * @param args String array to check
     * @return boolean, true of any are null or are empty
     */
    public static boolean anyNullsOrEmptyInStrings(String[] args){
        for(String str : args){
            if(Utilities.isNullOrEmpty(str)){
                return true;
            }
        }
        return false;
    }

    /**
     * Overloaded to allow for no charset to be passed
     */
    public static byte[] convertStringToBytes(String str){
        return convertStringToBytes(str, null);
    }

    /**
     * Convert a String to a byte array
     * @param str String to convert
     * @param charSetPreference {@link java.nio.charset.Charset} IE, "UTF-8"
     * @return byte[]
     */
    public static byte[] convertStringToBytes(String str,
                                              String charSetPreference){
        if(Utilities.isNullOrEmpty(str)){
            return null;
        }
        byte[] s;
        if(Utilities.isNullOrEmpty(charSetPreference)){
            s = str.getBytes();
        } else {
            try {
                s = str.getBytes(charSetPreference);
            } catch (UnsupportedEncodingException e) {
                s = str.getBytes();
            }
        }
        return s;
    }

    /**
     * Convert a byte array to a hex string
     * @param buf byte array to convert
     * @return Hex String
     */
    public static String toHex(byte[] buf) {
        return toHex(buf, false);
    }

    /**
     * Convert a byte array to a hex string (boolean to capitalize all chars)
     * @param buf byte array to convert
     * @param capitalize if true, all chars will be capitalized
     * @return String
     */
    public static String toHex(byte[] buf, boolean capitalize) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2*buf.length);
        for (int i = 0; i < buf.length; i++) {
            if(capitalize) {
                result.append(Constants.HEX.charAt((buf[i] >> 4) & 0x0f))
                        .append(Constants.HEX.charAt(buf[i] & 0x0f));
            } else {
//                result.append(Integer.toHexString(0xFF & buf[i]));
                String str = Integer.toHexString(0xFF & buf[i]);
                while (str.length() < 2)
                    str = "0" + str;
                result.append(str);
            }
        }
        return result.toString();
    }

    /**
     * Convert a hex string to a byte array
     * @param hexString
     * @return
     */
    public static byte[] toByte(String hexString) {
        int len = hexString.length()/2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        return result;
    }
    
    /**
     * For converting 2 times into SMS-like format where it shows "how long ago" someone did
     * something. IE, when someone sends you a message 'just now' vs 'an hour ago'
     * @param timeEventHappened Time the event happened
     * @param currentTime current time (if null, it will just get current time)
     * @return String of the converted date. If something fails, it will return the time the
     *         event happened to string (date.toString())
     */
    public static String convertDatesToSMSTimeMeasurements(Date timeEventHappened, Date currentTime){
        if(timeEventHappened == null){
            return null;
        }
        if(currentTime == null){
            currentTime = new Date();
        }

        long eventTimeMil = timeEventHappened.getTime();
        long currentTimeMil = currentTime.getTime();
        long time = currentTimeMil - eventTimeMil;
        if(time < 0){
            return "In the future";
        } else {
            if(time <= (ONE_MINUTE)){
                return "A moment ago";
            } else if (time <= (ONE_MINUTE * 2)){
                return "1 minute ago";
            } else if (time <= (ONE_MINUTE * 3)){
                return "2 minutes ago";
            } else if (time <= (ONE_MINUTE * 4)){
                return "3 minutes ago";
            } else if (time <= (ONE_MINUTE * 5)){
                return "4 minutes ago";
            } else if (time <= (ONE_MINUTE * 6)){
                return "5 minutes ago";
            } else if (time <= (ONE_MINUTE * 7)){
                return "6 minutes ago";
            } else if (time <= (ONE_MINUTE * 8)){
                return "7 minutes ago";
            } else if (time <= (ONE_MINUTE * 9)){
                return "8 minutes ago";
            } else if (time <= (ONE_MINUTE * 10)){
                return "9 minutes ago";
            } else if (time <= (ONE_MINUTE * 11)){
                return "10 minutes ago";
            } else if (time <= (ONE_MINUTE * 12)){
                return "11 minutes ago";
            } else if (time <= (ONE_MINUTE * 13)){
                return "12 minutes ago";
            } else if (time <= (ONE_MINUTE * 14)){
                return "13 minutes ago";
            } else if (time <= (ONE_MINUTE * 15)){
                return "14 minutes ago";
            } else if (time <= (ONE_MINUTE * 16)){
                return "15 minutes ago";
            } else if (time <= (ONE_MINUTE * 29)){
                return "20 minutes ago";
            } else if (time <= (ONE_MINUTE * 59)){
                return "30 minutes ago";
            } else if (time <= (ONE_HOUR * 2)){
                return "An hour ago";
            } else if (time <= (ONE_HOUR * 3)){
                return "2 hours ago";
            } else if (time <= (ONE_HOUR * 4)){
                return "3 hours ago";
            } else if (time <= (ONE_HOUR * 5)){
                return "4 hours ago";
            } else if (time <= (ONE_HOUR * 6)){
                return "5 hours ago";
            } else if (time <= (ONE_HOUR * 7)){
                return "6 hours ago";
            } else if (time <= (ONE_HOUR * 8)){
                return "7 hours ago";
            } else if (time <= (ONE_HOUR * 9)){
                return "8 hours ago";
            } else if (time <= (ONE_HOUR * 10)){
                return "9 hours ago";
            } else if (time <= (ONE_HOUR * 11)){
                return "10 hours ago";
            } else if (time <= (ONE_HOUR * 12)){
                return "11 hours ago";
            } else if (time <= (ONE_HOUR * 13)){
                return "12 hours ago";
            } else if (time <= (ONE_HOUR * 24)){
                return "12 hours ago";
            } else if (time <= (ONE_DAY * 2)){
                return "Yesterday";
            } else {
                return timeEventHappened.toString();
            }
        }
    }


    /**
     * Convert Inches to feet
     * @param i
     * @return
     */
    public static double convertInchesToFeet(double i){
        double dbl = i * 0.08333333;
        return dbl;
    }

    /**
     * Convert Feet into Inches
     * @param f
     * @return
     */
    public static double convertFeetToInches(double f){
        double dbl = f / 0.08333333;
        return dbl;
    }

    /**
     * Convert Miles to Kilometers
     * @param m
     * @return
     */
    public static double convertMilesToKilometers(double m){
        double dbl = (m * 1.609344);
        return dbl;
    }

    /**
     * Convert Kilometers to miles
     * @param k
     * @return
     */
    public static double convertKilometersToMiles(double k){
        double dbl = (k * 0.621371);
        return dbl;
    }

    /**
     * Convert feet to meters
     * @param f
     * @return
     */
    public static double convertFeetToMeters(double f){
        double dbl = f * 0.3048;
        return dbl;
    }

    /**
     * Convert meters to feet
     * @param m
     * @return
     */
    public static double convertMetersToFeet(double m){
        double dbl = m / 0.3048;
        return dbl;
    }

    /**
     * Convert feet to miles
     * @param f
     * @return
     */
    public static double convertFeetToMiles(double f){
        double dbl = (f * 0.00019);
        return dbl;
    }

    /**
     * Convert miles to feet
     * @param m
     * @return
     */
    public static double convertMilesToFeet(double m){
        double dbl = (m / 0.00019);
        return dbl;
    }

    /**
     * Convert Celsius to Fahrenheit
     * @param c
     * @return
     */
    public static double convertCelsiusToFahrenheit(double c){
        double dbl = ((c * 9) / 5) + 32;
        return dbl;
    }

    /**
     * Convert Fahrenheit to Celsius
     * @param f
     * @return
     */
    public static double convertFahrenheitToCelsius(double f){
        double dbl = ((f - 32) * 5) / 9;
        return dbl;
    }
    /**
     * Checks if a string passed in is a number (IE, "12345" would return true)
     * @param str String to check
     * @return true if number, false if not
     */
    public static boolean isNumber(String str){
        if(Utilities.isNullOrEmpty(str)){
            return false;
        }

        str = str.trim();
        for(int i = 0; i < str.length(); i++){
            boolean bool = Character.isDigit(str.charAt(i));
            if(!bool){
                return false;
            }
        }
        return true;
    }

    /**
     * Rounds a double via passed in amount and places
     * @param value
     * @param places
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Rounds a double down via passed in amount and places
     * @param value
     * @param places
     * @return
     */
    public static double roundDown(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.FLOOR);
        return bd.doubleValue();
    }

    /**
     * Rounds a double up via passed in amount and places
     * @param value
     * @param places
     * @return
     */
    public static double roundUp(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.CEILING);
        return bd.doubleValue();
    }

    /**
     * Simple method for getting a Long and converting it to a non-null number
     * @param lngX
     * @return long value, 0 if it is null
     */
    public static long getLong(Long lngX){
        if(lngX == null){
            return 0;
        } else {
            return lngX;
        }
    }

    /**
     * Simple method for getting an Integer and converting it to a non-null number
     * @param intx
     * @return Int value, 0 if int is null
     */
    public static int getInt(Integer intx){
        if(intx == null){
            return 0;
        } else {
            return intx;
        }
    }

    /**
     * Simple method for preventing Null Pointer Exceptions when working with Floats
     * @param flt
     * @return Float value, 0 if float was null
     */
    public static float getFloat(Float flt){
        if(flt == null){
            return 0;
        } else {
            return flt;
        }
    }

    /**
     * Simple method for preventing Null Pointer Exceptions when working with Doubles
     * @param dbl
     * @return Float value, 0 if float was null
     */
    public static double getDouble(Double dbl){
        if(dbl == null){
            return 0;
        } else {
            return dbl;
        }
    }

    /**
     * Simple formatter to add in commas for formatting. Obtained from:
     * https://stackoverflow.com/questions/3672731/how-can-i-format-a-string-number-to-have-commas-and-round
     * @param number number to round. If unparseable, will return number
     * @return Formatted String. IE, 44234 would return as 44,234
     */
    public static String formatNumberAddCommas(long number){
        DecimalFormat formatter = new DecimalFormat("#,###");
        try {
            return formatter.format(number);
        } catch (Exception e){
            e.printStackTrace();
            return number + "";
        }
    }

    /**
     * Simple formatter to add in commas for formatting. Obtained from:
     * https://stackoverflow.com/questions/3672731/how-can-i-format-a-string-number-to-have-commas-and-round
     * @param number number to round. If unparseable, will return number
     * @return Formatted String. IE, 44234 would return as 44,234
     */
    public static String formatNumberAddCommas(double number){
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        try {
            return formatter.format(number);
        } catch (Exception e){
            e.printStackTrace();
            return number + "";
        }
    }

    /**
     * This used when a double ends in 0 (IE 100.0) and you want to remove the significant figures
     * after the decimal point (assuming they end in zero)
     * @param value The double to convert
     * @param addDollarSign boolean, if null passed, nothing, if true passed, will add
     *                      a $ to the beginning
     * @return A String, formatted correctly. Will look like this: 104.44 or $99.
     *         if the last 2 are 00, it will remove the significant figures after
     *         the decimal as well as the decimal itself
     */
    public static String convertDoubleToStringAddZero(double value, Boolean addDollarSign){
        String str = Double.toString(value);
        if(str == null){
            return null;
        }
        //String ending = str.substring((str.length()-2));
        String ending = str.substring((str.length()-2), (str.length()-1));
        if(ending.equalsIgnoreCase(".")){
            str = str + "0";
        }
        if(addDollarSign != null){
            if(addDollarSign){
                str = "$"+str;
            }
        }
        ending = str.substring((str.length() - 3));
        if(ending.equalsIgnoreCase(".00")){
            str = str.replace(".00", "");
        }

        return str;
    }
    /**
     * This used when a double ends in 0 (IE 100.0) and you want 2 decimal places instead (IE 100.00)
     * @param value The double to convert
     * @param addDollarSign boolean, if null passed, nothing, if true passed, will add
     *                      a $ to the begining
     * @return A String, formatted correctly. Will look like this: 104.44 or $99.40
     */
    public static String convertDoubleToStringAddZeroNoRemove(double value, Boolean addDollarSign){
        String str = Double.toString(value);
        String ending = str.substring((str.length()-2), (str.length()-1));
        if(ending.equalsIgnoreCase(".")){
            str = str + "0";
        }
        if(Utilities.isTrue(addDollarSign)){
            str = "$"+str;
        }
        return str;
    }

    /**
     * Simple checker for bools 
     * @param bool
     * @return
     */
    public static boolean isTrue(Boolean bool){
        if(bool == null){
            return false;
        } else {
            return bool;
        }
    }

    /**
     * Get a random number between a min and max
     * @param min lower end min
     * @param max higher end max
     * @return
     */
    public static int getRandomInt(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    /**
     * Checks a list for either being empty or containing objects within it
     * @param myArray array to check
     * @param <T> T extends object
     * @return boolean, true if it is null or empty, false it if is not
     */
    public static <T extends Object> boolean isArrayNullOrEmpty(T[] myArray){
        if(myArray == null){
            return true;
        }
        if(myArray.length <= 0){
            return true;
        }
        return false;
    }

    /**
     * Checks a list for either being empty or containing objects within it
     * @param myList List to check
     * @return Boolean, true if it is null or empty, false it if is not
     */
    public static boolean isListNullOrEmpty(List<?> myList){
        if(myList == null){
            return true;
        }
        if(myList.size() <= 0){
            return true;
        }
        return false;
    }

    /**
     * Checks a set for either being empty or containing objects within it
     * @param mySet set to check
     * @return Boolean, true if it is null or empty, false it if is not
     */
    public static boolean isSetNullOrEmpty(Set<?> mySet){
        if(mySet == null){
            return true;
        }
        if(mySet.size() <= 0){
            return true;
        }
        return false;
    }


    /**
     * Checks a map for either being empty or containing objects within it
     * @param myMap map to check
     * @return Boolean, true if it is null or empty, false it if is not
     */
    public static boolean isMapNullOrEmpty(Map<?, ?> myMap){
        if(myMap == null){
            return true;
        }
        if(myMap.size() <= 0){
            return true;
        }
        return false;
    }

    /**
     * Print out a list of objects
     * @param myList
     */
    public static void printOutList(List<?> myList){
        if(isListNullOrEmpty(myList)){
            return;
        }
        int x = 0;
        for(Object item : myList){
            try {
                L.m(item.toString());
            } catch (Exception e){
                L.m("Could not print position " + x);
            }
            x++;
        }
    }

    /**
     * Print out an array of objects
     * @param myArray Array of objects
     */
    public static void printOutArray(Object[] myArray){
        if(isArrayNullOrEmpty(myArray)){
            return;
        }
        int x = 0;
        for(Object item : myArray){
            try {
                L.m(item.toString());
            } catch (Exception e){
                L.m("Could not print position " + x);
            }
            x++;
        }
    }

    /**
     * Print out a hashmap
     * @param myMap Map of type String, ?
     */
    public static void printOutHashMap(Map<?,?> myMap){
        if(myMap == null){
            return;
        }
        L.m("Printing out entire Hashmap:\n");
        for(Map.Entry<?,?> map : myMap.entrySet()){
            Object key = map.getKey();
            Object value = map.getValue();
            L.m(key.toString() + ", " + value.toString());
        }
        L.m("\nEnd printing out Hashmap:");
    }



    /**
     * Remove nulls from a list of list of objects
     * @param nestedListObject
     * @return remove the nulls and return objects
     */
    public static List<List<Object>> removeNullsFromLists(List<List<?>> nestedListObject){
        List<List<Object>> listsToReturn = new ArrayList<>();
        for(int i = 0; i < nestedListObject.size(); i++){
            try {
                List<Object> obj = listsToReturn.get(i);
                if(obj == null){
                    continue;
                }
                obj = removeNullsFromList(obj);
                if(obj != null){
                    listsToReturn.add(obj);
                }
            } catch (Exception e){}
        }
        return listsToReturn;
    }

    /**
     * Remove nulls from a list of objects
     * @param myList
     * @return remove the nulls and return objects
     */
    public static List<Object> removeNullsFromList (List<?> myList){
        if(myList == null){
            return null;
        }
        List<Object> listToReturn = new ArrayList<>();
        for(int i = 0; i < myList.size(); i++){
            try {
                Object obj = myList.get(i);
                if(obj != null){
                    listToReturn.add(obj);
                }
            } catch (Exception e){}
        }
        return listToReturn;
    }

    public static CustomException buildCustomException(String message, String error, Integer i) {
        CustomException customException = new CustomException(message);
        customException.setError(error);
        customException.setErrorCode(i);
        return customException;
    }

    /*
    public static Class getClass(String classname) throws ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(classLoader == null)
            classLoader = Singleton.class.getClassLoader();
        return (classLoader.loadClass(classname));
    }
    */

    //Not used atm. Re-working to fix exception issues. Supposed to work via reflection.
    //Link: http://stackoverflow.com/questions/6591665/merging-two-objects-in-java
    private Object mergeObjects(Object obj, Object update){
        if(!obj.getClass().isAssignableFrom(update.getClass())){
            return null;
        }

        Method[] methods = obj.getClass().getMethods();

        for(Method fromMethod: methods){
            if(fromMethod.getDeclaringClass().equals(obj.getClass())
                    && fromMethod.getName().startsWith("get")){

                String fromName = fromMethod.getName();
                String toName = fromName.replace("get", "set");

                try {
                    Method toMetod = obj.getClass().getMethod(toName, fromMethod.getReturnType());
                    Object value = fromMethod.invoke(update, (Object[])null);
                    if(value != null){
                        toMetod.invoke(obj, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Simple method to allow for less null checks in code
     * @param atomicInteger {@link AtomicInteger}
     * @return int. -1 if Atomic Integer is null, actual int else.
     */
    public static int getInt(AtomicInteger atomicInteger){
        if(atomicInteger == null){
            return -1;
        } else {
            return atomicInteger.get();
        }
    }

    public static ErrorModel buildErrorMessage(){
        return new ErrorModel("An unknown error has occurred",
                "An unknown error has occurred. Please try your request again in a few moments.",
                400);
    }

    public static ErrorModel buildErrorMessage(String error, String description, int code){
        return new ErrorModel(error, description, code);
    }

}
