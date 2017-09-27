package com.qg.controler;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
/**
 * 
 * @author qg
 *
 */

@Controller
public class FileUpAndDownloadControler {
	private static final String rootPath = "src/main/resources/static/upload/";
	//文件上传页面
	@RequestMapping("/up")
	public String  index(){
		return "filelist";
	}
	//上传方法
	@RequestMapping(value="/uploadfile",method=RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file) {
		String filename = file.getOriginalFilename();
		System.out.println(filename);
		String message ="";
		//获取文件后缀名
		String fname = filename.substring(filename.lastIndexOf("."));
		System.out.println(fname);
		if(!(fname.equalsIgnoreCase(".mp3") || fname.equalsIgnoreCase(".mp4"))){
			message ="暂时只支持视频MP4格式和音频MP3格式文件上传";
			return message;
		}
		if(!file.isEmpty()){
			String name = rootPath + filename;
			try {
				Streams.copy(file.getInputStream(),new FileOutputStream(name),true);
				message = "上传成功";
			} catch (IOException e) {
				e.printStackTrace();
				message = "上传失败";
			}	
			
		}
		return message;
		
		} 
		 
		
		
	//查看上传成功的
	@RequestMapping(value="/")
	public String list(HttpServletRequest request){
		File file = new File(rootPath);
		File[] fileList = file.listFiles();//获取当前文件夹下所有文件
		List<String> list = new ArrayList<String>();
		if(fileList.length!=0){
			for(File file1:fileList){//遍历文件
				list.add(file1.getName());
			}
		}
		request.setAttribute("list", list);
		return "filelist";
	}
	//下载
	@RequestMapping(value="/down")
	public void down(HttpServletResponse response,String fileName){
		String path = rootPath + fileName;//设置下载文件的根目录
        File file = new File(path);
        if (file.exists()) {
            String mimeType = "application/octet-stream";
            response.setContentType(mimeType);
            String encodedFileName = null;//解决中文名乱码
            try {
                encodedFileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setHeader("Content-Disposition", "attachment;fileName=" + encodedFileName);//设置响应头
            InputStream in = null;
            //文件下载
            try {
                in = new BufferedInputStream(new FileInputStream(file));
                FileCopyUtils.copy(in, response.getOutputStream());
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}
	
}
	

