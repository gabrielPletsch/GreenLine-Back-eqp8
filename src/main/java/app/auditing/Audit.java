package app.auditing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Audit {

    private static final Logger logger = LogManager.getLogger(Audit.class);

    public void registrarLogin(String username) {
        logger.info("Usuário {} fez login com sucesso.", username);
    }

    public void registrarLoginFail(String username) {
        logger.warn("Falha ao tentar login com o usuário {}.", username);
    }
}
