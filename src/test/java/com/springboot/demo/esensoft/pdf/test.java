package com.springboot.demo.esensoft.pdf;
 /*       <dependency>
            <groupId>com.itextpdf</groupId>
            <artifactId>itextpdf</artifactId>
            <version>5.5.13</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.itextpdf/itext-asian -->
        <dependency>
            <groupId>com.itextpdf</groupId>
            <artifactId>itext-asian</artifactId>
            <version>5.2.0</version>
        </dependency>*/
public class test {
	public static void main(String[] args) {
		String nsrxx="北京市测试1科技有限责任公司，社会统一信用代码81000000000MA1；北京市测试2科技有限责任公司，社会统一信用代码81000000000MA2；北京市测试3科技有限责任公司，社会统一信用代码81000000000MA3";
		String swqz="税务机关（电子签章）";

		PdfReport.CreatePdf("D:\\app3web\\qz.pdf","88","北京中关村银行",nsrxx,swqz);
		System.out.println(111);
	}
}
