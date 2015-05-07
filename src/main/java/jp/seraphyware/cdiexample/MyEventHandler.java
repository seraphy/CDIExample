package jp.seraphyware.cdiexample;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MyEventイベントオブジェクトをハンドリングするハンドラ.
 * 
 * @author seraphy
 */
@ApplicationScoped
public class MyEventHandler {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    void handleEvent(@Observes MyEvent event) {
        logger.info("▼▲▼▲ " + event);
    }
}
