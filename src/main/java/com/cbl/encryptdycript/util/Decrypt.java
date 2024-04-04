package com.cbl.encryptdycript.util;

import com.nimbusds.jose.jwk.RSAKey;

public class Decrypt {
	private String decryptPublicKeyPath;
	private String decryptPrivateKeyPath;
	private String verifyPublicKeyPath;
	private RSAKey decryptJWK;
	private RSAKey verifyJWK;

	public String getDecryptPublicKeyPath() {
		return decryptPublicKeyPath;
	}

	public void setDecryptPublicKeyPath(String decryptPublicKeyPath) {
		this.decryptPublicKeyPath = decryptPublicKeyPath;
	}

	public String getDecryptPrivateKeyPath() {
		return decryptPrivateKeyPath;
	}

	public void setDecryptPrivateKeyPath(String decryptPrivateKeyPath) {
		this.decryptPrivateKeyPath = decryptPrivateKeyPath;
	}

	public String getVerifyPublicKeyPath() {
		return verifyPublicKeyPath;
	}

	public void setVerifyPublicKeyPath(String verifyPublicKeyPath) {
		this.verifyPublicKeyPath = verifyPublicKeyPath;
	}

	public RSAKey getDecryptJWK() {
		return decryptJWK;
	}

	public void setDecryptJWK(RSAKey decryptJWK) {
		this.decryptJWK = decryptJWK;
	}

	public RSAKey getVerifyJWK() {
		return verifyJWK;
	}

	public void setVerifyJWK(RSAKey verifyJWK) {
		this.verifyJWK = verifyJWK;
	}

}
