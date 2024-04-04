package com.cbl.encryptdycript.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class JWTEncryptor {
    Encrypt encrypt = new Encrypt();

    public JWTEncryptor(Encrypt encrypt) throws FileNotFoundException, Exception {
        RSAKey signJWK = new RSAKey.Builder(
                JWTUtils.getPublicKeyFromPEM(new FileReader(encrypt.getSignPublicKeyPath())))
                .privateKey(JWTUtils.getPrivateKeyFromPEM(new FileReader(encrypt.getSignPrivateKeyPath())))
                .build();
        encrypt.setSignJWK(signJWK);

        RSAKey encryptJWK = new RSAKey.Builder(
                JWTUtils.getPublicKeyFromPEM(new FileReader(encrypt.getEncryptPublicKeyPath()))).build();
        encrypt.setEncryptJWK(encryptJWK);
        this.encrypt = encrypt;
    }

    public String signandEncryptMessage(String message) throws JOSEException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        System.out.println("<-------Creating JWT encrypted message------>");

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(encrypt.getSignJWK().getKeyID()).build(),
                new JWTClaimsSet.Builder().claim("Payload", message).subject("testorgid")
                        .issueTime(simpleDateFormat.parse(simpleDateFormat.format(new Date()))).issuer("testissuer")
                        .build());

        System.out.println("<-------Sign the JWT message using private key------>" + encrypt.getSignPrivateKeyPath());
        signedJWT.sign(new RSASSASigner(encrypt.getSignJWK()));

        System.out.println("<-------Create JWE Object with signed JWT as payload------>");

        JWEObject jweObject = new JWEObject(
                new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256CBC_HS512).contentType("JWT")
                        .build(),
                new Payload(signedJWT));

        System.out.println("<-------Encrypt the JWE object with public key------>" + encrypt.getEncryptPublicKeyPath());

        jweObject.encrypt(new RSAEncrypter(encrypt.getEncryptJWK()));

        String jweString = jweObject.serialize();

        System.out.println("<---Encrypted JWT Message---->\n" + jweString);
        return jweString;
    }
}
