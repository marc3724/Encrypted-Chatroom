/*
package trickster;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

public class RSA {


    public static void main(String[] args) {

        KeyPairGenerator generator = null;
        KeyPair pair = generator.generateKeyPair();

        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        {
            try {
                generator = KeyPairGenerator.getInstance("RSA");
                generator.initialize(2048);

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        try (FileOutputStream fos = new FileOutputStream("public.key")) {
            fos.write(publicKey.getEncoded());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
*/
