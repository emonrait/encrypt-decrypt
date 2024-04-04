package com.cbl.encryptdycript.util;

import java.io.Reader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

public class JWTUtils {
	static PrivateKey getPrivateKeyFromPEM(Reader reader) throws Exception {
		PrivateKey key;
		PEMParser pem = null;
		try {
			pem = new PEMParser(reader);
			JcaPEMKeyConverter jcaPEMKeyConverter = new JcaPEMKeyConverter();
			Object pemContent = pem.readObject();
			if (pemContent instanceof PEMKeyPair) {
				PEMKeyPair pemKeyPair = (PEMKeyPair) pemContent;
				KeyPair keyPair = jcaPEMKeyConverter.getKeyPair(pemKeyPair);
				key = keyPair.getPrivate();
			} else if (pemContent instanceof PrivateKeyInfo) {
				PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) pemContent;
				key = jcaPEMKeyConverter.getPrivateKey(privateKeyInfo);
			} else {
				throw new IllegalArgumentException("Unsupported private key format");
			}
		} catch (Exception e) {
			throw new Exception("Exception");
		} finally {
			pem.close();
		}

		return key;
	}

	static RSAPublicKey getPublicKeyFromPEM(Reader reader) throws Exception {
		PublicKey key;
		PEMParser pem = null;
		try {
			pem = new PEMParser(reader);
			JcaPEMKeyConverter jcaPEMKeyConverter = new JcaPEMKeyConverter();
			Object pemContent = pem.readObject();
			if (pemContent instanceof PEMKeyPair) {
				PEMKeyPair pemKeyPair = (PEMKeyPair) pemContent;
				KeyPair keyPair = jcaPEMKeyConverter.getKeyPair(pemKeyPair);
				key = keyPair.getPublic();
			} else if (pemContent instanceof SubjectPublicKeyInfo) {
				SubjectPublicKeyInfo keyInfo = (SubjectPublicKeyInfo) pemContent;
				key = jcaPEMKeyConverter.getPublicKey(keyInfo);
			} else if (pemContent instanceof X509CertificateHolder) {
				X509CertificateHolder cert = (X509CertificateHolder) pemContent;
				key = jcaPEMKeyConverter.getPublicKey(cert.getSubjectPublicKeyInfo());
			} else {
				throw new IllegalArgumentException(
						"Unsupported public key format '" + pemContent.getClass().getSimpleName() + '"');
			}
		} catch (Exception e) {
			throw new Exception("Exception");
		} finally {
			pem.close();
		}

		return (RSAPublicKey) key;
	}
}
