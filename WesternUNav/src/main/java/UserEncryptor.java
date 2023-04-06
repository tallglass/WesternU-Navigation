/**
 *
 * @author orenj
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class UserEncryptor {

    private Key key;
    private String filename;

    public UserEncryptor(String password, String filename)  {
        try{
            this.filename = filename;
            KeyGenerator generator;
            generator = KeyGenerator.getInstance("DES");
            SecureRandom sec = new SecureRandom(password.getBytes());
            generator.init(sec);
            key = generator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public byte[] encrypt(String systemID, String password) throws Exception {
        String accountJson = "[{\"systemID\": \"" + systemID + "\", \"password\": \"" + password + "\"}]";
        try{
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedAccount = cipher.doFinal(accountJson.getBytes(StandardCharsets.UTF_8));
            //String encryptedID = cipher.doFinal(systemID);
            // Save the encrypted user account data, the key, and the IV to a file
            FileOutputStream fos = new FileOutputStream(filename);
            fos.write(encryptedAccount);
            fos.close();
            return encryptedAccount;
        } catch (Exception e) { 
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(byte[] encryptedAccount) throws Exception{
        try{
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            byte[] decryptedAccount = cipher.doFinal(encryptedAccount);
            // Check if the decrypted user account data matches the given username and password
            String accountJson = new String(decryptedAccount, StandardCharsets.UTF_8);
            //String expectedJson = "[{\"systemID\": \"" + systemID + "\", \"password\": \"" + password + "\"}]";
            return accountJson;
        } catch(Exception e ){
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        try {
            UserEncryptor encryptor = new UserEncryptor("password", "dataFiles/UserData.json");
            byte[] encryptedUser = encryptor.encrypt("orenj", "hello");
            System.out.println(encryptedUser);
            String decryptedUser = encryptor.decrypt(encryptedUser);
            System.out.println(decryptedUser);
            //System.out.println(encryptor.login("ojoffe", "hello","dataFiles/UserData.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}

/**
public class JSONEncryptor {
    private SecretKey key;
    private byte[] iv;
    
    public static void main(String[] args) {
        try {
            JSONEncryptor encryptor = new JSONEncryptor();
            encryptor.createAccount("ojoffe", "hello","dataFiles/UserData.json");
            System.out.println(encryptor.login("ojoffe", "hello","dataFiles/UserData.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public JSONEncryptor() {
        try {
            // Generate a secure random key to use for encryption
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom random = new SecureRandom();
            keyGen.init(256, random);
            this.key = keyGen.generateKey();
            
            // Generate a random initialization vector (IV)
            iv = new byte[16];
            random.nextBytes(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void createAccount(String systemID, String password, String fileName) throws Exception {
        // Create a user account with the given username and password
        String accountJson = "[{\"systemID\": \"" + systemID + "\", \"password\": \"" + password + "\"}]";

        // Encrypt the user account data using AES encryption
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] encryptedAccount = cipher.doFinal(accountJson.getBytes(StandardCharsets.UTF_8));

        // Save the encrypted user account data, the key, and the IV to a file
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(encryptedAccount);
        fos.write(key.getEncoded());
        fos.write(iv);
        fos.close();
    }

    public boolean login(String systemID, String password, String fileName) throws Exception {
        // Load the encrypted user account data, the key, and the IV from the file
        FileInputStream fis = new FileInputStream(fileName);
        byte[] encryptedAccount = fis.readAllBytes();
        byte[] keyBytes = fis.readNBytes(32);
        //byte[] iv = fis.readNBytes(16);
        fis.close();

        // Decrypt the user account data using AES decryption
        //SecretKey key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, this.key, new IvParameterSpec(this.iv));
        try {
            byte[] decryptedAccount = cipher.doFinal(encryptedAccount);
            // Check if the decrypted user account data matches the given username and password
            String accountJson = new String(decryptedAccount, StandardCharsets.UTF_8);
            String expectedJson = "[{\"systemID\": \"" + systemID + "\", \"password\": \"" + password + "\"}]";
            return accountJson.equals(expectedJson);
        } catch (BadPaddingException b) {
            return false;
        }
    }
}*/

