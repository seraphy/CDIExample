package jp.seraphyware.cdiexample;

/**
 * CDIでハンドル可能なイベントオブジェクト.
 * 
 * @author seraphy
 */
public class MyEvent {
   
    private String message;
    
    public MyEvent() {
        this(null);
    }
    
    public MyEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "message=" + message;
    }
}
