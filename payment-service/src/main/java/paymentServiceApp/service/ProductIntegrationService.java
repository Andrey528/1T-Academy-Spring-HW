package paymentServiceApp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import paymentServiceApp.dto.GetProductsRequest;
import paymentServiceApp.dto.GetProductsResponse;
import paymentServiceApp.dto.UpdateProductsRequest;

@AllArgsConstructor
@Service
public class ProductIntegrationService {

    private final RestTemplate restTemplate;

    public GetProductsResponse getProductsInfo(GetProductsRequest request) {
        return restTemplate.postForObject(
                "/api/v1/products/byIds",
                request,
                GetProductsResponse.class
        );
    }

    public void updateProductsBalance(UpdateProductsRequest request) {
        restTemplate.postForObject(
                "/api/v1/products/updateProductsBalance",
                request,
                Void.class
        );
    }
}
