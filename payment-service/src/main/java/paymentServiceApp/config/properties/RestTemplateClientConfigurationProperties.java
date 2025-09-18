package paymentServiceApp.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties("integrations.clients")
public class RestTemplateClientConfigurationProperties {

    private final RestTemplateProperties productClient;
}
