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
    public String encryptMessage(String message)
    {
        if (message.equals("")){return message;}
        fillBlock(message);

        String eM = "";
        int i =0;
        String segment = "";
        while (i < message.length()){
            segment += String.valueOf(message.charAt(i));

            if (i++ == message.length()){
                fillBlock(segment);
            }
            else if(segment.length()/6 == 0){
                fillBlock(segment);
                eM += encryptBlock();
                segment = "";
            }
        }




        String eM = "";
        int l = message.length()/(numCols*numRows);
        message += " ";
        String extra = message.substring((numCols*numRows*l+1));
        message = message.substring(0,numCols*numRows*l+1);

        for (int i =0; i<l;i++){
            eM+=  encryptBlock();

            fillBlock(message.substring(6*(i+1)));
        }
        fillBlock(extra);
        eM += encryptBlock();


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
        int index = 0;
        for (int c =0;c<numCols;c++){
            for (int r=0;r<numRows;r++){
                if (index++ < encryptedMessage.length()){
                    letterBlock[r][c] = String.valueOf(encryptedMessage.charAt(index));
                    index++;
                }
            }
        }

        String str = "";
        for (int r=0;r<numRows;r++){
            for (int c=0;c<numCols;c++){
                str += letterBlock[r][c];
            }
        }
        return removeDupeA(str);

    }

    private String removeDupeA(String eM){
        eM += "  ";
        for (int i =eM.length()-1; i>-1;i-=2){
            if (String.valueOf(eM.charAt(i)).equals("A")){
                eM = eM.substring(0,i) + eM.substring(i+1);
            }
        }
        return eM;
    }

}