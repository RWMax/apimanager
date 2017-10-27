package com.liuht.ec.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

import com.liuht.ec.base.util.ResultBean;
import com.liuht.ec.util.StringUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class XMLUtil {
	
	public static String bean2xml(Object object, String rootElement) {
		Class<?> beanClass = object.getClass();
		Document doc = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement(StringUtils.isEmpty(rootElement)? beanClass.getSimpleName() : rootElement);
		doc.add(root);
		Field[] fields = beanClass.getDeclaredFields();
		try {
			fields2xml(fields, object, root);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return doc.asXML();
	}
	
	private static void fields2xml(Field[] fields, Object object, Element body) throws IllegalArgumentException, IllegalAccessException {
		for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Object value = null;
            if (field.isAccessible()) {
				value = field.get(object);
			} else {
				field.setAccessible(true);
				value = field.get(object);
				field.setAccessible(false);
			}
            Element element = DocumentHelper.createElement(field.getName());
            if(value != null){
            	if(value instanceof List){
            		List<?> list = (List<?>) value;
            		for (Object object2 : list) {
            			fields2xml(object2.getClass().getDeclaredFields(), object2 , element);
					}
            	}else if(value instanceof Map) {
            		Map map = (Map) value;
            		__buildMap2xmlBody(element, new LinkedHashMap(map));
            	}else if(value.getClass().isArray()) {
            		Object[] objects = (Object[]) value;
            		for (int j = 0; j < objects.length; j++) {
						Object object2 = objects[j];
						fields2xml(object2.getClass().getDeclaredFields(), object2 , element);
					}
            	}else{
            		element.setText(value+"");
            	}
            	body.add(element);
            }
		}
	}
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
		ResultBean bean = new ResultBean();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("test", "双宿双飞");
		map.put("sss", "双宿双飞");
		bean.setError(false);
		bean.setSuccess_msg("成功");
		bean.setLists(map);
		System.out.println(bean2xml(bean, null));
	}
	
	/**
    * 根据Map组装xml消息体，值对象仅支持基本数据类型、String、BigInteger、BigDecimal，以及包含元素为上述支持数据类型的Map
    * 
    * @param vo
    * @param rootElement
    * @return
    */
    public static String map2xmlBody(Map<String, Object> vo, String rootElement) {
        Document doc = DocumentHelper.createDocument();
        Element body = DocumentHelper.createElement(rootElement);
        doc.add(body);
        __buildMap2xmlBody(body, vo);
        return doc.asXML();
    }
    public static String map2xmlBodyForGBK(LinkedHashMap<String, Object> vo, String rootElement) {
    	Document doc = DocumentHelper.createDocument();
    	Element body = DocumentHelper.createElement(rootElement);
    	doc.add(body);
    	__buildMap2xmlBody(body, vo);
    	doc.setXMLEncoding("GBK");
    	return doc.asXML();
    }
     
    private static void __buildMap2xmlBody(Element body, Map<String, Object> vo) {
        if (vo != null) {
            Iterator<String> it = vo.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (key!=null&&!"".equals(key)) {
                    Object obj = vo.get(key);
                    Element element = DocumentHelper.createElement(key);
                    if (obj != null) {
                        if (obj instanceof java.lang.String) {
                            element.setText((String) obj);
                        } else {
                            if (obj instanceof java.lang.Character || obj instanceof java.lang.Boolean || obj instanceof java.lang.Number
                                    || obj instanceof java.math.BigInteger || obj instanceof java.math.BigDecimal) {
                                element.setText(String.valueOf(obj));
                            } else if (obj instanceof java.util.Map) {
                                __buildMap2xmlBody(element, (Map<String, Object>) obj);
                            } else if (obj instanceof java.util.List){
								List list = (List) obj;
								for (int i = 0; i < list.size(); i++) {
									Map map = (Map)list.get(i);
									Element element2 = DocumentHelper.createElement(key);
									__buildMap2xmlBody(element2, map);
									body.add(element2);
								}
							}
                        }
                    }
                    body.add(element);
                }
            }
        }
    }
     
    /**
     * 根据xml消息体转化为Map
     * 
     * @param xml
     * @param rootElement
     * @return
     * @throws DocumentException
     */
    public static Map xmlBody2map(String xml) {
        Document doc;
		try {
			doc = DocumentHelper.parseText(xml);
			Element element = doc.getRootElement();
			Map vo = (Map) xml2map(element);
			return vo;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
    }
    
     
    @SuppressWarnings("unused")
	private static Map __buildXmlBody2map(Element body) {
        Map vo = new HashMap();
        if (body != null) {
            List<Element> elements = body.elements();
            for (Element element : elements) {
                String key = element.getName();
                if (key!=null&&!"".equals(key)) {
                    String text = element.getText().trim();
                    String value = text;
                    vo.put(key, value);
                }
            }
        }
        return vo;
    }
    
	private static Object xml2map(Element element) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Element> elements = element.elements();
		if (elements.size() == 0) {
			map.put(element.getName(), element.getText());
			if (!element.isRootElement()) {
				return element.getText();
			}
		} else if (elements.size() == 1) {
			map.put(elements.get(0).getName(), xml2map(elements.get(0)));
		} else if (elements.size() > 1) {
			// 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
			// 构造一个map用来去重
			Map<String, Element> tempMap = new HashMap<String, Element>();
			for (Element ele : elements) {
				tempMap.put(ele.getName(), ele);
			}
			Set<String> keySet = tempMap.keySet();
			for (String string : keySet) {
				Namespace namespace = tempMap.get(string).getNamespace();
				List<Element> elements2 = element.elements(new QName(string, namespace));
				// 如果同名的数目大于1则表示要构建list
				if (elements2.size() > 1) {
					List<Object> list = new ArrayList<Object>();
					for (Element ele : elements2) {
						list.add(xml2map(ele));
					}
					map.put(string, list);
				} else {
					// 同名的数量不大于1则直接递归去
					map.put(string, xml2map(elements2.get(0)));
				}
			}
		}

		return map;
	}
	    
}
