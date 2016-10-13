<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ 
	page contentType="text/html;charset=UTF-8"%><%@ 
	page import="Xproer.*" %><%@ 
	page import="java.net.URLDecoder" %><%@ 
	page import="java.net.URLEncoder" %><%@ 
	page import="org.apache.commons.lang.*" %><%@ 
	page import="com.google.gson.FieldNamingPolicy" %><%@ 
	page import="com.google.gson.Gson" %><%@ 
	page import="com.google.gson.GsonBuilder" %><%@ 
	page import="com.google.gson.annotations.SerializedName" %><%@ 
	page import="java.io.*" %><%
/*
	此页面主要用来向数据库添加一条记录。
	一般在 HttpUploader.js HttpUploader_MD5_Complete(obj) 中调用
	更新记录：
		2012-05-24 完善
		2012-06-29 增加创建文件逻辑，
*/
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String uid 		 = request.getParameter("uid");
String cbk  	 = request.getParameter("callback");//jsonp
String fileLoc	 = request.getParameter("file"); 
fileLoc 		 = fileLoc.replaceAll("\\+","%20");
fileLoc			 = URLDecoder.decode(fileLoc,"UTF-8");//utf-8解码

if (StringUtils.isBlank(uid))
{
	out.write(cbk+"({\"value\":null})");
	return;
}

Gson g = new Gson();
DnFileInf inf = g.fromJson(fileLoc,DnFileInf.class);

DnFile db = new DnFile();
inf.idSvr = db.Add(inf);

String json = g.toJson(inf);
json = URLEncoder.encode(json,"UTF-8");
json = json.replaceAll("\\+","%20");
json = cbk + "({\"value\":\"" + json + "\"})";//返回jsonp格式数据。
out.write(json);%>