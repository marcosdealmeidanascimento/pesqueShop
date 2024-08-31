package com.les.pesqueshop.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.les.pesqueshop.dominio.Cliente;
import com.les.pesqueshop.dominio.EntidadeDominio;

import java.sql.SQLException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CriptografiaSenha implements IStrategy {
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;
    private final SecretKey secretKey;

    public CriptografiaSenha() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(KEY_SIZE);
        this.secretKey = keyGen.generateKey();
    }

    public CriptografiaSenha(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }

    public String encrypt(String password) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String getKey() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    @Override
    public String processar(EntidadeDominio entidade) throws SQLException, JsonProcessingException {
        Cliente cliente = (Cliente) entidade;
        try {
            String senhaCriptografada = encrypt(cliente.getSenha());
            cliente.setSenha(senhaCriptografada);
            return "";
        } catch (Exception e) {
            throw new SQLException("Erro ao criptografar senha", e);
        }
    }
}