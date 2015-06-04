package jp.seraphyware.cdiexample;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * テスト用ロガーファクトリ
 */
@Dependent
public class LoggerProducerForTest {
    
    @Produces
    @Alternative // テスト用にLoggerのインジェクションを切り替え可能にする.
    public Logger createLogger() {
        System.out.println("◇◇◇ createLogger ◇◇◇");
        return LoggerFactory.getLogger("_TEST_LOGGER_");
    }
}
