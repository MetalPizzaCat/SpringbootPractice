package com.goblin.internetproviderpractice;

import com.goblin.internetproviderpractice.model.UserRequest;
import com.goblin.internetproviderpractice.model.requests.UserRegisterRequest;
import org.junit.ClassRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class InternetproviderpracticeApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void createUserTest() {
        ResponseEntity<String> createUserRes = template
                .exchange(
                        "/user/register",
                        HttpMethod.POST,
                        new HttpEntity<>(new UserRegisterRequest("test_user_123", "123")),
                        String.class
                );
        Assertions.assertEquals("Created new user", createUserRes.getBody());
    }

    @Test
    @WithMockUser(username = "dave", roles = {"USER", "ADMIN"})
    void deleteUserTest() throws Exception {
        mvc.perform(delete("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\":\"test_user_123\"}")
        ).andExpect(status().isOk());
    }

    @Test
    void userNameAccessFailureTest() {
        ResponseEntity<String> res = template.withBasicAuth("a", "b").exchange("/user",
                HttpMethod.GET,
                null,
                String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatusCode().value());
    }

    @Test
    void serviceAccessFailureTest() {
        ResponseEntity<String> res = template.withBasicAuth("a", "b").exchange("/services",
                HttpMethod.GET,
                null,
                String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatusCode().value());
    }

    @Test
    @WithMockUser(username = "dave", roles = {"USER", "ADMIN"})
    void userGetNameTest() throws Exception {
        mvc.perform(get("/user")).andExpect(status().isOk());
    }


}
