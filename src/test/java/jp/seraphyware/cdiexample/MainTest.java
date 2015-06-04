package jp.seraphyware.cdiexample;

import javax.inject.Inject;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalPackages;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.deltaspike.SupportDeltaspikeCore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * テスト
 */
@RunWith(CdiRunner.class) // CDIテストランナー
@SupportDeltaspikeCore // Apache-DeltaSpikeを有効化
@AdditionalPackages(MainApp.class) // MainAppクラスのパッケージ配下を読み込ませる
@ActivatedAlternatives(LoggerProducerForTest.class) // Loggerの@Producersをテスト用に切り替える
public class MainTest {
    
    @Inject
    private EchoServer echoServer;
    
    @Test
    public void testEcho() {
        echoServer.say("hello, world!!");
    }
}
