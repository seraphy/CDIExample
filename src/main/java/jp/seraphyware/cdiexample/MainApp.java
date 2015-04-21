package jp.seraphyware.cdiexample;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;

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
     * エコーサービス
     */
    @Inject
    private EchoServer echoServer;
    
    /**
     * 都度インスタンス作成する間接参照
     */
    @Inject
    Instance<SimpleBean> simpleBean;
    
    public void run() {
        // シングルトン(ApplicationScoped)のサービス呼び出し
        echoServer.say("hello, " + getClass());

        // Instance<T>によるインジェクションへの間接参照
        SimpleBean bean1 = simpleBean.get();
        System.out.println("bean1=" + bean1);

        // Instance<T>によるインジェクションへの間接参照 #2
        // @Dependentなので呼び出すたびにインスタンスが新しい.
        SimpleBean bean2 = simpleBean.get();
        System.out.println("bean2=" + bean2);
        
        // Instanceで取得した@Dependentのインスタンスは明示的に破棄する必要がある
        System.out.println("instance<T>.destroy");
        simpleBean.destroy(bean1);
        simpleBean.destroy(bean2);
    }

    /**
     * エントリポイント
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
        
        // コンテキストの無効化
        System.out.println("☆コンテキストを終了します。");
        contextControl.stopContexts();
        System.out.println("★コンテキストは終了しました。");
        
        // コンテナの終了
        cdiContainer.shutdown();
        System.out.println("★コンテナは終了しました");
     }
}
