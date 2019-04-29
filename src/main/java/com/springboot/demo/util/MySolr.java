package com.springboot.demo.util;

import org.apache.solr.client.solrj.*;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * solr 5.3.0
 * Created by daxiong on 2015/10/23.
 */
public class MySolr {

    //solr url
    public static final String URL = "http://127.0.0.1:8080/solr";
    //solr应用
    public static final String SERVER = "";
    //待索引、查询字段
    public static String[] docs = {"Solr是一个独立的企业级搜索应用服务器",
            "它对外提供类似于Web-service的API接口",
            "用户可以通过http请求",
            "向搜索引擎服务器提交一定格式的XML文件生成索引",
            "也可以通过Http Get操作提出查找请求",
            "并得到XML格式的返回结果"};

    public static SolrClient getSolrClient() {
        return new HttpSolrClient(URL + "/" + SERVER);
    }

    /**
     * 新建索引
     */
    public static void createIndex() {
        SolrClient client = getSolrClient();
        int i = 0;
        List<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();
        for (String str : docs) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", i++);
            doc.addField("content_test", str);
            docList.add(doc);
        }
        try {
            client.add(docList);
            client.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ;

    /**
     * 搜索
     */
    public static void search() {
        SolrClient client = getSolrClient();
        SolrQuery query = new SolrQuery();
        query.setQuery("content_test:搜索");
        QueryResponse response = null;
        try {
            response = client.query(query);
            System.out.println(response.toString());
            System.out.println();
            SolrDocumentList docs = response.getResults();
            System.out.println("文档个数：" + docs.getNumFound());
            System.out.println("查询时间：" + response.getQTime());
            for (SolrDocument doc : docs) {
                System.out.println("id: " + doc.getFieldValue("id") + "      content: " + doc.getFieldValue("content_test"));
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createIndex();
        search();
    }
}
