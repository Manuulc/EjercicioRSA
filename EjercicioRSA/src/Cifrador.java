import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Cifrador {

    public static void main(String[] args) {

        byte [] cifradoPriv;
        byte [] cifradoPublic;

        try {
            PrivateKey emisorClavePrivada = Emisor.obtenerClavePrivada();
            PublicKey receptorClavePublica = Receptor.obtenerClavePublica();

            Cipher cipherEmisor = Cipher.getInstance("RSA");
            Cipher cipherReceptor = Cipher.getInstance("RSA");

            cipherEmisor.init(Cipher.ENCRYPT_MODE, emisorClavePrivada);
            cipherReceptor.init(Cipher.ENCRYPT_MODE, receptorClavePublica);

            cifradoPriv = cipherEmisor.doFinal(leerFichero().readAllBytes());
            cifradoPublic = cipherReceptor.doFinal(cifradoPriv);

            escribirEnFichero(cifradoPublic);

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }  catch (InvalidKeyException e) {
            e.printStackTrace();
        }catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void escribirEnFichero(byte [] msjCifrado){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("mensajeCifrado.txt");
            fileOutputStream.write(msjCifrado);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static FileInputStream leerFichero (){


        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream("mensaje.txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileInputStream;
    }


}