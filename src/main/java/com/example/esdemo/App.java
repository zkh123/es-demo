package com.example.esdemo;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by huanglijun on 2018/11/27
 */
public class App {

    private final static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        String host = "172.20.10.145";
        Integer port = 9300;

        TransportClient client = null;
        try {

            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
            //如果有多个节点 后面继续 addTransportAddress
        } catch (UnknownHostException e) {
            logger.error("创建elasticsearch客户端失败");
            e.printStackTrace();
        }
        logger.info("创建elasticsearch客户端成功");

    }
}
