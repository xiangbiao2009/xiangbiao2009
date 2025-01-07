package com.jack.jvm.oa;

public class OADemo6 {
    public static void main(String[] args) throws Exception {
        Double salary = 15000.00;
        Double money = 0.00;

        //模拟不停机状态
        while (true) {
            try {
                money = calSalary(salary);
                System.out.println("实际到手Money:" + money);
            }catch(Exception e) {
                System.out.println("加载出现异常 ："+e.getMessage());
            }
            Thread.sleep(5000);
        }
    }

    private static Double calSalary(Double salary) throws Exception {
        SalaryJARLoader6 salaryClassLoader = new SalaryJARLoader6(Content.jarURL);
        Class<?> clazz = salaryClassLoader.loadClass(Content.pkgName);
//        System.out.println(clazz.getClassLoader());
//        System.out.println(clazz.getClassLoader().getParent());
        if(null != clazz) {
            Object object = clazz.newInstance();
            return (Double)clazz.getMethod("cal", Double.class).invoke(object, salary);
        }
        return -1.00;
    }
}
