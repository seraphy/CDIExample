package jp.seraphyware.cdiexample;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author seraphy
 */
@ApplicationScoped
public class EchoServerImpl implements EchoServer, Serializable {

    /**
     * ロガー
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    protected void initialize() {
        logger.info(">init @" + Integer.toHexString(hashCode()));
    }
    
    @PreDestroy
    protected void destroy() {
        logger.info(">destroy");
    }
    
    @Override
    public void say(String message) {
        logger.info(">" + message);
    }
}
