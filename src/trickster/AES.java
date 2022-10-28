package trickster;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES {

    private SecretKey key;
    private int KeySize = 128;
    private int T_len = 128;
    private Cipher encryptionCipher;

    public  void init () throws Exception{
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(KeySize);
        key = generator.generateKey();
    }




    public String encrypt (String message) throws Exception {
        byte[] messageInBytes = message.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE,key);
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encoder(encryptedBytes);
    }



    public String decrypt (String encryptedMessage) throws Exception{
        byte[] messageInBytes = decoder(encryptedMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_len,encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE,key,spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);

        return new String(decryptedBytes);
    }

    private String encoder (byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }
    private byte[] decoder(String data){
        return Base64.getDecoder().decode(data);
    }

    public static void main(String[] args) {
        try {
            AES aes = new AES();
            aes.init();
            String encryptedMessage = aes.encrypt("hello world");
            String decryptedMessage = aes.decrypt(encryptedMessage);
            System.err.println("encrypted message = " + encryptedMessage);
            System.err.println("decrypted message = " + decryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
