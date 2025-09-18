package productServiceApp.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import productServiceApp.config.properties.RestTemplateClientConfigurationProperties;
import productServiceApp.config.properties.RestTemplateProperties;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(RestTemplateClientConfigurationProperties.class)
public class AppConfig {

    private final RestTemplateErrorHandler errorHandler;
    private final RestTemplateClientConfigurationProperties clientConfigurationProperties;

    @Bean
    public RestTemplate paymentClient() {
        RestTemplateProperties paymentClientProperties = clientConfigurationProperties.getPaymentClient();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(paymentClientProperties.getConnectTimeout());
        requestFactory.setReadTimeout(paymentClientProperties.getReadTimeout());

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(paymentClientProperties.getUrl());

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setUriTemplateHandler(uriBuilderFactory);
        restTemplate.setErrorHandler(errorHandler);
        return restTemplate;
    }
}
