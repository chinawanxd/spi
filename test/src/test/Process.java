package test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Process {
public static void main(String[] args) {
	CountDownLatch latch = new CountDownLatch(1);
	try {
		java.lang.Process pr=Runtime.getRuntime().exec("");
		TimeUnit.SECONDS.sleep(5);
		latch.countDown();
		System.out.println("sdfsdfsdf");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
