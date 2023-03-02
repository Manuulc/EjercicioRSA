import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Descifrador {

    public static void main(String[] args) {

        byte [] emisorDescifrado;
        byte [] receptorDescifrado;

        try {
            PrivateKey clavePrivadaReceptor = Receptor.obtenerClavePrivada();
            PublicKey emisorClavePublica = Emisor.getClavePublica();

            Cipher cipherRec = Cipher.getInstance("RSA");
            Cipher cipherEm = Cipher.getInstance("RSA");

            cipherRec.init(Cipher.DECRYPT_MODE, clavePrivadaReceptor);
            cipherEm.init(Cipher.DECRYPT_MODE, emisorClavePublica);

            receptorDescifrado = cipherRec.doFinal(leerFichero().readAllBytes());
            emisorDescifrado = cipherEm.doFinal(receptorDescifrado);

            System.out.println(new String(emisorDescifrado, StandardCharsets.UTF_8));
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    private static FileInputStream leerFichero (){

        FileInputStream fis = null;
        try {
            fis = new FileInputStream("mensajeCifrado.txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fis;
    }
}
