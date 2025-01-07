package com.jack.jvm.oa;

import java.util.Iterator;
import java.util.ServiceLoader;

public class OADemo8 {
    public static void main(String[] args) throws Exception {
        Double salary = 15000.00;

        //使用 SalaryJARLoader6，就需要在 OADemo 中添加 SPI 的配置文件
        while (true) {
            SalaryJARLoader6 salaryJARLoader = new SalaryJARLoader6(Content.jarURL);
            SalaryCalService salaryService = getSalaryService(salaryJARLoader);
            System.out.println("应该到手Money:" + salaryService.cal(salary));

            SalaryJARLoader6 salaryJARLoader2 = new SalaryJARLoader6("/Users/roykingw/lib2/SalaryCaler.jar");
            SalaryCalService salaryService2 = getSalaryService(salaryJARLoader2);
            System.out.println("实际到手Money:" + salaryService2.cal(salary));

            Thread.sleep(5000);
        }
    }
    private static SalaryCalService getSalaryService(ClassLoader classloader){
        SalaryCalService service = null;
//        ServiceLoader.load(SalaryCalService.class,classloader);
        ClassLoader c1 = Thread.currentThread().getContextClassLoader();
        try{
            Thread.currentThread().setContextClassLoader(classloader);
            ServiceLoader<SalaryCalService> services = ServiceLoader.load(SalaryCalService.class);
            //这里只需要拿SPI加载到的第一个实现类
            Iterator<SalaryCalService> iterator = services.iterator();
            if(iterator.hasNext()){
                service = iterator.next();
            }
        }finally {
            Thread.currentThread().setContextClassLoader(c1);
        }
        return service;
    }
}
