package com.ajc.producer.service;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2021/12/23 2:25 下午
 */
@Service
public class EsService {

    @Resource
    private RestHighLevelClient esClient ;

    public void addIndex(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_name","张三");
        jsonObject.put("message","hello~~~");
        IndexRequest indexRequest = new IndexRequest("test_index").source(jsonObject);
        try {
            IndexResponse index = esClient.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(index);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
