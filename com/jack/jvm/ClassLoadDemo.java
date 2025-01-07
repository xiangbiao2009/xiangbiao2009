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

class jackClassLoad extends ClassLoader{
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        /**
          synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded

            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    if (parent != null) {
                        c = parent.loadClass(name, false);
                    } else {
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }

                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    c = findClass(name);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
         */
        return super.loadClass(name, resolve);
    }
}

class Apple {
    static Apple apple = new Apple(10);

     static double price = 20.00;
    // final static double price = 20.00;
    double totalpay;
    public Apple (double discount) {
        System.out.println("===="+price);
        totalpay = price - discount;
    }
}
class print{
    public static void main(String[] args) {
        // 输出结果  -10 ，
        // 怎么解释呢？ double price = 20.00; 这行代码还没有执行，
        // 只是将price进行初始化了，这时 price=0.0
        System.out.println(Apple.apple.totalpay);

        // 怎么样解决上面的问题呢？
        // 有2种方式
        // 第一种，将如下代码换个位置
        /**
         *   static Apple apple = new Apple(10);
         *   static double price = 20.00;
         */
        // 第二种， 将price声明成常量,如下
        /**
         *  final static double price = 20.00;
         */
    }
}