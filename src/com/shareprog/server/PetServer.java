package com.shareprog.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.shareprog.constant.CommonConstant;
import com.shareprog.service.PetService;

/**
 * <p>Title: PetServer</p>  
 * <p>Description: 宠物服务端</p> 
 * @author chengli
 * @date 2021年4月21日
 */
public class PetServer {
	
	private static PetService petService = new PetService();

	public static void main(String[] args) throws IOException {
		ExecutorService executor = Executors.newFixedThreadPool(100);
		ServerSocket server = new ServerSocket(CommonConstant.PORT);
		while (true) {
			Socket client = server.accept();
			Runnable runnable = () -> {
				try (
						BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
						OutputStream outputStream = client.getOutputStream();
						) {
					String request = buf.readLine();
					String[] requestMethod = judgeRequest(request);
					String response = analysis(requestMethod);
					outputStream.write(response.getBytes("UTF-8"));
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
					try {
						server.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} 
			};
			executor.submit(runnable);
		}
	}

	/**
	 * <p>Title: analysis</p>  
	 * <p>Description: 分析请求返回相应的结果方案</p>  
	 * @param requestMethod
	 * @return
	 */
	private static String analysis(String[] requestMethod) {
		if (requestMethod.length == 0) {
			return CommonConstant.RESPONSE_FAILURE;
		}
		switch (requestMethod[0]) {
		case CommonConstant.REQUEST_GET:
			return petService.adoptPet(requestMethod);
		case CommonConstant.REQUEST_LIST:
			return petService.listPet2();
		case CommonConstant.REQUEST_LIST2:
			return petService.listPet1();
		default:
			return CommonConstant.RESPONSE_FAILURE;
		}
	}

	/**
	 * <p>Title: judgeRequest</p>  
	 * <p>Description: 判定发起的请求</p>  
	 * @param request
	 * @return
	 */
	private static String[] judgeRequest(String request) {
		if (request.isEmpty()) {
			return new String[0];
		}
		return request.split(CommonConstant.SPACE);
	}
}
