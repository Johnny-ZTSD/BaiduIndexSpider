package cn.johnnyzen.util.print;

import java.util.*;

/**
 * @IDE: Created by IntelliJ IDEA.
 * @Author: 千千寰宇
 * @Date: 2019/4/23  14:39:41
 * @Description: 控制台输出对象信息
 */

public class Print {
    /**
     * @param isOuput 是否立即打印(用于调试时控制)
     * @param object
     * @param <T>
     */
    public static <T> void print(boolean isOuput,T object){
        if(isOuput){
            System.out.println(object.toString());
        }
    }

    /**
     *
     * @param collection
     * @param <T>
     *
     * [demo]
     *  Print.<T>printCollection(collection);
     */
    public static <T> void printCollection(Collection collection){
        if(collection==null || collection.size()<1){
            System.out.print("collection is null or its size < 1!");
            return;
        }
        Iterator<T> iterator = collection.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }
    public static <T> void print(T object){
        System.out.println(object.toString());
    }

    public static <T> void print(List<T> list){
        print(list.toArray());
    }
    public static <T> void print(T [] data){
        for(int i=0,size = data.length;i<size;i++){
            System.out.print(data[i]+" ");
        }
        System.out.println();
    }

    public static <T> void print(T [][] data){
        for(int i=0,rows = data.length;i<rows;i++){
            for(int j=0,cols=data[i].length;j<rows;j++){
                System.out.print(data[i][j] + "\t\t\t\t");
            }
            System.out.println();
        }
    }

    public static void print(Map[] data){
        for(int i=0,rows = data.length;i<rows;i++){
            print(data[i]);
            System.out.println();
        }
    }

    public static void print(Map data){
        Set set = data.keySet();
        Iterator iter = set.iterator();
        while(iter.hasNext()){
            Object key = iter.next();
            System.out.print("<" + key + ":" + data.get(key) + ">" + "\t\t\t\t");
        }
    }
}
