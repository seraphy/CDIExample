package jp.seraphyware.cdiexample;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import org.slf4j.Logger;

/**
 * EchoServerに対するデコレータ.<br>
 * META-INF/beans.xmlで、デコレータを指定することで機能する.
 * @author seraphy
 */
@Decorator
public class EchoServerDecorator implements EchoServer {

    @Inject
    private Logger logger;
    
    @Inject
    @Delegate
    private EchoServer delegate;

    @Override
    public void say(String message) {
        logger.info("♪BEFORE♪:" + this);
        delegate.say(message);
        logger.info("♪AFTER♪:" + this);
    }
}
