package paymentServiceApp.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import paymentServiceApp.config.properties.RestTemplateClientConfigurationProperties;
import paymentServiceApp.config.properties.RestTemplateProperties;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(RestTemplateClientConfigurationProperties.class)
public class AppConfig {

    private final RestTemplateErrorHandler errorHandler;
    private final RestTemplateClientConfigurationProperties clientConfigurationProperties;

    @Bean
    public RestTemplate paymentClient() {
        RestTemplateProperties productClientProperties = clientConfigurationProperties.getProductClient();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(productClientProperties.getConnectTimeout());
        requestFactory.setReadTimeout(productClientProperties.getReadTimeout());

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(productClientProperties.getUrl());

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setUriTemplateHandler(uriBuilderFactory);
        restTemplate.setErrorHandler(errorHandler);
        return restTemplate;
    }
}
