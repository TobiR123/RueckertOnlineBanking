package RueckertOnlineBanking.ui.loggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.logging.Logger;

@ApplicationScoped
public class LoggerFactory {
    @Produces
    @ApplicationScoped
    public Logger create() {
        return Logger.getLogger("RueckertOnlineBanking");
    }
}
