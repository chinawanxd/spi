package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

public class Getweather {
    public static void main(String[] args) {
		//http://api.weatherdt.com/common/?area=101010100&type=index&key=4a27f641309479d27217a55616d1a12a
    	
    	List<String> writeList=new ArrayList<String>();
    	try{ 
    		//String str=HttpClientUtil.sellerGetUrl("http://api.weatherdt.com/common/?area=101010100&type=index&key=4a27f641309479d27217a55616d1a12a");
    		List<String> listStation=getStation("G:\\20.csv");
    		for (String line : listStation) {
    			String[] citys=line.split(",");
    			String returnJson=HttpClientUtil.sellerGetUrl("http://api.weatherdt.com/common/?area="+citys[0]+"&type=index&key=4a27f641309479d27217a55616d1a12a");
    			String indexCon=getJson(returnJson);
    			writeList.add(citys[1]+","+citys[2]+","+citys[3]+","+indexCon);
			}
    		
    		writeStation("G:\\indexContent.csv", writeList);
    		
    		//System.out.println(writeList.size());
    		
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
	}
    
    private static String getJson(String returnJson){
    	//String jsonIndex="{\"index\":{\"24h\":{\"101010100\":{\"1001004\":[{\"000\":\"20181010\",\"001\":{\"001002\":\"不易发\",\"001001\":\"过敏指数\",\"001003\":\"天气条件不易诱发过敏，可放心外出，除特殊体质外，无需担心过敏问题。\"}},{\"000\":\"20181011\",\"001\":{\"001002\":\"不易发\",\"001001\":\"过敏指数\",\"001003\":\"天气条件不易诱发过敏，可放心外出，除特殊体质外，无需担心过敏问题。\"}},{\"000\":\"20181012\",\"001\":{\"001002\":\"不易发\",\"001001\":\"过敏指数\",\"001003\":\"天气条件不易诱发过敏，可放心外出，除特殊体质外，无需担心过敏问题。\"}},{\"000\":\"20181013\",\"001\":{\"001002\":\"不易发\",\"001001\":\"过敏指数\",\"001003\":\"天气条件不易诱发过敏，可放心外出，除特殊体质外，无需担心过敏问题。\"}}],\"000\":\"201810101100\"}}}}";
    	String result="";
    	try{
    		Document doc=Document.parse(returnJson);
        	Document doc_24h=(Document)doc.get("index");
        	Document doc_index=(Document)doc_24h.get(doc_24h.keySet().iterator().next());
            Document stationdoc=(Document)doc_index.get(doc_index.keySet().iterator().next());
            List<Document> listdoc=(List<Document>)stationdoc.get(stationdoc.keySet().iterator().next());
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        	String nowDate=sdf.format(new Date());
        	for (Document document : listdoc) {
        		String str=(String)document.get("000");
    			if(str.equals(nowDate)){
    				Document content=(Document)document.get("001");
    				result=content.get("001002")+","+content.get("001003");
//    				System.out.println(content.get("001002"));
//    				System.out.println(content.get("001003"));
    			}
    		}
    	}catch (Exception e) {
			// TODO: handle exception
    		System.out.println(returnJson);
		}
    	
        return result;
    }
    
    //读取文件
    private static List<String> getStation(String filepath){
    	List<String> stationList=new ArrayList<String>();
    	try {
    		//读取文件(字节流)
    		//这里主要是涉及中文
    		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),"gbk"));
            //写入相应的文件
            //PrintWriter out = new PrintWriter(new FileWriter("d:\\2.txt"));
            //读取数据
            //循环取出数据
            //byte[] bytes = new byte[1024];
            String len = "";
            while ((len = in.readLine()) != null){
                //System.out.println(len);
            	stationList.add(len);
                //写入相关文件
                //out.write(len);
            }
            //清楚缓存
            //out.flush();
            //关闭流
            in.close();
            System.out.println(stationList.size());
            //out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
        return stationList;
    }
    
    //写入文件
    private static void writeStation(String filepath, List<String> content){
    	try {
    		//读取文件(字节流)
    		//这里主要是涉及中文
    		//BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),"gbk"));
            //写入相应的文件
            PrintWriter out = new PrintWriter(new FileWriter(filepath));
            //读取数据
            //循环取出数据
            //byte[] bytes = new byte[1024];
            for (String string : content) {
            	out.write(string);
                out.println();
			}
            //清楚缓存
            out.flush();
            //关闭流
            out.close();
            System.out.println("end");
            //out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    
}
