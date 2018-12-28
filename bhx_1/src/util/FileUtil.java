package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;


/**
 * 文件操作工具类
 */
public class FileUtil {
	public static Logger logger = Logger.getLogger(FileUtil.class);
	
	public static Set<String> sets = new HashSet<String>();

	public static void main(String[] args) {
		refreshFileList("G:\\Music",".mp3");
		//moveFolder("G:\\music\\周杰伦", "E:\\Kugou");
	}
	
	/**
	 * 
	 * 功能描述: 获取文件项目路径
	 *
	 * @Title: getWebRootPath
	 * @return
	 * 
	 * @author : lyan 
	 * @date 2016年2月3日
	 */
	public static String getWebRootPath(){
		String webPath = FileUtil.class.getResource("/").getPath();
		webPath = webPath.substring(0, webPath.indexOf("WEB-INF"));
		return webPath;
	}
	
	/**
	 * 
	 * 功能描述: 过滤文件
	 *
	 * @Title: fileFilterList
	 * @param strPath
	 * @param suffix  后缀格式:.xxx
	 * 
	 * @author : lyan 
	 * @date 2016年2月1日
	 */
	public static void fileFilterList(String strPath, String suffix) {
		File dir = new File(strPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				fileFilterList(files[i].getAbsolutePath(), suffix);
			} else {
				String strFilePath = files[i].getAbsolutePath().toLowerCase();
				String strName = files[i].getName().toLowerCase();
				suffix = suffix.toLowerCase();
				if (strName.endsWith(suffix)) {
					boolean bFlag = sets.add(strName);
					if (bFlag == Boolean.FALSE) {
						// 删除重复文件
						removeFile(strFilePath);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * 功能描述: 过滤文件
	 *
	 * @Title: refreshFileList
	 * @param strPath
	 * @param suffix  后缀格式:.xxx
	 * 
	 * @author : lyan 
	 * @date 2016年2月1日
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List refreshFileList(String strPath, String suffix) {
		List list = new ArrayList();
		File dir = new File(strPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return null;
		}
		
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				refreshFileList(file.getAbsolutePath(), suffix);
			} else {
				String strName = file.getName().toLowerCase();
				suffix = suffix.toLowerCase();
				if (strName.endsWith(suffix)) {
					list.add(file);
				}
			}
		}
		
		return list;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param strFilePath
	 *            文件夹路径
	 */
	public static boolean mkdirFolder(String strFilePath) {
		boolean bFlag = false;
		try {
			File file = new File(strFilePath.toString());
			if (!file.getParentFile().exists()) {
				bFlag = file.getParentFile().mkdir();
			}
			if (!file.exists()) {
				bFlag = file.mkdir();
			}
		} catch (Exception e) {
			logger.error("新建目录操作出错" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return bFlag;
	}

	/**
	 * 
	 * 功能描述: 创建文件
	 *
	 * @Title: createFile
	 * @param strFilePath
	 * @param strFileContent
	 * @return
	 * 
	 * @author : lyan 
	 * @date 2016年2月1日
	 */
	public boolean createFile(String strFilePath, String strFileContent) {
		boolean bFlag = false;
		try {
			File file = new File(strFilePath.toString());
			if (!file.exists()) {
				bFlag = file.createNewFile();
			}
			if (bFlag == Boolean.TRUE) {
				FileWriter fw = new FileWriter(file);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(strFileContent.toString());
				pw.close();
			}
		} catch (Exception e) {
			logger.error("新建文件操作出错" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return bFlag;
	}

	/**
	 * 删除文件
	 * 
	 * @param strFilePath
	 * @return
	 */
	public static boolean removeFile(String strFilePath) {
		boolean result = false;
		if (strFilePath == null || "".equals(strFilePath)) {
			return result;
		}
		File file = new File(strFilePath);
		if (file.isFile() && file.exists()) {
			result = file.delete();
			if (result == Boolean.TRUE) {
				logger.debug("[REMOE_FILE:" + strFilePath + "删除成功!]");
			} else {
				logger.debug("[REMOE_FILE:" + strFilePath + "删除失败]");
			}
		}
		return result;
	}

	/**
	 * 删除文件夹(包括文件夹中的文件内容，文件夹)
	 * 
	 * @param strFolderPath
	 * @return
	 */
	public static boolean removeFolder(String strFolderPath) {
		boolean bFlag = false;
		try {
			if (strFolderPath == null || "".equals(strFolderPath)) {
				return bFlag;
			}
			File file = new File(strFolderPath.toString());
			bFlag = file.delete();
			if (bFlag == Boolean.TRUE) {
				logger.debug("[REMOE_FOLDER:" + file.getPath() + "删除成功!]");
			} else {
				logger.debug("[REMOE_FOLDER:" + file.getPath() + "删除失败]");
			}
		} catch (Exception e) {
			logger.error("FLOADER_PATH:" + strFolderPath + "删除文件夹失败!");
			e.printStackTrace();
		}
		return bFlag;
	}

	/**
	 * 移除所有文件
	 * 
	 * @param strPath
	 */
	public static void removeAllFile(String strPath) {
		File file = new File(strPath);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] fileList = file.list();
		File tempFile = null;
		for (int i = 0; i < fileList.length; i++) {
			if (strPath.endsWith(File.separator)) {
				tempFile = new File(strPath + fileList[i]);
			} else {
				tempFile = new File(strPath + File.separator + fileList[i]);
			}
			if (tempFile.isFile()) {
				tempFile.delete();
			}
			if (tempFile.isDirectory()) {
				removeAllFile(strPath + "/" + fileList[i]);// 下删除文件夹里面的文件
				removeFolder(strPath + "/" + fileList[i]);// 删除文件夹
			}
		}
	}
	 
	/**
	 * 创建目录
	 * 
	 * @param dir
	 *            目录
	 */
	public static void mkdir(String dir) {
		try {
			String dirTemp = dir;
			File dirPath = new File(dirTemp);
			if (!dirPath.exists()) {
				dirPath.mkdir();
			}
		} catch (Exception e) {
			logger.error("创建目录操作出错: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 新建文件
	 * 
	 * @param fileName
	 *            String 包含路径的文件名 如:E:\phsftp\src\123.txt
	 * @param content
	 *            String 文件内容
	 * 
	 */
	public static void createNewFile(String fileName, String content) {
		try {
			String fileNameTemp = fileName;
			File filePath = new File(fileNameTemp);
			if (!filePath.exists()) {
				filePath.createNewFile();
			}
			FileWriter fw = new FileWriter(filePath);
			PrintWriter pw = new PrintWriter(fw);
			String strContent = content;
			pw.println(strContent);
			pw.flush();
			pw.close();
			fw.close();
		} catch (Exception e) {
			logger.error("新建文件操作出错: " + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 *            包含路径的文件名
	 */
	public static void delFile(String fileName) {
		try {
			String filePath = fileName;
			File delFile = new File(filePath);
			delFile.getAbsoluteFile().delete();
		} catch (Exception e) {
			logger.error("删除文件操作出错: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹路径
	 */
	public static void delFolder(String folderPath) {
		try {
			// 删除文件夹里面所有内容
			delAllFile(folderPath);
			String filePath = folderPath;
			java.io.File myFilePath = new java.io.File(filePath);
			// 删除空文件夹
			myFilePath.delete();
		} catch (Exception e) {
			logger.error("删除文件夹操作出错" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            文件夹路径
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] childFiles = file.list();
		File temp = null;
		for (int i = 0; i < childFiles.length; i++) {
			// File.separator与系统有关的默认名称分隔符
			// 在UNIX系统上，此字段的值为'/'；在Microsoft Windows系统上，它为 '\'。
			if (path.endsWith(File.separator)) {
				temp = new File(path + childFiles[i]);
			} else {
				temp = new File(path + File.separator + childFiles[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + childFiles[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + childFiles[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param srcFile
	 *            包含路径的源文件 如：E:/phsftp/src/abc.txt
	 * @param dirDest
	 *            目标文件目录；若文件目录不存在则自动创建 如：E:/phsftp/dest
	 * @throws IOException
	 */
	public static void copyFile(String srcFile, String dirDest) {
		try {
			FileInputStream in = new FileInputStream(srcFile);
			mkdir(dirDest);
			FileOutputStream out = new FileOutputStream(dirDest + "/"
					+ new File(srcFile).getName());
			int len;
			byte buffer[] = new byte[1024];
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			out.close();
			in.close();
		} catch (Exception e) {
			logger.error("复制文件操作出错:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 复制文件夹
	 * 
	 * @param oldPath
	 *            String 源文件夹路径 如：E:/phsftp/src
	 * @param newPath
	 *            String 目标文件夹路径 如：E:/phsftp/dest
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			// 如果文件夹不存在 则新建文件夹
			mkdir(newPath);
			File file = new File(oldPath);
			String[] files = file.list();
			File temp = null;
			for (int i = 0; i < files.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + files[i]);
				} else {
					temp = new File(oldPath + File.separator + files[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] buffer = new byte[1024 * 2];
					int len;
					while ((len = input.read(buffer)) != -1) {
						output.write(buffer, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + files[i], newPath + "/"
							+ files[i]);
				}
			}
		} catch (Exception e) {
			logger.error("复制文件夹操作出错:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            包含路径的文件名 如：E:/phsftp/src/ljq.txt
	 * @param newPath
	 *            目标文件目录 如：E:/phsftp/dest
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动文件到指定目录，不会删除文件夹
	 * 
	 * @param oldPath
	 *            源文件目录 如：E:/phsftp/src
	 * @param newPath
	 *            目标文件目录 如：E:/phsftp/dest
	 */
	public static void moveFiles(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delAllFile(oldPath);
	}

	/**
	 * 移动文件到指定目录，会删除文件夹
	 * 
	 * @param oldPath
	 *            源文件目录 如：E:/phsftp/src
	 * @param newPath
	 *            目标文件目录 如：E:/phsftp/dest
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * 压缩文件
	 * 
	 * @param srcDir
	 *            压缩前存放的目录
	 * @param destDir
	 *            压缩后存放的目录
	 * @throws Exception
	 */
	public static void yaSuoZip(String srcDir, String destDir) throws Exception {
		String tempFileName = null;
		byte[] buf = new byte[1024 * 2];
		int len;
		// 获取要压缩的文件
		File[] files = new File(srcDir).listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(fis);
					if (destDir.endsWith(File.separator)) {
						tempFileName = destDir + file.getName() + ".zip";
					} else {
						tempFileName = destDir + "/" + file.getName() + ".zip";
					}
					FileOutputStream fos = new FileOutputStream(tempFileName);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					ZipOutputStream zos = new ZipOutputStream(bos);// 压缩包

					ZipEntry ze = new ZipEntry(file.getName());// 压缩包文件名
					zos.putNextEntry(ze);// 写入新的ZIP文件条目并将流定位到条目数据的开始处

					while ((len = bis.read(buf)) != -1) {
						zos.write(buf, 0, len);
						zos.flush();
					}
					bis.close();
					zos.close();

				}
			}
		}
	}

	/**
	 * 读取数据
	 * 
	 * @param inSream
	 * @param charsetName
	 * @return
	 * @throws Exception
	 */
	public static String readData(InputStream inSream, String charsetName)
			throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inSream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inSream.close();
		return new String(data, charsetName);
	}

	/**
	 * 一行一行读取文件，适合字符读取，若读取中文字符时会出现乱码
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static Set<String> readFile(String path) throws Exception {
		Set<String> datas = new HashSet<String>();
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		while ((line = br.readLine()) != null) {
			datas.add(line);
		}
		br.close();
		fr.close();
		return datas;
	}
	
	/**
	 * 
	 * 功能描述: 将图片转换成字节数组
	 *
	 * @Title: storeImage
	 * @param file
	 * @return
	 * 
	 * @author : lyan 
	 * @date 2016年2月3日
	 */
	public static byte[] storeImage(File file) {
		byte[] content = null;
		try {
            FileInputStream fin = new FileInputStream(file);
            ByteBuffer nbf = ByteBuffer.allocate((int) file.length());
            byte[] array = new byte[1024];
            int length = 0;
            while ((length = fin.read(array)) > 0) {
                if (length != 1024){
                    nbf.put(array, 0, length);
                }else{
                    nbf.put(array);
                }
            }
            fin.close();
            content = nbf.array();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
	
	/**
	 * Enhancement of java.io.File#createNewFile()
	 * Create the given file. If the parent directory  don't exists, we will create them all.
	 * @param file the file to be created
	 * @return true if the named file does not exist and was successfully created; false if the named file already exists
	 * @see java.io.File#createNewFile
	 * @throws IOException
	 */
	public static boolean createFile(File file) throws IOException {
		if(! file.exists()) {
			makeDir(file.getParentFile());
		}
		return file.createNewFile();
	}
	
	/**
	 * Enhancement of java.io.File#mkdir()
	 * Create the given directory . If the parent folders don't exists, we will create them all.
	 * @see java.io.File#mkdir()
	 * @param dir the directory to be created
	 */
	public static void makeDir(File dir) {
		if(! dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}
	
	 /**
     * 向文本文件中写入内容或追加新内容,如果append为true则直接追加新内容,<br>
     * 如果append为false则覆盖原来的内容<br>
     * 
     * @param path
     * @param content
     */
    public static void writeFile(String path, String content) {
        File writefile;
        try {
            writefile = new File(path);

            // 如果文本文件不存在则创建它
            if (writefile.exists() == false) {
                writefile.createNewFile();
                writefile = new File(path); // 重新实例化
            }

            FileOutputStream fw = new FileOutputStream(writefile);
            System.out.println("###content:" + content);
            fw.write(content.getBytes());
            fw.flush();
            fw.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
	
    /**
     * 复制文件
     * @param source
     * @param target
     */
    public static void nioTransferCopyFile(File source, File target) {  
        FileChannel in = null;  
        FileChannel out = null;  
        FileInputStream inStream = null;  
        FileOutputStream outStream = null;  
        try {  
            inStream = new FileInputStream(source);  
            outStream = new FileOutputStream(target);  
            in = inStream.getChannel();  
            out = outStream.getChannel();  
          
            in.transferTo(0, in.size(), out);  
           
            inStream.close();
            outStream.close(); 
            in.close();  
        	out.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
        	  
        }  
    }  
    
    
    
    /** 
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下 
     * @param sourceFilePath :待压缩的文件路径 
     * @param zipFilePath :压缩后存放路径 
     * @param fileName :压缩后文件的名称 
     * @return 
     */  
    public static String fileToZip(String sourceFilePath,String zipFilePath,String fileName){  
//        boolean flag = false;  
        File sourceFile = new File(sourceFilePath); 
        FileInputStream fis = null;
        
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
        String zipName = "";  
        
        if(sourceFile.exists() == false){  
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");
        }else{  
            try {  
                File zipFile = new File(zipFilePath + "/" + fileName +".zip");  
                if(zipFile.exists()){  
                    System.out.println(zipFilePath + "目录下存在名字为:" + fileName +".zip" +"打包文件.");  
                }else{  
                    File[] sourceFiles = sourceFile.listFiles();
                    if(null == sourceFiles || sourceFiles.length<1){  
                        System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                    }else{  
                        fos = new FileOutputStream(zipFile);  
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                        byte[] bufs = new byte[1024*10];  
                        for(int i=0;i<sourceFiles.length;i++){  
                            //创建ZIP实体，并添加进压缩包  
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                            zos.putNextEntry(zipEntry);  
                            //读取待压缩的文件并写进压缩包里  
                            fis = new FileInputStream(sourceFiles[i]);
                            bis = new BufferedInputStream(fis, 1024*10);  
                            int read = 0;  
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                                zos.write(bufs,0,read);  
                            }  
                        }  
//                        flag = true;  
                    }  
                }  
                zipName = zipFile.getName();  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } finally{  
                //关闭流  
                try {  
                    if(null != bis) bis.close();  
                    if(null != zos) zos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        return zipName;  
    }  
    
    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    
    /**
	 * 功能：Java读取txt文件的内容 步骤：1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
	 * 
	 * @param filePath
	 */
	public static String readTxtFile(String filePath) {
		try {
			String encoding = "utf-8";
			File file = new File(filePath);
			String str = "";
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
//					System.out.println(lineTxt);
					str += lineTxt + "\r\n";
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
			return str;
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return "";
	}
	
    /**
     * 更换文本中的指定内容
     * @param cont
     * @param dist
     * @return
     */
	public static boolean write(String cont, File dist) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(dist), "utf-8"));
			writer.write(cont);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
    
	public static void rewrite(File file, String data) {  
        BufferedWriter bw = null;  
        try {  
            bw = new BufferedWriter(new FileWriter(file));  
            bw.write(data);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (bw != null) {  
                try {  
                    bw.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
  
    public static List<String> readList(File file) {  
        BufferedReader br = null;  
        List<String> data = new ArrayList<String>();  
        try {  
            br = new BufferedReader(new FileReader(file));  
            for (String str = null; (str = br.readLine()) != null;) {  
                data.add(str);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (br != null) {  
                try {  
                    br.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return data;  
    }  
}

