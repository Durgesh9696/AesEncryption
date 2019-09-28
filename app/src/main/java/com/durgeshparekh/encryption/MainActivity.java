package com.durgeshparekh.encryption;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.security.KeyPair;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class MainActivity extends AppCompatActivity {
    EditText inputStr;
    TextView encryptedStr;
    private static final String encryptionKey = "fe434d98558ce2b3";
    private static final String characterEncoding = "UTF-8";
    private static final String cipherTransformation = "AES/CBC/PKCS5PADDING";
    private static final String aesEncryptionAlgorithem = "AES";
    String textToDecrypt = "gvDePTCgfLXtoWIiRrkC7X3WKfj0/LUd9QvEJWGHR2J3HyCunI1lYR9xwqOtW2/zl3aMDy9Odox6y1cY4RXzVA==";
    String encryptedText;
    String decryptedText;
    private static final String TAG = "MainActivity";

    //RSA key pair (public and private)
    private KeyPair rsaKey = null;
    String tempKey = "1234567890123456";
    EditText etDecrypt;
    TextView tvPlainText;
    CryptLib cryptLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputStr = findViewById(R.id.input_text);
        encryptedStr = findViewById(R.id.encrypted_string);
        etDecrypt = findViewById(R.id.decrypt_text);
        tvPlainText = findViewById(R.id.plain_text);
        try {
            cryptLib = new CryptLib();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void onEncryptClick(View view) {

        try {
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            byte[] key = tempKey.getBytes(characterEncoding);

            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
            byte[] cipherText = cipher.doFinal(inputStr.getText().toString().getBytes(characterEncoding));

            encryptedText = android.util.Base64.encodeToString(cipherText, android.util.Base64.DEFAULT);
            Log.e("onEncryptClick: ", encryptedText );

            encryptedStr.setText(encryptedText);

        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    public void onDecryptClick(View view) {

        try {
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            byte[] key = tempKey.getBytes(characterEncoding);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);

            byte[] cipherText = android.util.Base64.decode(encryptedStr.getText().toString().getBytes(characterEncoding),
                    android.util.Base64.DEFAULT);
            decryptedText = new String(cipher.doFinal(cipherText), characterEncoding);

            Log.e("onDecryptClick: ", decryptedText );

            encryptedStr.setText(decryptedText);

        } catch (Exception E) {
            E.printStackTrace();
        }
    }


    @TargetApi(Build.VERSION_CODES.O)
    public void onDecryptToPlainClick(View view) {

        try {
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            byte[] key = tempKey.getBytes(characterEncoding);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);

            byte[] cipherText = android.util.Base64.decode(encryptedStr.getText().toString().getBytes(characterEncoding),
                    android.util.Base64.DEFAULT);
            decryptedText = new String(cipher.doFinal(cipherText), characterEncoding);

            Log.e("onDecryptClick: ", decryptedText );

            tvPlainText.setText(decryptedText);

        } catch (Exception E) {
            E.printStackTrace();
        }
    }
}
