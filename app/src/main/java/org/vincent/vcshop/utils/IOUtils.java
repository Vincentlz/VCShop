package org.vincent.vcshop.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 流相关
 * Created by Vincent on 2016/10/15.
 */
public class IOUtils {
	/** 关闭流 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				LogUtils.e(e);
			}
		}
		return true;
	}
}
