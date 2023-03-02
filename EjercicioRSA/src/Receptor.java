import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;


public class Receptor {

    private static final String CLAVEPUBLICATXT = "clavePublicaReceptor.key";
    private static final String CLAVEPRIVADATXT = "clavePrivadaReceptos.key";


    public static void main(String[] args) {
        KeyPair keypair = generarClaves();
        guardarKeyPair(keypair);
    }

    public static KeyPair generarClaves() {
        KeyPairGenerator generador;
        KeyPair keypair = null;
        try {
            generador = KeyPairGenerator.getInstance("RSA");
            generador.initialize(4096);
            keypair = generador.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keypair;
    }


    public static void guardarKeyPair(KeyPair keypair) {

        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = new FileOutputStream(CLAVEPUBLICATXT);
            fileOutputStream.write(keypair.getPublic().getEncoded());
            fileOutputStream.close();
            fileOutputStream = new FileOutputStream(CLAVEPRIVADATXT);
            fileOutputStream.write(keypair.getPrivate().getEncoded());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PublicKey obtenerClavePublica() {

        File fileClavePublica = new File(CLAVEPUBLICATXT);
        PublicKey publicKey = null;

        try {
            byte[] bytePublicKey = Files.readAllBytes(fileClavePublica.toPath());
            KeyFactory kf = KeyFactory.getInstance("RSA");
            EncodedKeySpec eks = new X509EncodedKeySpec(bytePublicKey);
            publicKey = kf.generatePublic(eks);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return publicKey;
    }

    public static PrivateKey obtenerClavePrivada() {

        File fileClavePrivada = new File(CLAVEPRIVADATXT);
        PrivateKey privateKey = null;

        try {
            byte[] bytePrivateKey = Files.readAllBytes(fileClavePrivada.toPath());
            KeyFactory kf = KeyFactory.getInstance("RSA");
            EncodedKeySpec eks = new PKCS8EncodedKeySpec(bytePrivateKey);
            privateKey = kf.generatePrivate(eks);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return privateKey;
    }
}