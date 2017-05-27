package com.taotao.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.taotao.utils.FastDFSClient;

public class TestFastDFS {

	@Test
	public void uploadFile() throws Exception {
		//1、添加jar包
		//2、创建配置文件，配置Tracker服务器的地址
//		3、加载配置文件
		ClientGlobal.init("H:/JavaEE_TaoTao/taotao-manager-web/src/main/resources/resource/client.conf");
//		4、创建TrackerClient对象\
		TrackerClient trackerClient = new TrackerClient();
//		5、使用TrackerClient获得TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
//		6、创建StorageServer的引用null就可以
		StorageServer storageServer = null;
//		7、创建StorageClient对象，两个参数TrackerClient，TrackerServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//		8、使用StorageClient上传文件
		String[] strings = storageClient.upload_file("C:/Users/Administrator/Pictures/timg.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
		
	}
	
	@Test
	public void testFastDfsClient() throws Exception{
		FastDFSClient fastDFSClient = new FastDFSClient("H:/JavaEE_TaoTao/taotao-manager-web/src/main/resources/resource/client.conf");
		String filename = fastDFSClient.uploadFile("C:/Users/Administrator/Pictures/timg.jpg");
		System.out.println(filename);
	}
}
