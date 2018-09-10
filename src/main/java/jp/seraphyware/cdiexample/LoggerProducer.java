package jp.seraphyware.cdiexample;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ロガーを構築するプロデューサ
 */
@Dependent
public class LoggerProducer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    protected void initialize() {
        logger.info("◇◇◇ LoggerProducer::Initialize ◇◇◇");
    }

    @PreDestroy
    protected void destroy() {
        logger.info("◇◇◇ LoggerProducer::destroy ◇◇◇");
    }

    @Produces
    @Dependent
    public Logger createLogger(InjectionPoint ip) {
        logger.info("createLogger: ip=" + ip);
        return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
    }
}
