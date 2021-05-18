package com.sunny.AsymmetricCryptography;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
@DesignerComponent(version = 3, description = "An extension for aymmetric cryptography <br> Developed by Sunny Gupta", 
                   category = ComponentCategory.EXTENSION, 
                   nonVisible = true, 
                   iconName = "https://res.cloudinary.com/andromedaviewflyvipul/image/upload/c_scale,h_20,w_20/v1571472765/ktvu4bapylsvnykoyhdm.png", 
                   helpUrl = "https://community.kodular.io/t/asymmetriccryptography-an-extension-for-asymmetric-cryptography/60061/13")
@SimpleObject(external = true)
public class AsymmetricCryptography extends AndroidNonvisibleComponent {
  public KeyPairGenerator keyGen;
  
  public KeyPair pair;
  
  public PrivateKey keyPrivate;
  
  public PublicKey keyPublic;
  
  public Cipher cipher;
  
  public KeyFactory kf;
  
  public AsymmetricCryptography(ComponentContainer paramComponentContainer) {
    super(paramComponentContainer.$form());
    try {
      cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
      keyGen = KeyPairGenerator.getInstance("RSA");
      kf = KeyFactory.getInstance("RSA");
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  @SimpleFunction(description = "Tries to generate keys of given key length and raises 'KeysGenerated' event.Key length should always be 8 times larger than string length.")
  public void GenerateKeys(int keyLength) {
    try {
      keyGen.initialize(keyLength);
      pair = keyGen.generateKeyPair();
      keyPrivate = pair.getPrivate();
      keyPublic = pair.getPublic();
      KeysGenerated(true, "Successful");
    } catch (Exception exception) {
      exception.printStackTrace();
      KeysGenerated(false, exception.toString());
    } 
  }
  
  @SimpleEvent(description = "Event raised after 'GenerateKeys' method with success and response")
  public void KeysGenerated(boolean successful, String response) {
    EventDispatcher.dispatchEvent(this, "KeysGenerated",successful,response);
  }
  
  @SimpleFunction(description = "Returns public key in string format")
  public String PublicKey() {
    return new String(Base64.encodeBase64(keyPublic.getEncoded()));
  }
  
  @SimpleFunction(description = "Returns private key in string format")
  public String PrivateKey() {
    return new String(Base64.encodeBase64(keyPrivate.getEncoded()));
  }
  
  @SimpleFunction(description = "Tries to encrypt string with provided public key")
  public String Encrypt(String string, String publicKey) {
    if (!string.isEmpty())
      try {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey.getBytes()));
        PublicKey pKey = kf.generatePublic(x509EncodedKeySpec);
        cipher.init(1, pKey);
        return new String(Base64.encodeBase64(cipher.doFinal(string.getBytes())), "UTF-8");
      } catch (Exception exception) {
      	exception.printStackTrace();
        return exception.toString();
      }  
    return "";
  }
  
  @SimpleFunction(description = "Tries to decrypt string with provided private key")
  public String Decrypt(String string, String privateKey) {
    if (!string.isEmpty())
      try {
        PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes()));
        PrivateKey prKey = kf.generatePrivate(pKCS8EncodedKeySpec);
        cipher.init(2, prKey);
        return new String(cipher.doFinal(Base64.decodeBase64(string.getBytes("UTF-8"))));
      } catch (Exception exception) {
      	exception.printStackTrace();
        return exception.toString();
      }  
    return "";
  }
}
