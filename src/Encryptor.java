import java.util.Arrays;
import java.util.ArrayList;

public class Encryptor
{
    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;

    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;

    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;

    /** Constructor*/
    public Encryptor(int r, int c)
    {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }

    public String[][] getLetterBlock()
    {
        return letterBlock;
    }

    /** Places a string into letterBlock in row-major order.
     *
     *   @param str  the string to be processed
     *
     *   Postcondition:
     *     if str.length() < numRows * numCols, "A" in each unfilled cell
     *     if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str)
    {
        int index = 0;

        for (int r=0;r<numRows;r++){
            for (int c=0;c<numCols;c++){
                if (index >= str.length()){
                    letterBlock[r][c]="A";
                }
                else letterBlock[r][c] = String.valueOf(str.charAt(index));
                index++;

                }
            }
    }

    /** Extracts encrypted string from letterBlock in column-major order.
     *
     *   Precondition: letterBlock has been filled
     *
     *   @return the encrypted string from letterBlock
     */
    public String encryptBlock()
    {
        String str ="";
        for (int c =0;c<numCols;c++){
            for (int r =0;r<numRows;r++){
                str += letterBlock[r][c];
            }
        }
        return str;
    }

    /** Encrypts a message.
     *
     *  @param message the string to be encrypted
     *
     *  @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message) {
        if (message.equals("")) {
            return message;
        }
        fillBlock(message);

        String eM = "";
        String tempMsg = message;
        int i = 0;
        String segment = "";
        while (i < tempMsg.length()) {
            segment += String.valueOf(tempMsg.charAt(i));
            i++;

            if (i == tempMsg.length()){
                fillBlock(segment);
                eM += encryptBlock();
            }

            else if ((segment.length())%(numCols*numRows) == 0){
                fillBlock(segment);
                eM += encryptBlock();
                segment = "";
            }

        }

        return eM;
    }

    /**  Decrypts an encrypted message. All filler 'A's that may have been
     *   added during encryption will be removed, so this assumes that the
     *   original message (BEFORE it was encrypted) did NOT end in a capital A!
     *
     *   NOTE! When you are decrypting an encrypted message,
     *         be sure that you have initialized your Encryptor object
     *         with the same row/column used to encrypted the message! (i.e.
     *         the “encryption key” that is necessary for successful decryption)
     *         This is outlined in the precondition below.
     *
     *   Precondition: the Encryptor object being used for decryption has been
     *                 initialized with the same number of rows and columns
     *                 as was used for the Encryptor object used for encryption.
     *
     *   @param encryptedMessage  the encrypted message to decrypt
     *
     *   @return  the decrypted, original message (which had been encrypted)
     *
     *   TIP: You are encouraged to create other helper methods as you see fit
     *        (e.g. a method to decrypt each section of the decrypted message,
     *         similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage)
    {
        String str = "";
        int i = 0;

        while (i < encryptedMessage.length()) {


            for (int c = 0; c < numCols; c++) {
                for (int r = 0; r < numRows; r++) {
                    letterBlock[r][c] = String.valueOf(encryptedMessage.charAt(i));
                    i++;
                    if (i == encryptedMessage.length()){
                        r= numRows;
                        c=numCols;
                    }
                }
            }

            for (int r = 0; r < numRows; r++) {
                for (int c = 0; c < numCols; c++) {
                    str += letterBlock[r][c];
                }
            }
        }



        return removeDupeA(str);
    }

    public String superEncryptor(String msg){
        String lMsg = "";

        String fMsg = encryptMessage(msg);

        for (int i =0;i<fMsg.length();i++){
            int temp = alphabetNumValue(String.valueOf(fMsg.charAt(i)));
            if (temp == 0){lMsg += " 0";}
            else if (temp != -1){
                lMsg += temp;
            }
            else lMsg+=String.valueOf(fMsg.charAt(i));
        }
        return lMsg;
    }

    public String superDecrypter(String msg){
        String fMsg = "";
        for (int i = 0;i<fMsg.length();i++){
            fMsg += numValueAlphabet(String.valueOf(fMsg.charAt(i)));
        }


     return fMsg;
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private static int alphabetNumValue(String letter){
        final String[] ALPHABET = {
                " ","a","b","c","d","e","f","g","h","i",
                "j","k","l","m","n","o","p","q","r",
                "s","t","u","v","w","x","y","z",
                "A","B","C","D","E","F","G","H","I",
                "J","K","L","M","N","O","P","Q","R",
                "S","T","U","V","W","X","Y","Z"
        };
        for (int i=0;i< ALPHABET.length;i++){
            if (letter.equals(ALPHABET[i])){
                return i;
            }
        }

        return -1;
    }
    private static String numValueAlphabet(String x){
        final String[] ALPHABET = {
                " ","a","b","c","d","e","f","g","h","i",
                "j","k","l","m","n","o","p","q","r",
                "s","t","u","v","w","x","y","z",
                "A","B","C","D","E","F","G","H","I",
                "J","K","L","M","N","O","P","Q","R",
                "S","T","U","V","W","X","Y","Z"
        };
        if(isNumeric(x));{
        if (Integer.parseInt(x) > -1 && Integer.parseInt(x) < ALPHABET.length){
            return ALPHABET[Integer.parseInt(x)];
        }
        }
        return x;
    }


    public static String removeDupeA(String eM){
        String curr = "";
        String next = "";

        for (int i =eM.length()-1;i>-1;i--){

            curr = String.valueOf(eM.charAt(i));
            next = String.valueOf(i-1);

            if (curr.equals("A")){
                eM = eM.substring(0,i);
            }
            else if (!curr.equals(next))
            {i = -2;}
        }

        return eM;
    }

}