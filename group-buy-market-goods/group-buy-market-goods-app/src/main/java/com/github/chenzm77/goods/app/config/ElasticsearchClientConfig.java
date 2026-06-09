package com.github.chenzm77.goods.app.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "goods.elasticsearch")
public class ElasticsearchClientConfig {

    private String host = "localhost";
    private Integer port = 9200;
    private String scheme = "http";

    @Bean
    public RestClient goodsRestClient() {
        return RestClient.builder(new HttpHost(host, port, scheme)).build();
    }

    @Bean
    public ElasticsearchTransport goodsElasticsearchTransport(RestClient goodsRestClient) {
        return new RestClientTransport(goodsRestClient, new JacksonJsonpMapper());
    }

    @Bean
    public ElasticsearchClient goodsElasticsearchClient(ElasticsearchTransport goodsElasticsearchTransport) {
        return new ElasticsearchClient(goodsElasticsearchTransport);
    }
}
