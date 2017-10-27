package com.liuht.plugin.dwr;

import org.directwebremoting.impl.DefaultScriptSessionManager;

import com.liuht.plugin.dwr.listener.DWRScriptSessionListener;

public class DWRScriptSessionManager extends DefaultScriptSessionManager{
	 public DWRScriptSessionManager(){
        //绑定一个ScriptSession增加销毁事件的监听器
        this.addScriptSessionListener( new DWRScriptSessionListener());
	 }
}
