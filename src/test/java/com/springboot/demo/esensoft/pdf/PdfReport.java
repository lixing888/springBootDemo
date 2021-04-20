package com.springboot.demo.esensoft.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

public class PdfReport {
	//private  String slswjgyz ="(受理税务机关印章)";
	/**
	 * 生成授权pdf
	 *
	 * @param PdfPath 生成后的pdf文件地址
	 * @param spdh    授权单号
	 * @param yhmc    银行名称
	 * @param nsrxx    纳税人信息
	 * @param swqz    税务签章名词
	 * @return

	 */
	public static void CreatePdf(String PdfPath, String spdh, String yhmc,String nsrxx ,String swqz) {
		try {
			Document document = new Document(PageSize.A4);// 建立一个Document对象
			// 2.建立一个书写器(Writer)与document对象关联
			File file = new File(PdfPath);
			file.createNewFile();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			// 3.打开文档
			document.open();
			// 4.向文档中添加内容
			new PdfReport().generatePDF2(document,spdh,yhmc,nsrxx,swqz,new Date());
			// 5.关闭文档
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generatePDF2(Document document,String spdh,String yhmc,String nsrxx ,String swqz,Date data)throws Exception {
		// TODO Auto-generated method stub
		// 段落1
		String str = "国家税务总局北京市税务局\r\n" + "纳税人银行信息查询函";
		Paragraph paragraph = getParagraph(1, str, titlefont);
		// 段落2
		Paragraph paragraph1 = getParagraph(1, "京税函(20)", headfont);
		// 带下划线的字体
		Chunk chunk1 = new Chunk(spdh);
		chunk1.setUnderline(0.2f, -2f);
		paragraph1.add(chunk1);
		paragraph1.add("号\n");

		Paragraph paragraph0 = new Paragraph("", headfont);
		// 下划线二
		Chunk dateUnderline = new Chunk(yhmc);
		dateUnderline.setUnderline(0.2f, -2f);
		paragraph0.add(dateUnderline);
		paragraph0.add(":");
		// 段落3
		String str3 = new String("    根据《中华人民共和国税收征收管理法》第五十四条第（六）项规定，经国家税务总局北京市税务局主管局长批准，我局拟查询你行关于");

		Paragraph paragraph3 = getParagraph(0, str3, headfont);
		//Chunk chunk3 = new Chunk("申请人、电话、申请事由、查询对象、查询银行、查询内容和查询起止时间");
		Chunk chunk3 = new Chunk(nsrxx);
		chunk3.setUnderline(0.2f, -2f);
		paragraph3.add(chunk3);
		paragraph3.add("等相关信息。");
		Paragraph paragraph31 = getParagraph(1, "请予支持协助，并在收到此函1日内反馈查询结果。", headfont);
		// 段落4
		//Paragraph paragraph4 = new Paragraph(slswjgyz, headfont);
		Paragraph paragraph4 = new Paragraph(swqz, headfont);
		paragraph4.setAlignment(2); // 设置文字居中 0靠左 1，居中 2，靠右
		paragraph4.setIndentationLeft(12); // 设置左缩进
		paragraph4.setIndentationRight(12); // 设置右缩进
		paragraph4.setFirstLineIndent(24); // 设置首行缩进
		paragraph4.setLeading(20f); // 行间距
		paragraph4.setSpacingBefore(5f); // 设置段落上空白
		paragraph4.setSpacingAfter(10f); // 设置段落下空白

		// 段落5
		Paragraph paragraph5 = new Paragraph(" ", headfont);
		paragraph5.setAlignment(2); // 设置文字居中 0靠左 1，居中 2，靠右
		paragraph5.setIndentationLeft(12); // 设置左缩进
		paragraph5.setIndentationRight(12); // 设置右缩进
		paragraph5.setFirstLineIndent(24); // 设置首行缩进
		paragraph5.setLeading(20f); // 行间距
		paragraph5.setSpacingBefore(5f); // 设置段落上空白
		paragraph5.setSpacingAfter(10f); // 设置段落下空白
		Calendar cal=Calendar.getInstance();
		paragraph5.add(cal.get(Calendar.YEAR)+"");
		paragraph5.add(" 年 ");
		paragraph5.add(cal.get(Calendar.MONTH)+1+"");
		paragraph5.add(" 月 ");
		paragraph5.add(cal.get(Calendar.DAY_OF_MONTH)+"");
		paragraph5.add(" 日 ");

		document.add(paragraph);
		document.add(paragraph1);
		document.add(paragraph0);
		document.add(paragraph3);
		document.add(paragraph31);
		document.add(paragraph4);
		document.add(paragraph5);
	}

	// main测试
	public static void main(String[] args) throws Exception {
		try {
			// 1.新建document对象
			Document document = new Document(PageSize.A4);// 建立一个Document对象

			// 2.建立一个书写器(Writer)与document对象关联
			File file = new File("F:\\pdf\\PDFDemo.pdf");
			file.createNewFile();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			// writer.setPageEvent(new Watermark("HELLO ITEXTPDF"));// 水印
			// writer.setPageEvent(new MyHeaderFooter());// 页眉/页脚

			// 3.打开文档
			document.open();
			/*
			 * document.addTitle("Title@PDF-Java");// 标题
			 * document.addAuthor("Author@umiz");// 作者
			 * document.addSubject("Subject@iText pdf sample");// 主题
			 * document.addKeywords("Keywords@iTextpdf");// 关键字
			 * document.addCreator("Creator@umiz`s");// 创建者
			 */
			// 4.向文档中添加内容
			new PdfReport().generatePDF(document);

			// 5.关闭文档
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 定义全局的字体静态变量
	private static Font titlefont;
	private static Font headfont;
	private static Font keyfont;
	private static Font textfont;
	// 最大宽度
	private static int maxWidth = 520;
	// 静态代码块
	static {
		try {
			// 不同字体（这里定义为同一种字体：包含不同字号、不同style）
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			titlefont = new Font(bfChinese, 16, Font.BOLD);
			headfont = new Font(bfChinese, 14, Font.BOLD);
			keyfont = new Font(bfChinese, 10, Font.BOLD);
			textfont = new Font(bfChinese, 10, Font.NORMAL);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 生成PDF文件
	public void generatePDF(Document document) throws Exception {

		// 段落1
		String str = "国家税务总局北京市税务局\r\n" + "纳税人银行信息查询函";
		Paragraph paragraph = getParagraph(1, str, titlefont);
		// 段落2
		Paragraph paragraph1 = getParagraph(1, "京税函(20)", headfont);
		// 带下划线的字体
		Chunk chunk1 = new Chunk("审批单编号");
		chunk1.setUnderline(0.2f, -2f);
		paragraph1.add(chunk1);
		paragraph1.add("号\n");

		Paragraph paragraph0 = new Paragraph("", headfont);
		// 下划线二
		Chunk dateUnderline = new Chunk("银行名称");
		dateUnderline.setUnderline(0.2f, -2f);
		paragraph0.add(dateUnderline);
		paragraph0.add(":");
		// 段落3
		String str3 = new String("    根据《中华人民共和国税收征收管理法》第五十四条第（六）项规定，经国家税务总局北京市税务局主管局长批准，我局拟查询你行关于");

		Paragraph paragraph3 = getParagraph(0, str3, headfont);
		Chunk chunk3 = new Chunk("申请人、电话、申请事由、查询对象、查询银行、查询内容和查询起止时间");
		chunk3.setUnderline(0.2f, -2f);
		paragraph3.add(chunk3);
		paragraph3.add("等相关信息。");
		Paragraph paragraph31 = getParagraph(1, "请予支持协助，并在收到此函1日内反馈查询结果。", headfont);
		// 段落4
		Paragraph paragraph4 = new Paragraph("税务机关（电子签章）", headfont);
		paragraph4.setAlignment(2); // 设置文字居中 0靠左 1，居中 2，靠右
		paragraph4.setIndentationLeft(12); // 设置左缩进
		paragraph4.setIndentationRight(12); // 设置右缩进
		paragraph4.setFirstLineIndent(24); // 设置首行缩进
		paragraph4.setLeading(20f); // 行间距
		paragraph4.setSpacingBefore(5f); // 设置段落上空白
		paragraph4.setSpacingAfter(10f); // 设置段落下空白

		// 段落5
		Paragraph paragraph5 = new Paragraph(" ", headfont);
		paragraph5.setAlignment(2); // 设置文字居中 0靠左 1，居中 2，靠右
		paragraph5.setIndentationLeft(12); // 设置左缩进
		paragraph5.setIndentationRight(12); // 设置右缩进
		paragraph5.setFirstLineIndent(24); // 设置首行缩进
		paragraph5.setLeading(20f); // 行间距
		paragraph5.setSpacingBefore(5f); // 设置段落上空白
		paragraph5.setSpacingAfter(10f); // 设置段落下空白
		paragraph5.add("2020");
		paragraph5.add(" 年 ");
		paragraph5.add("06");
		paragraph5.add(" 月 ");
		paragraph5.add("02");
		paragraph5.add(" 日 ");

		document.add(paragraph);
		document.add(paragraph1);
		document.add(paragraph0);
		document.add(paragraph3);
		document.add(paragraph31);
		document.add(paragraph4);
		document.add(paragraph5);
	}

	private Paragraph getParagraph(int i, String string, Font titlefont2) {
		Paragraph paragraph = new Paragraph(string, titlefont2);
		paragraph.setAlignment(i); // 设置文字居中 0靠左 1，居中 2，靠右
		paragraph.setIndentationLeft(12); // 设置左缩进
		paragraph.setIndentationRight(12); // 设置右缩进
		paragraph.setFirstLineIndent(24); // 设置首行缩进
		paragraph.setLeading(20f); // 行间距
		paragraph.setSpacingBefore(5f); // 设置段落上空白
		paragraph.setSpacingAfter(10f); // 设置段落下空白
		// TODO Auto-generated method stub
		return paragraph;
	}

	/** ------------------------创建表格单元格的方法start---------------------------- */
	/**
	 * 创建单元格(指定字体)
	 *
	 * @param value
	 * @param font
	 * @return
	 */
	public PdfPCell createCell(String value, Font font) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}

	/**
	 * 创建单元格（指定字体、水平..）
	 *
	 * @param value
	 * @param font
	 * @param align
	 * @return
	 */
	public PdfPCell createCell(String value, Font font, int align) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}

	/**
	 * 创建单元格（指定字体、水平居..、单元格跨x列合并）
	 *
	 * @param value
	 * @param font
	 * @param align
	 * @param colspan
	 * @return
	 */
	public PdfPCell createCell(String value, Font font, int align, int colspan) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setPhrase(new Phrase(value, font));
		return cell;
	}

	/**
	 * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
	 *
	 * @param value
	 * @param font
	 * @param align
	 * @param colspan
	 * @param boderFlag
	 * @return
	 */
	public PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		cell.setPhrase(new Phrase(value, font));
		cell.setPadding(3.0f);
		if (!boderFlag) {
			cell.setBorder(0);
			cell.setPaddingTop(15.0f);
			cell.setPaddingBottom(8.0f);
		} else if (boderFlag) {
			cell.setBorder(0);
			cell.setPaddingTop(0.0f);
			cell.setPaddingBottom(15.0f);
		}
		return cell;
	}

	/**
	 * 创建单元格（指定字体、水平..、边框宽度：0表示无边框、内边距）
	 *
	 * @param value
	 * @param font
	 * @param align
	 * @param borderWidth
	 * @param paddingSize
	 * @param flag
	 * @return
	 */
	public PdfPCell createCell(String value, Font font, int align, float[] borderWidth, float[] paddingSize,
							   boolean flag) {
		PdfPCell cell = new PdfPCell();
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(align);
		cell.setPhrase(new Phrase(value, font));
		cell.setBorderWidthLeft(borderWidth[0]);
		cell.setBorderWidthRight(borderWidth[1]);
		cell.setBorderWidthTop(borderWidth[2]);
		cell.setBorderWidthBottom(borderWidth[3]);
		cell.setPaddingTop(paddingSize[0]);
		cell.setPaddingBottom(paddingSize[1]);
		if (flag) {
			cell.setColspan(2);
		}
		return cell;
	}

	/** ------------------------创建表格单元格的方法end---------------------------- */

	/** --------------------------创建表格的方法start------------------- --------- */
	/**
	 * 创建默认列宽，指定列数、水平(居中、右、左)的表格
	 *
	 * @param colNumber
	 * @param align
	 * @return
	 */
	public PdfPTable createTable(int colNumber, int align) {
		PdfPTable table = new PdfPTable(colNumber);
		try {
			table.setTotalWidth(maxWidth);
			table.setLockedWidth(true);
			table.setHorizontalAlignment(align);
			table.getDefaultCell().setBorder(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}

	/**
	 * 创建指定列宽、列数的表格
	 *
	 * @param widths
	 * @return
	 */
	public PdfPTable createTable(float[] widths) {
		PdfPTable table = new PdfPTable(widths);
		try {
			table.setTotalWidth(maxWidth);
			table.setLockedWidth(true);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setBorder(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}

	/**
	 * 创建空白的表格
	 *
	 * @return
	 */
	public PdfPTable createBlankTable() {
		PdfPTable table = new PdfPTable(1);
		table.getDefaultCell().setBorder(0);
		table.addCell(createCell("", keyfont));
		table.setSpacingAfter(20.0f);
		table.setSpacingBefore(20.0f);
		return table;
	}
	/** --------------------------创建表格的方法end------------------- --------- */

}