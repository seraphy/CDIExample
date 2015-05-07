package jp.seraphyware.cdiexample;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author seraphy
 */
@Dependent
public class SimpleBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @PostConstruct
    protected void initialize() {
        logger.info(getClass().getSimpleName() +
                ">initialize: " + Integer.toHexString(hashCode()));
    }
    
    @PreDestroy
    protected void destroy() {
        logger.info(getClass().getSimpleName() +
                ">destroy: " + Integer.toHexString(hashCode()));
    }
}
