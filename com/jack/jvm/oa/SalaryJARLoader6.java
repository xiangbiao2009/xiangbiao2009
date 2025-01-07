package com.jack.jvm.oa;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureClassLoader;

/**
 * 从jar包中加载薪水计算类。
 * 
 * @author 楼兰
 *
 */
public class SalaryJARLoader6 extends SecureClassLoader {
	private String jarFile;

	public SalaryJARLoader6(String jarFile) {
		this.jarFile = jarFile;
	}

	@Override
	public Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException {
		//MAC 下会不断加载 Object 类，出现栈溢出的问题
//		if(name.startsWith("com.roy")) {
//			return this.findClass(name);
//		}else {
//			return super.loadClass(name);
//		}

		// 把双亲委派机制反过来，先到子类加载器中加载，加载不到再去父类加载器中加载。
		Class<?> c = null;
		synchronized (getClassLoadingLock(name)) {
			c = findLoadedClass(name);
			if(c == null){
				c = findClass(name);
				if(c == null){
					c = super.loadClass(name,resolve);
				}
			}
		}
		return c;
	}

	@Override
	protected Class<?> findClass(String fullClassName) throws ClassNotFoundException {
		String classFilepath = fullClassName.replace('.', '/').concat(".class");
		System.out.println("重新加载类："+classFilepath);
		int code;
		try {
			// 访问jar包的url
			URL jarURL = new URL("jar:file:" + jarFile + "!/" + classFilepath);
			URLConnection urlConnection = jarURL.openConnection();
			urlConnection.setUseCaches(false);
			InputStream is = urlConnection.getInputStream();
//			InputStream is = jarURL.openStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			while ((code = is.read()) != -1) {
				bos.write(code);
			}
			byte[] data = bos.toByteArray();
			is.close();
			bos.close();
			return defineClass(fullClassName, data, 0, data.length);
		} catch (Exception e) {
//			e.printStackTrace();
			//当前类加载器出现异常，就会通过双亲委派，交由父加载器去加载
//			System.out.println("加载出现异常 ："+e.getMessage());
//			throw new ClassNotFoundException(e.getMessage());
			return null;
		}
	}
}
