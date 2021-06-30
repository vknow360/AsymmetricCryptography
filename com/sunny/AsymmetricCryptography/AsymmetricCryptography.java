package com.sunny.AsymmetricCryptography;
import android.app.Activity;
import android.util.Base64;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
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
import com.google.appinventor.components.runtime.util.AsynchUtil;
@DesignerComponent(version = 3, versionName = "3.1",description = "An extension for asymmetric cryptography <br> Developed by Sunny Gupta", category = ComponentCategory.EXTENSION, nonVisible = true, iconName = "https://res.cloudinary.com/andromedaviewflyvipul/image/upload/c_scale,h_20,w_20/v1571472765/ktvu4bapylsvnykoyhdm.png", helpUrl = "https://community.kodular.io/t/asymmetriccryptography-an-extension-for-asymmetric-cryptography/60061/13")
@SimpleObject(external = true)
public class AsymmetricCryptography extends AndroidNonvisibleComponent {
    public Activity activity;
    public KeyPairGenerator keyGen;
    public KeyPair pair;
    public PrivateKey privateKey;
    public PublicKey publicKey;
    public Cipher cipher;
    public KeyFactory kf;

    public AsymmetricCryptography(ComponentContainer container) {
        super(container.$form());
        activity = container.$context();
        try {
            cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            keyGen = KeyPairGenerator.getInstance("RSA");
            kf = KeyFactory.getInstance("RSA");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @SimpleFunction(description = "Tries to generate keys of given key length and raises 'KeysGenerated' event.Key length should always be 8 times larger than string length.")
    public void GenerateKeys(final int keyLength) {
        AsynchUtil.runAsynchronously(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                String message;
                try {
                    keyGen.initialize(keyLength);
                    pair = keyGen.generateKeyPair();
                    privateKey = pair.getPrivate();
                    publicKey = pair.getPublic();
                    result = true;
                    message = "Successful";
                } catch (Exception e) {
                    e.printStackTrace();
                    message = e.toString();
                }
                postResult(result,message);
            }
        });
    }
    public void postResult(final boolean success,final String response){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                KeysGenerated(success,response);
            }
        });
    }

    @SimpleEvent(description = "Event raised after 'GenerateKeys' method with success and response")
    public void KeysGenerated(boolean successful, String response) {
        EventDispatcher.dispatchEvent(this, "KeysGenerated",successful,response);
    }

    @SimpleFunction(description = "Returns public key in string format")
    public String PublicKey() {
        return new String(Base64.encode(publicKey.getEncoded(),Base64.NO_WRAP));
    }

    @SimpleFunction(description = "Returns private key in string format")
    public String PrivateKey() {
        return new String(Base64.encodeToString(privateKey.getEncoded(),Base64.NO_WRAP));
    }

    @SimpleFunction(description = "Tries to encrypt string with provided public key")
    public String Encrypt(String string, String publicKey) {
        if (!string.isEmpty())
            try {
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(publicKey.getBytes(),Base64.NO_WRAP));
                PublicKey pKey = kf.generatePublic(x509EncodedKeySpec);
                cipher.init(1, pKey);
                return Base64.encodeToString(cipher.doFinal(string.getBytes("UTF-8")), Base64.NO_WRAP);
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
                PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey.getBytes(),Base64.NO_WRAP));
                PrivateKey prKey = kf.generatePrivate(pKCS8EncodedKeySpec);
                cipher.init(2, prKey);
                return new String(cipher.doFinal(Base64.decode(string.getBytes("UTF-8"),Base64.NO_WRAP)));
            } catch (Exception exception) {
                exception.printStackTrace();
                return exception.toString();
            }
        return "";
    }
    @SimpleFunction(description = "Tries to encrypt string with provided public key asynchronously")
    public void AsyncEncrypt(final String string,final String publicKey ){
        AsynchUtil.runAsynchronously(new Runnable() {
            @Override
            public void run() {
                final String str = Encrypt(string,publicKey);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringEncrypted(str);
                    }
                });
            }
        });
    }
    @SimpleEvent(description = "Returns encrypted text")
    public void StringEncrypted(String data){
        EventDispatcher.dispatchEvent(this,"StringEncrypted",data);
    }
    @SimpleFunction(description = "Tries to decrypt string with provided private key asynchronously")
    public void AsyncDecrypt(final String string,final String privateKey ){
        AsynchUtil.runAsynchronously(new Runnable() {
            @Override
            public void run() {
                final String str = Decrypt(string,privateKey);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringDecrypted(str);
                    }
                });
            }
        });
    }
    @SimpleEvent(description = "Returns decrypted text")
    public void StringDecrypted(String data){
        EventDispatcher.dispatchEvent(this,"StringDecrypted",data);
    }
}