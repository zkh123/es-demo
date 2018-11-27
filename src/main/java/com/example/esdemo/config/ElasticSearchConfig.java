package com.example.esdemo.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by huanglijun on 2018/11/27
 */
@Configuration
public class ElasticSearchConfig {
    private final static Logger logger = LoggerFactory.getLogger(ElasticSearchConfig.class);


    @Value("${spring.elasticsearch.host}")
    private String host;

    @Value("${spring.elasticsearch.port}")
    private String port;


    @Bean
    public TransportClient client(){
        TransportClient client = null;
        //集群的名称
        Settings settings = Settings.builder().put("cluster.name","huanglijun-es-test").build();
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(host), Integer.valueOf(port)));
        } catch (UnknownHostException e) {
            logger.error("创建elasticsearch客户端失败");
            e.printStackTrace();
        }
        logger.info("创建elasticsearch客户端成功");
        return client;
    }
}
