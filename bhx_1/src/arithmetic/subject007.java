package arithmetic;

import java.io.File;

public class subject007 {

    /**
     * 递归读取文件夹下的文件
     */

    public static void listFile(String path) {
        if (path == null) {
            return;// 因为下面的new File如果path为空，回报异常
        }
        File[] files = new File(path).listFiles();
        if (files == null) {
            System.out.println("is empty！！");
            return;
        }
        for(File file : files) {
            if (file.isFile()) {
                System.out.println(file.getName());
            } else if (file.isDirectory()) {
                System.out.println("Directory:"+file.getName());
                listFile(file.getPath());
            } else {
                System.out.println("Error");
            }
        }
    }

    public static void main(String[] args) {
        listFile("D:\\data");
    }
}
