package com.les.pesqueshop.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.les.pesqueshop.dominio.Cliente;
import com.les.pesqueshop.dominio.EntidadeDominio;

import java.sql.SQLException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


public class DescriptografarSenha implements IStrategy {
    private static final String ALGORITHM = "AES";
    private final SecretKeySpec secretKey;

    public DescriptografarSenha(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }

    public String decrypt(String encryptedPassword) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }

    @Override
    public String processar(EntidadeDominio entidade) throws SQLException, JsonProcessingException {
        Cliente cliente = (Cliente) entidade;
        try {
            return decrypt(cliente.getSenha());
        } catch (Exception e) {
            throw new SQLException("Erro ao descriptografar senha", e);
        }
    }
}
