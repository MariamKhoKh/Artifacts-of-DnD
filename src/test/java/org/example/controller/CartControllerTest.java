package org.example.controller;

import org.example.constants.Pages;
import org.example.constants.PathConstants;
import org.example.util.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-artifacts-before.sql", "/sql/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-user-after.sql", "/sql/create-artifacts-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(TestConstants.USER_EMAIL)
    @DisplayName("[200] GET /cart - Get Cart")
    public void getCart() throws Exception {
        mockMvc.perform(get(PathConstants.CART))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.CART))
                .andExpect(model().attribute("artifacts", hasSize(2)));
    }

    @Test
    @WithUserDetails(TestConstants.USER_EMAIL)
    @DisplayName("[300] POST /cart/add - Add artifact To Cart")
    public void addArtifactToCart() throws Exception {
        mockMvc.perform(post(PathConstants.CART + "/add")
                        .param("artifactId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(PathConstants.CART));
    }

    @Test
    @WithUserDetails(TestConstants.USER_EMAIL)
    @DisplayName("[300] POST /cart/remove - Remove artifact From Cart")
    public void removeArtifactFromCart() throws Exception {
        mockMvc.perform(post(PathConstants.CART + "/remove")
                        .param("artifactId", "44"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(PathConstants.CART));
    }
}
