import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Emisor {

    private static final String CLAVEPUBLICATXT = "clavePublicaEmisor.key";
    private static final String CLAVEPRIVADATXT = "clavePrivadaEmisor.key";


    public static void main(String[] args) {
        KeyPair keypair = generarClaves();
        guardarClaves(keypair);
    }


    public static KeyPair generarClaves() {
        KeyPairGenerator generador;
        KeyPair claves = null;
        try {
            generador = KeyPairGenerator.getInstance("RSA");
            generador.initialize(2048);
            claves = generador.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No existe el algoritmo especificado");
            e.printStackTrace();
        }
        return claves;
    }


    public static void guardarClaves(KeyPair keypair) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(CLAVEPUBLICATXT);
            fileOutputStream.write(keypair.getPublic().getEncoded());
            fileOutputStream.close();
            fileOutputStream = new FileOutputStream(CLAVEPRIVADATXT);
            fileOutputStream.write(keypair.getPrivate().getEncoded());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            System.err.println("No se encuentra el fichero.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Se ha producido un error durante la escritura en el fichero.");
            e.printStackTrace();
        }
    }


    public static PublicKey getClavePublica() {

        File fileClavePublica = new File(CLAVEPUBLICATXT);
        PublicKey clavePublica = null;

        try {
            byte[] bytePublicKey = Files.readAllBytes(fileClavePublica.toPath());
            KeyFactory kf = KeyFactory.getInstance("RSA");
            EncodedKeySpec eks = new X509EncodedKeySpec(bytePublicKey);
            clavePublica = kf.generatePublic(eks);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clavePublica;
    }


    public static PrivateKey obtenerClavePrivada() {

        File fileClavePrivada = new File(CLAVEPRIVADATXT);
        PrivateKey clavePrivada = null;

        try {
            byte[] bytePrivateKey = Files.readAllBytes(fileClavePrivada.toPath());
            KeyFactory kf = KeyFactory.getInstance("RSA");

            EncodedKeySpec eks = new PKCS8EncodedKeySpec(bytePrivateKey);
            clavePrivada = kf.generatePrivate(eks);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clavePrivada;
    }
}