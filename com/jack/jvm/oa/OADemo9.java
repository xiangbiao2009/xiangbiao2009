package com.jack.jvm.oa;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.ServiceLoader;

public class OADemo9 {
    public static void main(String[] args) throws Exception {
        Double salary = 15000.00;

        //使用 URLClassLoader，就不需要在 OADemo 中添加 SPI 的配置文件，直接在 SalaryCaler.jar中添加 SPI 配置文件即可
        //将实现类和SPI 配置文件放在一起，更符合工程化的思想
        while (true) {
            String jarPath1 = "file:/Users/roykingw/lib/SalaryCaler.jar";
            URLClassLoader urlClassLoader1 = new URLClassLoader(new URL[] {new URL(jarPath1)});
            SalaryCalService salaryService1 = getSalaryService(urlClassLoader1);
            System.out.println("应该到手Money:" + salaryService1.cal(salary));

            String jarPath2 = "file:/Users/roykingw/lib2/SalaryCaler.jar";
            URLClassLoader urlClassLoader2 = new URLClassLoader(new URL[] {new URL(jarPath2)});
            SalaryCalService salaryService2 = getSalaryService(urlClassLoader2);
            System.out.println("实际到手Money:" + salaryService2.cal(salary));

            SalaryCalService salaryCalService3 = getSalaryService(null);
            System.out.println("OA系统中计算的Money:"+salaryCalService3.cal(salary));
            Thread.sleep(5000);
        }
    }
    private static SalaryCalService getSalaryService(ClassLoader classloader){
        ServiceLoader<SalaryCalService> services;
        if(null == classloader){
            services = ServiceLoader.load(SalaryCalService.class);
        }else{
            ClassLoader c1 = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(classloader);
            services = ServiceLoader.load(SalaryCalService.class);
            Thread.currentThread().setContextClassLoader(c1);

        }
        SalaryCalService service = null;
        if(null != services){
            //这里只需要拿SPI加载到的第一个实现类
            Iterator<SalaryCalService> iterator = services.iterator();
            if(iterator.hasNext()){
                service = iterator.next();
            }
        }
        return service;
    }
}
