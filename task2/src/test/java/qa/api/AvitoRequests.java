package qa.api;

import io.qameta.allure.Allure;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.SocketTimeoutException;

import static io.restassured.RestAssured.given;

final class AvitoRequests {

    private static final int CONNECT_TIMEOUT_MS = timeoutMillis("QA_API_CONNECT_TIMEOUT_MS", 10_000);
    private static final int READ_TIMEOUT_MS = timeoutMillis("QA_API_READ_TIMEOUT_MS", 30_000);
    private static final String RETRIED_GET = "qa.api.retriedGet";

    private static final RequestSpecification REQUEST_SPEC = new RequestSpecBuilder()
            .setBaseUri("https://qa-internship.avito.com")
            .setContentType(ContentType.JSON)
            .setConfig(RestAssuredConfig.config().httpClient(httpClientConfig()))
            .addFilter((request, response, chain) -> {
                String step = request.getMethod() + " " + URI.create(request.getURI()).getPath();
                return Allure.step(step, context -> {
                    try {
                        Response result = chain.next(request, response);
                        context.parameter("HTTP status", result.statusCode());
                        return result;
                    } catch (Exception failure) {
                        context.parameter("Request error", failure.getClass().getSimpleName());
                        throw failure;
                    }
                });
            })
            .build();

    private AvitoRequests() {
    }

    private static HttpClientConfig httpClientConfig() {
        HttpClientConfig config = HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", CONNECT_TIMEOUT_MS)
                .setParam("http.socket.timeout", READ_TIMEOUT_MS);
        if (!Boolean.parseBoolean(System.getenv("QA_API_RETRY_GET"))) {
            return config;
        }
        return config.httpClientFactory(() -> {
            DefaultHttpClient client = new DefaultHttpClient();
            client.setHttpRequestRetryHandler((failure, attempt, context) -> {
                HttpRequest request = HttpClientContext.adapt(context).getRequest();
                String method = request == null ? "" : request.getRequestLine().getMethod();
                if (Boolean.TRUE.equals(context.getAttribute(RETRIED_GET)) || !shouldRetry(method, failure, attempt)) {
                    return false;
                }
                context.setAttribute(RETRIED_GET, true);
                Allure.step("Retry GET after " + failure.getClass().getSimpleName());
                return true;
            });
            return client;
        });
    }

    static boolean shouldRetry(String method, IOException failure, int attempt) {
        return attempt == 1 && "GET".equals(method)
                && (failure instanceof ConnectTimeoutException || failure instanceof SocketTimeoutException);
    }

    private static int timeoutMillis(String variable, int defaultValue) {
        String value = System.getenv(variable);
        if (value == null) {
            return defaultValue;
        }
        int timeout = Integer.parseInt(value);
        if (timeout <= 0) {
            throw new IllegalArgumentException(variable + " must be a positive timeout in milliseconds");
        }
        return timeout;
    }

    static RequestSpecification request() {
        return given(REQUEST_SPEC);
    }

    static RequestSpecification requestAs(SellerSession seller) {
        return request().header("Authorization", "Bearer " + seller.token());
    }
}
