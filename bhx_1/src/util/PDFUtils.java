package util;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

/**
 * This class provides methods to create new file/directory
 *
 */
public class PDFUtils {
	
	public static void txt2pdf(String txtPath, String pdfPath)
			throws DocumentException, IOException {
		Document document = new Document(PageSize.A1);

		InputStream is = new FileInputStream(txtPath);
		// 读取文本内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));//utf-8
		PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
		/**
		 * 新建一个字体,iText的方法 STSongStd-Light 是字体，在iTextAsian.jar 中以property为后缀
		 * UniGB-UCS2-H 是编码，在iTextAsian.jar 中以cmap为后缀 H 代表文字版式是 横版， 相应的 V 代表 竖版
		 */
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", false);

		Font fontChinese = new Font(bfChinese, 30, Font.NORMAL, Color.BLACK);

		// 打开文档，将要写入内容
		document.open();
		String line = reader.readLine();
		
		while (line != null) {
			System.err.println("line === " + line);
			Paragraph pg = new Paragraph(line, fontChinese);
			document.add(pg);
			line = reader.readLine();
		}
		document.close();
		reader.close();
		is.close();
	}
	

	public void img2pdf(String imgPath, String pdfPath)
			throws MalformedURLException, IOException, DocumentException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
		Image img = Image.getInstance(imgPath);
		float pdfHeight = document.getPageSize().getHeight();
		float pdfWidth = document.getPageSize().getWidth() - 70;
		float imgHeight = img.getPlainHeight();
		float imgWidth = img.getPlainWidth();
		if (imgHeight > pdfHeight || imgWidth > pdfWidth) {
			if (pdfHeight / imgHeight > pdfWidth / imgWidth) {
				img.scalePercent(pdfWidth / imgWidth * 100);
			} else {
				img.scalePercent(pdfHeight / imgHeight * 100);
			}
		}

		document.open();
		document.add(img);
		document.close();
	}
	
	/**
     * html转换成pdf
     * @param html  html字符串（输入的HTML页面必须是标准的XHTML页面。页面的顶上必须是这样的格式：
     * <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
     * <html xmlns="http://www.w3.org/1999/xhtml">
     * 并且HTML页面的语法必须是非常严谨的，所有标签 都必须闭合等等（由于flying-Saucer做了XML解析的工作，不严谨会报错的。），这是对页面的第一个要求。）
     * @param outputFile pdf文件输出地址
     * @throws Exception
     */
    public static void htmlToPdf(String html, String outputFilePath) throws Exception {       
	    
	    OutputStream os = new FileOutputStream(outputFilePath);       
	    ITextRenderer renderer = new ITextRenderer();       
	    ITextFontResolver fontResolver = renderer.getFontResolver();       
	    //生产环境下wqy-zenhei.ttc这个字体在这个路径下/usr/share/fonts/wqy-zenhei/
	    fontResolver.addFont("/usr/share/fonts/wqy-zenhei/wqy-zenhei.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);       
//	    fontResolver.addFont("C:/Windows/fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);       
	        
	    renderer.setDocumentFromString(html);       
	    // 解决图片的相对路径问题       
	    // renderer.getSharedContext().setBaseURL("file:/F:/teste/html/");       
	    renderer.layout();       
	    renderer.createPDF(os);  
	    System.out.println("======转换成功!");  
	    os.close();       
	}  
    
	public static void main(String args[]) throws DocumentException, IOException {
		txt2pdf("D:/d.txt", "D:/c.pdf");
	}
}
