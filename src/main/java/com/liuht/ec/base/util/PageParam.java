package com.liuht.ec.base.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings({"rawtypes","unchecked"})
public class PageParam extends HashMap implements Map,Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Map map = null;
	private HttpServletRequest request;
	private List list;
	private Long total;
	private List footer;
	
	public PageParam() {
		map = new HashMap();
	}
	
	public PageParam(HttpServletRequest request){
		this.request = request;
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap(); 
		Iterator entries = properties.entrySet().iterator(); 
		Map.Entry entry; 
		String name = "";  
		String value = "";
		StringBuilder sb = null;
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next(); 
			name = (String) entry.getKey(); 
			Object valueObj = entry.getValue(); 
			if(null == valueObj){ 
				value = ""; 
			}else if(valueObj instanceof String[]){ 
				sb = new StringBuilder();
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){
					sb.append(values[i] + ",");
				}
				value = sb.substring(0, sb.length()-1); 
			}else{
				value = valueObj.toString(); 
			}
			returnMap.put(name, value.trim()); 
		}
		map = returnMap;
	}
	
	@Override
	public Object get(Object key) {
		Object obj = null;
		if(map.get(key) instanceof Object[]) {
			Object[] arr = (Object[])map.get(key);
			obj = request == null ? arr:(request.getParameter((String)key) == null ? arr:arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}
	
	public String getString(Object key) {
		return get(key)+"";
	}
	
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}
	
	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set entrySet() {
		return map.entrySet();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set keySet() {
		return map.keySet();
	}

	@Override
	public void putAll(Map t) {
		map.putAll(t);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection values() {
		return map.values();
	}
	
	/**
	 * 获取分页start
	 */
	public Integer getStart() {
		Integer pageSize = getPageSize();
		if (pageSize == null) {
			return null;
		}
		String page = map.get("page") == null ? "1":(String)map.get("page");
		return (Integer.valueOf(page)-1)*pageSize;
	}
	
	/**
	 * 获取分页pageSize
	 */
	public Integer getPageSize() {
		if(map == null){
			return null;
		}
		String rows = map.get("rows") == null?"10":(String)map.get("rows");
		return Integer.valueOf(rows);
	}
	
	// bootstrap table 分页
	public Integer getOffsetStart() {
		String offset = map.get("offset") == null ? "1" : (String) map.get("offset");
		return Integer.valueOf(offset);
	}
	
	public Integer getOffsetPageSize() {
		if(map == null){
			return null;
		}
		String rows = map.get("limit") == null ? "10" : (String) map.get("limit");
		return Integer.valueOf(rows);
	}
	
	/**
	 * 获取map
	 */
	public Map getMap() {
		return this.map;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	
	public Map toDatagridMap(){
		Map map = new HashMap();
		map.put("rows", getList());
		map.put("total", getTotal());
		return map;
	}
	
	public Map toDatagridMapWithFooter(){
		Map map = new HashMap();
		map.put("rows", getList());
		map.put("total", getTotal());
		map.put("footer", getFooter());
		return map;
	}

	public List getFooter() {
		return footer;
	}

	public void setFooter(List footer) {
		this.footer = footer;
	}
}
