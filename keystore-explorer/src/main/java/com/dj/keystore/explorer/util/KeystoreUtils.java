package com.dj.keystore.explorer.util;

import com.dj.keystore.explorer.model.KeyModel;
import com.dj.keystore.explorer.model.KeyUsage;
import com.dj.keystore.explorer.model.KeyValueModel;
import sun.security.x509.X509Key;

import javax.net.ssl.X509KeyManager;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PublicKey;
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

	public static List<KeyValueModel> readSingleKey(String keystoreFile, String keystoreP, String alias) {
		try (FileInputStream is = new FileInputStream(keystoreFile)) {
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			char[] pArray = keystoreP.toCharArray();
			keystore.load(is, pArray);
			List<KeyValueModel> keyModelList = new ArrayList<>();
			Certificate certificate = keystore.getCertificate(alias);
			if (certificate instanceof X509Certificate) {
				X509Certificate x509 = (X509Certificate) certificate;
				keyModelList.add(new KeyValueModel("Version", "V" + x509.getVersion()));
				keyModelList.add(new KeyValueModel("Serial Number", x509.getSerialNumber().toString(16)));
				keyModelList.add(new KeyValueModel("Signature algorithm", x509.getSigAlgName()));
				keyModelList.add(new KeyValueModel("Issuer", getFieldFromPrincipal(x509.getIssuerDN().toString(), "CN")));
				keyModelList.add(new KeyValueModel("Valid From", x509.getNotBefore().toString()));
				keyModelList.add(new KeyValueModel("Valid To", x509.getNotAfter().toString()));
				keyModelList.add(new KeyValueModel("Subject", getFieldFromPrincipal(x509.getSubjectDN().toString(), "CN")));
				keyModelList.add(new KeyValueModel("Subject Alternative Names", x509.getSubjectAlternativeNames().toString()));
				PublicKey publicKey = x509.getPublicKey();
				keyModelList.add(new KeyValueModel("Public key", publicKey.getAlgorithm() + " (" + publicKey.getEncoded().length + " Bits)"));
				keyModelList.add(new KeyValueModel("Key Usages", getKeyUsageString(x509.getKeyUsage())));
				keyModelList.add(new KeyValueModel("Basic Constraints", getBasicConstraint(x509.getBasicConstraints())));
			}
			return keyModelList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String getFieldFromPrincipal(String principal, String field) {
		String value = "";
		String[] issuerDNValues = principal.split(",");
		for (String eachIssuerDNValue : issuerDNValues) {
			String[] keyVal = eachIssuerDNValue.split("=");
			if (field.equals(keyVal[0])) {
				value = keyVal[1];
			}
		}
		return value;
	}

	private static String getKeyUsageString(boolean[] keyUsages) {
		StringBuilder builder = new StringBuilder();
		for (KeyUsage keyUsage : KeyUsage.values()) {
			if (keyUsages[keyUsage.ordinal()]) {
				builder.append(keyUsage.getDescription()).append(", ");
			}
		}
		String retVal = builder.toString();
		if (retVal.length() > 2) {
			retVal = retVal.substring(0, retVal.length() - 2);
		}
		return retVal;
	}

	private static String getBasicConstraint(int basicConstraint) {
		StringBuilder builder = new StringBuilder();
		if (basicConstraint != -1) {
			builder.append("Subject Type = CA");
		} else {
			builder.append("Subject Type = Non CA");
		}
		if (basicConstraint == Integer.MAX_VALUE) {
			builder.append(", Path Length Constraint = None");
		} else {
			builder.append(", Path Length Constraint = " + basicConstraint);
		}
		return builder.toString();
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
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
