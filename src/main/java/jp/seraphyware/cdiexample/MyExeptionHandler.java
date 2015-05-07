package jp.seraphyware.cdiexample;

import java.io.IOException;
import javax.enterprise.event.Observes;
import org.apache.deltaspike.core.api.exception.control.ExceptionHandler;
import org.apache.deltaspike.core.api.exception.control.Handles;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionEvent;
import org.apache.deltaspike.core.api.exception.control.event.ExceptionToCatchEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 例外ハンドラの実験コード.
 * 
 * @author seraphy
 */
@ExceptionHandler
public class MyExeptionHandler {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * ExceptionToCatchイベントを直接ハンドリングする.
     * @param evt 
     */
    void printExceptions2(@Observes ExceptionToCatchEvent evt)
    {
        logger.info("■★■★■★■: " + evt.getException());
        //evt.handledAndContinue();
    }

    /**
     * 例外イベントをハンドリングする.
     * (ただしJavaSE環境下では動作しない？)
     * @param evt 
     */
    void printExceptions(@Handles ExceptionEvent<IOException> evt)
    {
        // ※ こちらの正規の方法ではJavaSE環境では、うまく取れないようだ？
        
        logger.info("★■★■★: " + evt.getException());
        evt.handledAndContinue();
    }
}
