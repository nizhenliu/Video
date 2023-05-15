package com.example.tool.simplemsg;

import java.util.Observer;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/4/26 14:20
 * @description
 * @updateUser hahajing
 * @updateDate 2022/4/26 14:20
 * @updateRemark
 */
public class NormalEventUtils {
    private NormalEventUtils(){
        observable=new NormalObservable();
    }
    static class HOLDER{
        static NormalEventUtils INSTANCE=new NormalEventUtils();
    }
    public static NormalEventUtils getInstance(){
        return HOLDER.INSTANCE;
    }

    private NormalObservable observable;

    public void addObserver(Observer observer){

        observable.addObserver(observer);
    }

    public void deleteObserver(Observer observer){
        observable.deleteObserver(observer);
    }

    public void nofityAll(Object arg){
        observable.notifyObservers(arg);
    }
}
