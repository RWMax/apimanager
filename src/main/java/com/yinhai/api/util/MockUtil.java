package com.yinhai.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.utils.DateUtils;
import org.apache.http.entity.ContentType;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yinhai.api.common.domain.InterfaceWithBLOBs;
import com.yinhai.ec.base.exception.BaseAppException;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.common.XMLUtil;
import com.yinhai.ec.util.StringUtils;

@SuppressWarnings("unchecked")
public class MockUtil {

    /**
     * 根据contentTyp 获取参数
     *
     * @return Map<String,Object>
     * @author 刘惠涛
     */
    public static Map<String, Object> getParam(InterfaceWithBLOBs api, HttpServletRequest request) {
        Map<String, Object> result = null;
        if (api.getContenttype().equals(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())) {
            // application/x-www-form-urlencoded 键值对的形式
            PageParam pageParam = new PageParam(request);
            result = pageParam.getMap();
        } else {
            InputStream is;
            try {
                is = request.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) is));
                String line = null;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String str = sb.toString();
                if (StringUtils.isEmpty(str)) {
                    return result;
                }
                // 根据contentType解析参数
                if (api.getContenttype().equals(ContentType.APPLICATION_JSON.getMimeType())) {
                    result = JSONObject.parseObject(str);
                } else if (api.getContenttype().equals(ContentType.APPLICATION_XML.getMimeType())) {
                    result = XMLUtil.xmlBody2map(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 验证请求参数
     *
     * @return void
     * @author 刘惠涛
     */
    public static void checkRequestArgs(String req, Map<String, Object> param) throws BaseAppException {
        JSONArray requestargs = JSONArray.parseArray(req);
        for (int i = 0; i < requestargs.size(); i++) {
            JSONObject object = (JSONObject) requestargs.get(i);
            // 验证是否是必传参数
            if (object.getBooleanValue("isRequired")) {
                if (param == null || !param.containsKey(object.get("paramName"))) {
                    throw new BaseAppException("参数: " + object.get("paramName") + " ,是必传参数!");
                }
            }
        }
    }

    /**
     * 根据预先定义好的返回数据自动生成返回数据
     *
     * @return Map<String,Object>
     * @author 刘惠涛
     */
    public static Map<String, Object> getResultBYResponseArgs(JSONArray responseargs) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (int i = 0; i < responseargs.size(); i++) {
            JSONObject object = (JSONObject) responseargs.get(i);
            String paramType = object.get("paramType") + "";
            if ("object".equals(paramType)) {
                JSONArray pps = (JSONArray) object.get("defaultValue");
                Map<String, Object> pms = getResultBYResponseArgs(pps);
                result.put(object.get("paramName") + "", pms);
            } else if ("array".equals(paramType)) {
                JSONArray pps = (JSONArray) object.get("defaultValue");
                Random random = new Random();
                int count = random.nextInt(10);
                if (count == 0) count = 5;
                List<Map<String, Object>> array = new ArrayList<Map<String, Object>>(count);
                for (int j = 0; j < count; j++) {
                    Map<String, Object> pms = getResultBYResponseArgs(pps);
                    array.add(pms);
                }
                result.put(object.get("paramName") + "", array);
            } else {
                if (StringUtils.isEmpty(object.get("defaultValue"))) {
                    if ("string".equals(paramType)) {
                        result.put(object.get("paramName") + "", UUID.randomUUID().toString().replaceAll("-", "").substring(1, 10));
                    } else if ("number".equals(paramType)) {
                        result.put(object.get("paramName") + "", Math.random());
                    } else if ("boolean".equals(paramType)) {
                        result.put(object.get("paramName") + "", Boolean.TRUE);
                    } else if ("date".equals(paramType)) {
                        result.put(object.get("paramName") + "", DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    } else if ("textarea".equals(paramType)) {
                        result.put(object.get("paramName") + "", UUID.randomUUID().toString().replaceAll("-", "").substring(1, 10));
                    } else {
                        result.put(object.get("paramName") + "", "mock");
                    }
                } else {
                    result.put(object.get("paramName") + "", object.get("defaultValue"));
                }
            }
        }
        return result;
    }

    /**
     * 根据datatype 返回数据
     *
     * @return Object
     * @throws IOException
     * @author 刘惠涛
     */
    public static String handleResult(Map<String, Object> result, InterfaceWithBLOBs api) {
        String datatype = api.getDatatype();
        if (HYConst.DataType.JSON.getName().equals(datatype)) {
            return JSONObject.toJSONString(result);
        } else if (HYConst.DataType.XML.getName().equals(datatype)) {
            return XMLUtil.map2xmlBody(result, api.getResponserootelement());
        }
        return null;
    }
}
