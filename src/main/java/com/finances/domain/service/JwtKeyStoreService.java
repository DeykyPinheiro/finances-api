package com.finances.domain.service;

import com.finances.common.properties.JwtKeyStoreProperties;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;

import com.nimbusds.jose.util.Base64;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.*;

import com.nimbusds.jose.jwk.RSAKey;

import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//TODO refatorar, codigo ta todo cagado, mas funciona minimamente bem

@Service
public class JwtKeyStoreService {

    private JwtKeyStoreProperties keyProperties;

    @Autowired
    public JwtKeyStoreService(JwtKeyStoreProperties keyProperties) {
        this.keyProperties = keyProperties;
    }

    public Map<String, Object> publicKey() {
        try {

            Map<String, Object> jwkMap = new HashMap<>();

            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(keyProperties.getJksLocation().getInputStream(), keyProperties.getPassword().toCharArray());

            Certificate certificate = keyStore.getCertificate(keyProperties.getKeypairAlias());
//            List<Base64> x5c = createX5c(certificate);


            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(certificate.getEncoded());
            Base64 x5c = Base64.encode(x509KeySpec.getEncoded());

            List<Base64> listX5c = new ArrayList<>();
            List<String> conteudo = new ArrayList<>();
            listX5c.add(x5c);

            conteudo = listX5c.stream().map((base) -> base.toString()).toList();


            JWK jwk = new RSAKey.Builder((RSAPublicKey) certificate.getPublicKey())
                    .keyID("finances_api")
                    .keyUse(KeyUse.SIGNATURE)
                    .algorithm(JWSAlgorithm.RS256)
                    .x509CertChain(listX5c)
                    .build();


            jwkMap = jwk.toJSONObject();


            var keys = jwk.getX509CertChain();

            jwkMap.put("x5c", conteudo);
            jwkMap.put("x5t#S512", calculateThumbprint((RSAPublicKey) certificate.getPublicKey()));


//            System.out.println("jwkMap: " + keys.toJSONString());
            return jwkMap;

        } catch (RuntimeException | KeyStoreException | IOException | NoSuchAlgorithmException |
                 CertificateException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private List<Base64> createX5c(Certificate certificate) {
        try {
            List<Base64> x5c = new ArrayList<>();

            byte[] certificateBytes = certificate.getEncoded();
            Base64 certificateBase64 = Base64.encode(certificateBytes);
            x5c.add(certificateBase64);

            return x5c;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String calculateThumbprint(PublicKey publicKey) throws Exception {
        byte[] publicKeyBytes = publicKey.getEncoded();

        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-512");
        byte[] thumbprintBytes = md.digest(publicKeyBytes);

        StringBuilder thumbprint = new StringBuilder();
        for (byte b : thumbprintBytes) {
            thumbprint.append(String.format("%02X", b));
        }

        return thumbprint.toString();
    }

//    RSAPublicKey publicKey, RSAPrivateKey privateKey

    @SneakyThrows
    public RSAPublicKey getPublicKey() {
        return (RSAPublicKey) getKeyPair().getPublic();
    }

    public RSAPrivateKey getPrivateKey() {
        try {
            return (RSAPrivateKey) getKeyPair().getPrivate();
        } catch (UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
    }

    public KeyPair getKeyPair() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException {

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(keyProperties.getJksLocation().getInputStream(), keyProperties.getPassword().toCharArray());

        Certificate certificate = keyStore.getCertificate(keyProperties.getKeypairAlias());

        Key key = (PrivateKey) keyStore.getKey(this.keyProperties.getKeypairAlias(), this.keyProperties.getPassword().toCharArray());

        PublicKey publicKey = certificate.getPublicKey();

        return new KeyPair(publicKey, (PrivateKey) key);


    }

}
