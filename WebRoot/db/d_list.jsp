<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ 
	page contentType="text/html;charset=UTF-8"%><%@ 
	page import="Xproer.*" %><%@ 
	page import="org.apache.commons.lang.*" %><%@ 
	page import="java.net.URLDecoder" %><%@
	page import="java.net.URLEncoder" %><%@
	page import="java.io.*" %><%
/*
	此页面主要用来向数据库添加一条记录。
	一般在 HttpUploader.js HttpUploader_MD5_Complete(obj) 中调用
	更新记录：
		2012-05-24 完善
		2012-06-29 增加创建文件逻辑，
*/
String uid 		= request.getParameter("uid");
String cbk		= request.getParameter("callback");

if (StringUtils.isBlank(uid))
{
	out.write(cbk + "({\"value\":null})");
	return;
}

DnFile db = new DnFile();
String json = db.GetAll(Integer.parseInt(uid));
json = URLEncoder.encode(json,"utf-8");
json = json.replaceAll("\\+","%20");

out.write(cbk + "({\"value\":\""+json+"\"})");
%>