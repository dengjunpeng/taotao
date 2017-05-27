package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.utils.FastDFSClient;
import com.taotao.utils.JsonUtils;

/**
 * 图片上传Controller
 * 
 * @author Administrator
 *
 */
@Controller
public class PictureController {

	//从属性文件中取值，首先需要在springmvc的配置文件中配置
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String picUpload(MultipartFile uploadFile) {
		try {

			// 接受上传的文件

			// 取扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

			// 上传到图片服务器
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), ext);
			
			// 响应上传图片的url
			url = IMAGE_SERVER_URL + url;
//			System.out.println(url);
			
			Map result = new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
//			兼容firefix
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			
			Map result = new HashMap<>();
			result.put("error", 1);
			result.put("message", "图片上传失败");
			return JsonUtils.objectToJson(result);
		}

	}
}
