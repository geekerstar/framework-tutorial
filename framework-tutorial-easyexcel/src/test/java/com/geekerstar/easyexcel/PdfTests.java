package com.geekerstar.easyexcel;


import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;


/**
 * @author geekerstar
 * date: 2019/9/19 21:37
 * description: PDF生成测试
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PdfTests {

	private static final String PATH = "/Users/geekerstar/Desktop/testPDF.pdf";


	@Test
	public void writePdfSimple() throws Exception {
	    Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(PATH));
		document.open();
		document.add(new Paragraph("hello world"));
		document.close();
		writer.close();
	}


}
