package com.avior.academic.service;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;

public class ServicioGenerico {

	public static Object changeNullValues(Object pojo) {
		Class<?> clase = pojo.getClass();
		try {
			for (PropertyDescriptor pd : Introspector.getBeanInfo(clase).getPropertyDescriptors()) {
				
				if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
//					System.out.println(pd.getReadMethod());
					Object field = pd.getReadMethod().invoke(clase.cast(pojo));
					if(field==null){
						if(pd.getReadMethod().toString().contains("java.lang.String")){
							pd.getWriteMethod().invoke(clase.cast(pojo), "");
						}
						
					}
				}
			}
		} catch (IllegalArgumentException | IntrospectionException
				| SecurityException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return pojo;
	}

	public static <T> T nvl(T arg0, T arg1) {
		return (arg0 == null) ? arg1 : arg0;
	}

	public String getBase64ImageFile(String path) throws IOException {
		byte[] bImage = extractBytes(path);
		return Base64.encodeBase64String(bImage);

	}

	private byte[] extractBytes(String ImageName) throws IOException {

		File imgPath = new File(ImageName);
		BufferedImage bufferedImage = ImageIO.read(imgPath);

		WritableRaster raster = bufferedImage.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

		return (data.getData());
	}

}
