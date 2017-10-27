package com.liuht.ec.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
* @package com.liuht.ec.base.util
* <p>Title: ObjectUtils.java</p>
* <p>Description:ObjectUtils </p>
* <p>Copyright: Copyright (c) 2016</p>
*
* @author 刘惠涛
* @date 2016年3月22日 上午11:19:48
* @version 1.0
 */
@SuppressWarnings({ "rawtypes" })
public class ObjectUtils {
	public ObjectUtils() {
	}

	public static boolean isCheckedException(Throwable ex) {
		return !(ex instanceof RuntimeException) && !(ex instanceof Error);
	}
	
	public static boolean isArray(Object obj) {
		return obj != null && obj.getClass().isArray();
	}

	public static boolean isEmpty(Object array[]) {
		return array == null || array.length == 0;
	}

	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		if (obj.getClass().isArray())
			return Array.getLength(obj) == 0;
		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;
		if (obj instanceof Collection)
			return ((Collection) obj).isEmpty();
		if (obj instanceof Map)
			return ((Map) obj).isEmpty();
		else
			return false;
	}
}
