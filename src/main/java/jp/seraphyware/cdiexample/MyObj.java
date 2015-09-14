/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.seraphyware.cdiexample;

import java.util.function.Consumer;

/**
 *
 * @author seraphy
 */
public class MyObj implements AutoCloseable {

    private final Consumer<MyObj> callback;
    
    public MyObj(Consumer<MyObj> callback) {
        this.callback = callback;
    }

    @Override
    public void close() {
        if (callback != null) {
            callback.accept(this);
        }
    }
}
