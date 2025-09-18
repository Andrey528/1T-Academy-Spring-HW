package productServiceApp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import productServiceApp.dto.exception.PaymentExecutorErrorResponseDto;
import productServiceApp.exception.ExternalServiceException;

import java.io.IOException;
import java.net.URI;

@AllArgsConstructor
@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        PaymentExecutorErrorResponseDto externalEx = new PaymentExecutorErrorResponseDto(
                url.getPath(),
                method.name(),
                objectMapper.readValue(response.getBody(), String.class)
        );

        throw new ExternalServiceException("Произошла ошибка при обращении во внешний сервис",
                externalEx);
    }
}
