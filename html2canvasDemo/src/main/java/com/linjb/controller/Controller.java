package com.linjb.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@RequestMapping(value = { "/qsupload" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public String petUpgradeTarget(HttpServletRequest request, String data) {
		Base64 base64 = new Base64();
		try {
			byte[] k = base64.decode(data.substring("data:image/jpeg;base64,".length()));
			InputStream is = new ByteArrayInputStream(k);
			String fileName = UUID.randomUUID().toString();
			String imgFilePath = "D:\\" + fileName + ".jpg";

			BufferedImage tag = ImageIO.read(is);

			ImageIO.write(tag, "jpg", new File(imgFilePath));
			return fileName;
		} catch (Exception localException) {
		}
		return "error";
	}
	@RequestMapping(value = { "/download" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public void download(HttpServletRequest request,HttpServletResponse response,String fileName){
		try {
    		if(null==fileName||fileName.equals("")){
    			throw new Exception("答题卡文件名不能为空！");
    		}
    		response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            //处理下载弹出框名字的编码问题
            response.setHeader("Content-Disposition", "attachment;fileName="
                    + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
            // 原始截图保存根路径
            //利用输入输出流对文件进行下载
    		File file=new File("D:\\" + fileName );
    		if(!file.exists()){
    			throw new Exception("答题卡文件不存在！");
    		}
            InputStream inputStream = new FileInputStream(file);
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            // 这里主要关闭。
            os.close();
            inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}