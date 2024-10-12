package com.jack.jvm;

import sun.misc.Launcher;
import sun.misc.URLClassPath;

import java.net.URL;

public class ClassLoadDemo {
    public static void main(String[] args) {
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader extClassLoader = appClassLoader.getParent();
        ClassLoader bootClassLoader = extClassLoader.getParent();

        System.out.println("bootClassLoader:" + bootClassLoader);
        System.out.println("extClassLoader:" + extClassLoader);
        System.out.println("appClassLoader：" + appClassLoader);

        System.out.println("---------------------------------");

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