package com.jack.jvm;

import java.io.FileInputStream;

public class MyClassLoader extends ClassLoader{

    private String classPath;

    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }

    private byte[] loadByte(String name) throws Exception {
        name = name.replaceAll("\\.", "/");
        FileInputStream fis = new FileInputStream(classPath + "/" + name
                + ".class");
        int len = fis.available();
        byte[] data = new byte[len];
        fis.read(data);
        fis.close();
        return data;
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            boolean b = false;
            byte[] bytes = loadByte(name);
            return defineClass(name,bytes,0,bytes.length);
        }catch (Exception e){
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }
}
