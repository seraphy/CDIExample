package jp.seraphyware.cdiexample;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.el.ELProcessor;
import javax.el.ELResolver;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanManagerProvider;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.slf4j.Logger;

/**
 * [Apache DeltaSpike + WeldSEの設定]
 * http://deltaspike.apache.org/documentation/cdiimp.html
 * http://deltaspike.apache.org/documentation/container-control.html
 *
 * [Weld-SEの説明]
 * http://docs.jboss.org/weld/reference/latest/en-US/html/environments.html#_java_se
 *
 * @author seraphy
 */
@ApplicationScoped
public class MainApp {

    /**
     * ロガー
     */
    @Inject
    private Logger logger;

    /**
     * エコーサービス
     */
    @Inject
    private EchoServer echoServer;

    /**
     * インジェクションへの間接参照
     */
    @Inject
    Instance<SimpleBean> simpleBeanHolder;

    /**
     *
     */
    @Inject
    Instance<MyObj> myObjHolder;

    /**
     * テスト
     */
    public void run() {
        // シングルトン(ApplicationScoped)のサービス呼び出し
        echoServer.say("hello, " + getClass());

        // 例外イベントのテスト
        echoServer.say(null);

        // Instance<T>によるインジェクションへの間接参照
        SimpleBean bean1 = simpleBeanHolder.get();
        try {
            logger.info("bean1=" + bean1);

            // Instance<T>によるインジェクションへの間接参照 #2
            // @Dependentなので呼び出すたびにインスタンスが新しい.
            SimpleBean bean2 = simpleBeanHolder.get();
            try {
                logger.info("bean2=" + bean2);

            } finally {
                // Instanceで取得した@Dependentのインスタンスは明示的に破棄する必要がある
                logger.info("instance<T>.destroy :" + bean2);
                simpleBeanHolder.destroy(bean2);
            }

        } finally {
            // Instanceで取得した@Dependentのインスタンスは明示的に破棄する必要がある
            logger.info("instance<T>.destroy :" + bean1);
            simpleBeanHolder.destroy(bean1);
        }

        // EL式のテスト
        ELProcessor elProc = new ELProcessor();
        BeanManager bm = BeanManagerProvider.getInstance().getBeanManager();
        ELResolver elResolver = bm.getELResolver();
        elProc.getELManager().addELResolver(elResolver);

        Object ret = elProc.eval("echoServer.say('●◎●◎ EL式からの呼び出し ●◎●◎')");
        logger.info("el ret=" + ret);

        List<MyObj> myObjs = IntStream.range(0, 5)
                .mapToObj(idx -> myObjHolder.get())
                .collect(Collectors.toList());
        myObjs.forEach(myObjHolder::destroy);
    }

    /**
     * 何らかのイベントをキャッチして表示する.
     *
     * @param event
     */
    public void hanleAnyEvent(@Observes MyEvent event) {
        logger.info("● " + event);
    }

    /**
     * エントリポイント
     *
     * @param args
     */
    public static void main(String[] args) {
        // CDIコンテナ作成
        CdiContainer cdiContainer = CdiContainerLoader.getCdiContainer();
        System.out.println("★コンテナを作成しました。");

        // 起動
        cdiContainer.boot();
        System.out.println("★コンテナを起動しました。");

        // コンテキストの有効化
        ContextControl contextControl = cdiContainer.getContextControl();
        System.out.println("☆コンテキストを開始します。");
        contextControl.startContexts();
        System.out.println("★コンテキストは開始しました。");

        // 明示的にDIコンテナからインスタンスを取得する.
        MainApp app = BeanProvider.getContextualReference(MainApp.class, false);
        app.run();

        // しばらく待つ
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
        }

        // コンテキストの無効化
        System.out.println("☆コンテキストを終了します。");
        contextControl.stopContexts();
        System.out.println("★コンテキストは終了しました。");

        // コンテナの終了
        cdiContainer.shutdown();
        System.out.println("★コンテナは終了しました");
    }
}
