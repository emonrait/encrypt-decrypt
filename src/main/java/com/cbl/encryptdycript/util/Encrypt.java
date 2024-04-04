package com.cbl.encryptdycript.util;

import com.nimbusds.jose.jwk.RSAKey;

public class Encrypt {

	private String signPublicKeyPath;
	private String signPrivateKeyPath;
	private String encryptPublicKeyPath;
	private RSAKey signJWK;
	private RSAKey encryptJWK;

	public String getSignPublicKeyPath() {
		return signPublicKeyPath;
	}

	public void setSignPublicKeyPath(String signPublicKeyPath) {
		this.signPublicKeyPath = signPublicKeyPath;
	}

	public String getSignPrivateKeyPath() {
		return signPrivateKeyPath;
	}

	public void setSignPrivateKeyPath(String signPrivateKeyPath) {
		this.signPrivateKeyPath = signPrivateKeyPath;
	}

	public String getEncryptPublicKeyPath() {
		return encryptPublicKeyPath;
	}

	public void setEncryptPublicKeyPath(String encryptPublicKeyPath) {
		this.encryptPublicKeyPath = encryptPublicKeyPath;
	}

	public RSAKey getSignJWK() {
		return signJWK;
	}

	public void setSignJWK(RSAKey signJWK) {
		this.signJWK = signJWK;
	}

	public RSAKey getEncryptJWK() {
		return encryptJWK;
	}

	public void setEncryptJWK(RSAKey encryptJWK) {
		this.encryptJWK = encryptJWK;
	}

}
