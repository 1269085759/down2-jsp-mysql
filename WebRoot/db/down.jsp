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
	下载数据库中的文件。
	更新记录：
		2015-05-13 创建
*/
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String fid 	= request.getParameter("fid");
if (StringUtils.isBlank(fid))
{
	return;
}

String fileName = "网易云音乐.exe";//客户端保存的文件名
String filePath = "d:\\网易云音乐.exe";//路径

File f = new File(filePath);
long fileLen = f.length();
RandomAccessFile raf = new RandomAccessFile(filePath,"r");
FileInputStream in = new FileInputStream( raf.getFD() );

fileName = URLEncoder.encode(fileName,"UTF-8");
fileName = fileName.replaceAll("\\+","%20");
response.setContentType("application/x-download");
response.addHeader("Content-Disposition","attachment;filename=" + fileName);
OutputStream outp = response.getOutputStream();
String range = request.getHeader("Range");
long rangePos = 0;
if(range != null)
{
	//客户端提交的字段：0-100
	String[] rs = range.split("-");//bytes=10254
	int numBegin = rs[0].indexOf("=")+1;
	String pos = rs[0].substring(numBegin);
	
	rangePos = Long.parseLong(pos);//起始位置
}
response.addHeader("Content-Length",Long.toString(f.length()-rangePos));

response.setHeader("Content-Range",new StringBuffer()
	.append("bytes ")
	.append(rangePos)//起始位置
	.append("-")
	.append(Long.toString(fileLen-1)).toString()//结束位置
	);
byte[] b = new byte[1024];
int i = 0;
in.skip(rangePos);//定位索引

while((i = in.read(b)) > 0)
{
    outp.write(b, 0, i);
}
outp.flush();

in.close();
outp.close();
in 	 = null;
outp = null;
%>