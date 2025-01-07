package com.jack.jvm.oa;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureClassLoader;

//加载文件系统中的class文件
public class SalaryClassLoader extends SecureClassLoader {
	private String classPath;
	public SalaryClassLoader(String classPath) {
		this.classPath = classPath;
	}

	@Override
	protected Class<?> findClass(String fullClassName) throws ClassNotFoundException {
		String filePath = this.classPath + fullClassName.replace(".", "/").concat(".myclass");
		int code;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			fis.read();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				while ((code = fis.read()) != -1) {
					bos.write(code);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] data = bos.toByteArray();
			bos.close();
			return defineClass(fullClassName, data, 0, data.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
