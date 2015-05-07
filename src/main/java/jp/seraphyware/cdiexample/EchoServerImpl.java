package jp.seraphyware.cdiexample;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionToCatchEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author seraphy
 */
@ApplicationScoped
@Named("echoServer")
public class EchoServerImpl implements EchoServer, Serializable {

    /**
     * 例外イベント
     */
    @Inject
    private Event<ExceptionToCatchEvent> catchEvent;
    
    /**
     * イベント送信
     */
    @Inject
    private Event<MyEvent> event;

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
    @Logged
    public void say(String message) {
        if (message == null || message.length() == 0) {
            // 例外ハンドラへの通知
            Exception ex = new IOException("too short");
            catchEvent.fire(new ExceptionToCatchEvent(ex));
            return;
        }
        logger.info(">" + message);

        // イベント送信・受信のテスト
        System.out.println("△▽△▽ event=" + event);
        event.fire(new MyEvent("myEvent!:" + message));
    }
}
