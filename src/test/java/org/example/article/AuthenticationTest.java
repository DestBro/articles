package org.example.article;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("user", "password")
                .getForEntity("/api/article", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void givenUserRequestsServiceWithAdminRole_shouldFailWith403() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("user", "password")
                .getForEntity("/api/article/countLast7Days", String.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    public void givenAdminRequestsServiceWithAdminRole_shouldSucceedWith200() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("admin", "password")
                .getForEntity("/api/article/countLast7Days", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
