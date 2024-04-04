package com.cbl.encryptdycript.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;

public class JWTDecryptor {
    Decrypt decrypt = new Decrypt();

    public static Boolean checkJWSSignatureAlgorithm(JWSAlgorithm jwsAlgorithm) {
        switch (jwsAlgorithm.getName()) {
            case "RS256":
                return true;
            default:
                return false;
        }
    }

    public static Boolean checkEncryptionMethodAlgorithm(EncryptionMethod encryptionMethod) {
        switch (encryptionMethod.getName()) {
            case "A128CBC-HS256":
                return true;
            case "A256CBC-HS512":
                return true;
            default:
                return false;
        }
    }

    public static Boolean checkJWEAlgorithm(JWEAlgorithm jweAlgorithm) {
        switch (jweAlgorithm.getName()) {
            case "RSA-OAEP-256":
                return true;
            //newly added
            case "RSA-OAEP":
                return true;
            //
            default:
                return false;
        }
    }

    public JWTDecryptor(Decrypt decrypt) throws FileNotFoundException, Exception {
        RSAKey decryptJWK = new RSAKey.Builder(
                JWTUtils.getPublicKeyFromPEM(new FileReader(decrypt.getDecryptPublicKeyPath())))
                .privateKey(JWTUtils.getPrivateKeyFromPEM(new FileReader(decrypt.getDecryptPrivateKeyPath())))
                .build();
        decrypt.setDecryptJWK(decryptJWK);

        RSAKey verifyJWK = new RSAKey.Builder(
                JWTUtils.getPublicKeyFromPEM(new FileReader(decrypt.getVerifyPublicKeyPath()))).build();
        decrypt.setVerifyJWK(verifyJWK);
        this.decrypt = decrypt;
    }

    public String decryptandVerifyMessage(String encryptedMessage) throws JOSEException, ParseException {

        System.out.println("<-------Decrypting JWT encrypted message------>");

        JWEObject jweObjectdecrypt = JWEObject.parse(encryptedMessage);

        System.out.println(
                "<-------Decrypting JWT message using private key------>" + decrypt.getDecryptPrivateKeyPath());
        jweObjectdecrypt.decrypt(new RSADecrypter(decrypt.getDecryptJWK()));

        SignedJWT signedJWT = jweObjectdecrypt.getPayload().toSignedJWT();

        System.out.println("<-------Verifying the signature using public key------>");

        if (signedJWT.verify(new RSASSAVerifier(decrypt.getVerifyJWK()))) {
            System.out.println("<------Signature verified------>");

            if (checkJWSSignatureAlgorithm(signedJWT.getHeader().getAlgorithm())
                    && checkEncryptionMethodAlgorithm(jweObjectdecrypt.getHeader().getEncryptionMethod())
                    && checkJWEAlgorithm(jweObjectdecrypt.getHeader().getAlgorithm())) {
                System.out.println("<-------Algorithm Check successfull------>");
            } else {
                System.err.println("<-------Algorithm Check failed------>");
                System.err.println("<-------JWS Signature Algorithm------>" + signedJWT.getHeader().getAlgorithm());
                System.err.println("<-------Encryption Method Algorithm------>"
                        + jweObjectdecrypt.getHeader().getEncryptionMethod().getName());
                System.err.println(
                        "<-------JWE Algorithm------>" + jweObjectdecrypt.getHeader().getAlgorithm().getName());
            }

            String decryptMessage = signedJWT.getJWTClaimsSet().getClaim("Payload").toString();
            if (decryptMessage != null && !decryptMessage.equals("") && !decryptMessage.equals("null")) {
                System.out.println("<-------decryptedMessage------>\n" + decryptMessage);
            } else {
                System.err.println("<-------Decryption Error. Decrypted Paylod Claim is null------>");
            }

            return decryptMessage;

        } else {
            System.err.println("<-------Signature verification failed------>");
        }

        return "";
    }
}
