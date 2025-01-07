package com.jack.jvm;

import sun.misc.Launcher;

import java.net.URL;


/**
 * <h1>3句总结JDK8的类加载机制</h1>
 * <ul>
 *     <li>1.类缓存：加载过的类都有缓存，再次加载时都读取缓存中的内容</li>
 *     <li>2.双亲委派：向上委托查找，向下委托加载</li>
 *     <li>3.沙箱保护机制：不允许应用程序加载JDK内部的系统内</li>
 * </ul>
 *
 */
public class ClassLoadDemo {
    public static void main(String[] args) {
        classLoadPrint();

        // 类加载器的文件路径
        classLoadFileURL();
    }

    /**
     * 输出类加载器 <br/>
     * <li>2.双亲委派：向上委托查找，向下委托加载</li>
     * 怎么理解呢？<br/>
     * 1.父子关系  bootStrap -> ext -> app <br/>
     *
     */
    private static void classLoadPrint() {
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader extClassLoader = appClassLoader.getParent();
        ClassLoader bootClassLoader = extClassLoader.getParent();

        System.out.println("BootStrap ClassLoader:" + bootClassLoader);
        System.out.println("ext ClassLoader:" + extClassLoader);
        System.out.println("app ClassLoader：" + appClassLoader);

        System.out.println("---------------------------------");
    }

    private static void classLoadFileURL() {
        // 类加载器下的文件
        System.out.println("bootClassLoader file url：");

        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urLs.length; i++) {
            System.out.println(urLs[i]);
        }
        System.out.println("---------------------------------");
        System.out.println("extClassLoader file url：");
        String extDirs = System.getProperty("java.ext.dirs");
        System.out.println(extDirs);
        System.out.println("---------------------------------");
        System.out.println("appClassLoader file url：");
        String classPath = System.getProperty("java.class.path");
        System.out.println(classPath);
    }
}