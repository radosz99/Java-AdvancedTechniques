package cipher;

import javax.crypto.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.*;

public class Encryptor {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Cipher cipher;
    private KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    public Encryptor() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes("public.der"));
        publicKey = keyFactory.generatePublic(publicSpec);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(readFileBytes("private.der"));
        privateKey = keyFactory.generatePrivate(keySpec);
    }

    public byte[] readFileBytes(String filename) throws IOException
    {
        Path path = Paths.get(filename);
        return Files.readAllBytes(path);
    }

    public byte[] encrypt(byte[] plaintext) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plaintext);
    }

    public byte[] decrypt(String filename) throws IOException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(readFileBytes(filename));
    }

    public static void saveToFile(byte[] bytes, String filename){
        File file = new File(filename);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);
            fos.write(bytes);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        }
        catch (IOException ioe) {
            System.out.println("Exception while writing file " + ioe);
        }
        finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            }
            catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }
    }

}
