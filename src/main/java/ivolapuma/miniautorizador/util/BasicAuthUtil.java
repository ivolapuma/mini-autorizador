package ivolapuma.miniautorizador.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Classe utilitária para fornecer string de autorização para requisições Http com Basic Auth.
 */
public final class BasicAuthUtil {

    /**
     * Retorna string codificada em Base64 para autorização de requisições Http Basic Auth.
     *
     * @param username
     * @param password
     * @return
     */
    public static String getAuthorizarion(String username, String password) {
        String credentials = String.format("%s:%s", username, password);
        String credentialsEncoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        return String.format("Basic %s", credentialsEncoded);
    }

}
