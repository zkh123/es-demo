package com.example.esdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.esdemo.model.ESData;
import com.example.esdemo.utils.CreateData;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.recycler.Recycler;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by huanglijun on 2018/11/27
 */
@RestController
public class TestController {
    @Resource
    TransportClient client;//注入es操作对象


    @GetMapping(value = "/hs")
    public String hello(){
        return "ok";
    }

    /**
     *  往es服务中 写数据
     * @return
     */
    @GetMapping(value = "/index")
    public Object test(){
        // 文档
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";

        ESData esData = new ESData();
        esData.setUser(CreateData.getESData());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        esData.setData(formatter.format(new Date()));
        esData.setMessage(CreateData.getESData() + CreateData.getESData() + CreateData.getESData());


        IndexResponse response = client.prepareIndex("twitter","_doc")
                .setSource(JSONObject.toJSONString(esData), XContentType.JSON)
                .get();

        String _index = response.getIndex();
        String _type = response.getType();
        String _id = response.getId();
        long _version = response.getVersion();
        RestStatus status = response.status();

        return "_index : " + _index + " ,_type : " + _type + " ,_id : " + _id + " ,_version : " + _version + " ,status : " + status.toString();

    }

    @GetMapping(value = "get")
    public Object get(@RequestParam(value = "index") String index,
                      @RequestParam(value = "type")String type,
                      @RequestParam(value = "id")String id){
        GetResponse response = client.prepareGet(index,type,id).get();
        Map<String, Object> map = response.getSource();
        return map;
    }
}
