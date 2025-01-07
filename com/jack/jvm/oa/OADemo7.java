package com.jack.jvm.oa;

public class OADemo7 {
    public static void main(String[] args) throws Exception {
        Double salary = 15000.00;
        Double money = 0.00;

        //模拟不停机状态
        while (true) {
            SalaryCaler caler = new SalaryCaler();
            System.out.println("应该到手Money:" + caler.cal(salary));

            SalaryJARLoader6 salaryJARLoader = new SalaryJARLoader6(Content.jarURL);
            Class<?> clazz = salaryJARLoader.loadClass(Content.pkgName);
            Object obj = clazz.newInstance();

//            反射太麻烦，能不能进行类型强转？
            SalaryCaler caler2 = (SalaryCaler)obj;
            money = caler2.cal(salary);

            money=(Double)clazz.getMethod("cal", Double.class).invoke(obj, salary);
            System.out.println("实际到手Money:" + money);

            System.out.println("============");
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
