package com.shareprog.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.shareprog.constant.CommonConstant;
import com.shareprog.enums.PetSpecies;

/**
 * @ClassName: SocketClient
 * @Description: 宠物客户端
 * @author cl
 * @date 2021年4月20日
 */
public class PetClient {
	
	private static AtomicInteger sum = new AtomicInteger(1);
	
	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(100);
		String host = System.getProperty("myapplication.ip");

		Runnable runnable = () -> {
			try {
				Socket client = new Socket(host, CommonConstant.PORT);
				String request = CommonConstant.REQUEST_GET + CommonConstant.SPACE + PetSpecies.randomPetEnum();
				if (sum.get() > 10000) {
					request = CommonConstant.REQUEST_LIST2;
				} else {
					sum.incrementAndGet();
				}
				System.out.println(request);
				client.getOutputStream().write(request.getBytes(CommonConstant.UTF_8));
				client.shutdownOutput();
				InputStream inputStream = client.getInputStream();
				byte[] bytes = new byte[CommonConstant.BYTE_LENGTH_1024];
				int len;
				StringBuilder sb = new StringBuilder();
				while ((len = inputStream.read(bytes)) != -1) {
					sb.append(new String(bytes, 0, len, CommonConstant.UTF_8));
				}
				System.out.println(sb);
				client.close();
				if (request.equals(CommonConstant.REQUEST_LIST2)) {
					long endTime = System.currentTimeMillis();
					System.out.println(endTime - startTime);
					System.exit(0);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		executor.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MILLISECONDS);
		
	}
}
