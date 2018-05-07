package Lab7.Encryptor;

import java.io.File;

import javax.crypto.Cipher;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	///EXAMPLE USAGE
    	String key = "This is a secret";
    	File inputFile = new File("text.txt");
    	File encryptedFile = new File("text.encrypted");
    	File decryptedFile = new File("decrypted-text.txt");
    		
    	try {
    	     Crypto.fileProcessor(Cipher.ENCRYPT_MODE,key,inputFile,encryptedFile);
    	     Crypto.fileProcessor(Cipher.DECRYPT_MODE,key,encryptedFile,decryptedFile);
    	     System.out.println("Sucess");
    	 } catch (Exception ex) {
    	     System.out.println(ex.getMessage());
                 ex.printStackTrace();
    	 }
    }
}
