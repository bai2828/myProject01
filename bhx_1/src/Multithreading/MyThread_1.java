package Multithreading;

public class MyThread_1 extends Thread{
	
	private int count = 5;
	
	@SuppressWarnings("static-access")
	@Override
	//加锁与不加锁
	public synchronized void run() {
		count--;
		System.out.println(this.currentThread().getName()+" count="+count);
	}
	
	public static void main(String[] args) {
		/**
		 * 分析：当多个我程访 myThread的run方法时，以排从的方式进行处理（这里排队是按据CPU分配的先后顺序而定的），
		 * 一个线程想要执行synchronized然饰的方法里的代码：
		 * 1尝试获得锁
		 * 2如果拿到锁，执行synchronized代码体内容：拿不到锁，这个能程就会不断的尝试获得这把锁，直到拿到为止，
		 * 而且是多个线程同时去竞争这把锁。（也就是会有锁竞争的问题）
		 */
		MyThread_1 myThread = new MyThread_1();
		Thread t1 = new Thread(myThread,"t1");
		Thread t2 = new Thread(myThread,"t2");
		Thread t3 = new Thread(myThread,"t3");
		Thread t4 = new Thread(myThread,"t4");
		Thread t5 = new Thread(myThread,"t5");
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
	}
}
