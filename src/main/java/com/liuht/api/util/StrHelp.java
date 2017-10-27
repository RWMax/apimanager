package com.liuht.api.util;

import java.io.UnsupportedEncodingException;

/**
 * @author:TANQINGPING
 * @version:1.0 2017/1/5
 * package:com.liuht.api.util
 * <p>Title: StrHelp.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 *
 */

public class StrHelp {
    public static String getChinese(String s) {
        try {
            return new String(s.getBytes("gb2312"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }
}
