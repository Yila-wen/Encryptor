import java.util.Arrays;

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

    private String removeDupeA(String eM){
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