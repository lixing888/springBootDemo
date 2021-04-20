package com.springboot.demo.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.*;
import java.util.*;

public class PdfUtils {
    /**
     * @param fields
     * @param data
     * @throws IOException
     * @throws DocumentException
     */
    private static void fillData(AcroFields fields, Map<String, String> data) throws IOException, DocumentException {
        List<String> keys = new ArrayList<String>();
        Map<String, AcroFields.Item> formFields = fields.getFields();
        for (String key : data.keySet()) {
            if (formFields.containsKey(key)) {
                String value = data.get(key);
                //为字段赋值,注意字段名称是区分大小写的
                fields.setField(key, value);
                keys.add(key);
            }
        }
        Iterator<String> itemsKey = formFields.keySet().iterator();
        while (itemsKey.hasNext()) {
            String itemKey = itemsKey.next();
            if (!keys.contains(itemKey)) {
                fields.setField(itemKey, " ");
            }
        }
    }

    /**
     * @param templatePdfName 模板pdf名称
     * @param generatePdfPath 生成pdf路径
     * @param data            数据
     */
    public static String generatePDF(String templatePdfName, String generatePdfPath, Map<String, String> data) {
        OutputStream fos = null;
        ByteArrayOutputStream bos = null;
        try {
            byte[] bytes = generatePDF(templatePdfName, data);
            fos = new FileOutputStream(generatePdfPath);
            fos.write(bytes);
            fos.flush();
            return generatePdfPath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * @param templatePdfName 模板pdf名称
     * @param data            数据
     */
    public static byte[] generatePDF(String templatePdfName, Map<String, String> data) {
        ByteArrayOutputStream bos = null;
        try {
            InputStream istemplate = PdfUtils.class.getClassLoader().getResourceAsStream(templatePdfName);
            PdfReader reader = new PdfReader(istemplate);
            bos = new ByteArrayOutputStream();
            /* 将要生成的目标PDF文件名称 */
            PdfStamper ps = new PdfStamper(reader, bos);
            /* 使用中文字体 */
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
            fontList.add(bf);
            /* 取出报表模板中的所有字段 */
            AcroFields fields = ps.getAcroFields();
            fields.setSubstitutionFonts(fontList);
            fillData(fields, data);
            /* 必须要调用这个，否则文档不会生成的  如果为false那么生成的PDF文件还能编辑，一定要设为true*/
            ps.setFormFlattening(true);
            ps.close();
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("sex", "男");
        data.put("payType", "微信");
        data.put("receiptCode", "20010001");
        data.put("realName", "nnn");
        data.put("age", "30");
        data.put("examType", "统考");
        data.put("college", "科技大学");
        data.put("major", "计算机");

        data.put("email", "lynhchun@126.com");
        data.put("qqWx", "616690485");
        data.put("parentPhone", "13453457219");
        data.put("phone", "15392687219");

        data.put("targetMajor", "计算机科学与技术");
        data.put("examDate", "2019");
        data.put("tutorType", "暑假冲刺班");
        data.put("courseName", "英语、数学");

        data.put("createTime", "2019-04-18 16:26:30");
        data.put("payAmountWord", "二百元整");
        data.put("payAmount", "200");
        data.put("createBy", "nnn");
        data.put("chargePerson", "nnn");
        data.put("remark", "无");
        data.put("financeUser", "nnn");

        data.put("payStatus", "（全额）");


        generatePDF("e:ky2.pdf",
                "e:ky3.pdf", data);
    }
}

