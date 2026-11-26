package qa.api;

import org.apache.http.conn.ConnectTimeoutException;
import org.junit.jupiter.api.Test;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AvitoRequestsTest {

    @Test
    void shouldRetryFirstGetAfterNetworkTimeout() {
        assertTrue(AvitoRequests.shouldRetry("GET", new ConnectTimeoutException(), 1));
        assertTrue(AvitoRequests.shouldRetry("GET", new SocketTimeoutException(), 1));
    }

    @Test
    void shouldStopAfterRetryAndNeverRepeatWrites() {
        assertFalse(AvitoRequests.shouldRetry("GET", new SocketTimeoutException(), 2));
        assertFalse(AvitoRequests.shouldRetry("GET", new UnknownHostException(), 1));
        assertFalse(AvitoRequests.shouldRetry("POST", new SocketTimeoutException(), 1));
        assertFalse(AvitoRequests.shouldRetry("DELETE", new ConnectTimeoutException(), 1));
    }
}
