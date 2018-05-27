package com.dj.keystore.explorer.util;

import com.dj.keystore.explorer.model.KeyModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class KeystoreUtils {

	public static List<KeyModel> readKeyStore(String keystoreFile, String keystoreP) {
		try (FileInputStream is = new FileInputStream(keystoreFile)) {
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			char[] pArray = keystoreP.toCharArray();
			keystore.load(is, pArray);
			Enumeration<String> aliases = keystore.aliases();

			List<KeyModel> keyModelList = new ArrayList<>();
			while (aliases.hasMoreElements()) {
				KeyModel keyModel = new KeyModel();

				String eachAlias = aliases.nextElement();
				Certificate certificate = keystore.getCertificate(eachAlias);
				if (certificate instanceof X509Certificate) {
					X509Certificate x509Certificate = (X509Certificate) certificate;
					keyModel.setAlias(eachAlias);
					keyModel.setSubject(x509Certificate.getSubjectDN().toString());
					keyModel.setSerialNumber(x509Certificate.getSerialNumber().toString(16));
					keyModel.setIssuer(x509Certificate.getIssuerDN().toString());
					keyModel.setValidFrom(x509Certificate.getNotBefore().toString());
					keyModel.setValidTo(x509Certificate.getNotAfter().toString());
				}
				keyModelList.add(keyModel);
			}

			return keyModelList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void exportCert(String keystoreFile, String keystoreP, String alias, String certFilePath) {
		try (FileInputStream is = new FileInputStream(keystoreFile)) {
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			char[] pArray = keystoreP.toCharArray();
			keystore.load(is, pArray);
			Certificate certificate = keystore.getCertificate(alias);
			FileOutputStream fos = new FileOutputStream(certFilePath);
			fos.write(certificate.getEncoded());
			fos.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
