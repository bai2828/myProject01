package Multithreading;
/**
 * 调用start()方法与直接调用run()方法的区别
 * @author bhx
 *
 */
public class MyThread_2 extends Thread{

	private String title;
    public MyThread_2(String title){
        this.title=title;
    }
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            System.out.println(this.title+".x="+i);
        }
    }
    
    public static void main(String[] args) {
    	MyThread_2 mt1=new MyThread_2("线程1");
    	MyThread_2 mt2=new MyThread_2("线程2");
    	MyThread_2 mt3=new MyThread_2("线程3");
        mt1.run();
        mt2.run();
        mt3.run();//只是顺序执行，不是多线程    
        
        System.out.println("***********");
        
        mt1.start();
        mt2.start();
        mt3.start();//调用Thread类的start方法才是正确启动多线程的方式
    }
}
