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
public class SalaryJARLoader extends SecureClassLoader {
	private String jarFile;

	public SalaryJARLoader(String jarFile) {
		this.jarFile = jarFile;
	}

	@Override
	protected Class<?> findClass(String fullClassName) throws ClassNotFoundException {
		String classFilepath = fullClassName.replace('.', '/').concat(".class");
		System.out.println("重新加载类："+classFilepath);
		int code;
		try {
			// 访问jar包的url
			URL jarURL = new URL("jar:file:" + jarFile + "!/" + classFilepath);
//			InputStream is = jarURL.openStream();
			URLConnection urlConnection = jarURL.openConnection();
			// 不使用缓存 不然有些操作系统下会出现jar包无法更新的情况
			urlConnection.setUseCaches(false);
			InputStream is = urlConnection.getInputStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			while ((code = is.read()) != -1) {
				bos.write(code);
			}
			byte[] data = bos.toByteArray();
			is.close();
			bos.close();
			return defineClass(fullClassName, data, 0, data.length);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("加载出现异常 ："+e.getMessage());
			throw new ClassNotFoundException(e.getMessage());
//			return null;
		}
	}
}
