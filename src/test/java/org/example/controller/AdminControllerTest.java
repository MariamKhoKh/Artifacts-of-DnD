package org.example.controller;

import org.example.constants.ErrorMessage;
import org.example.constants.Pages;
import org.example.constants.PathConstants;
import org.example.constants.SuccessMessage;
import org.example.util.TestConstants;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WithUserDetails(TestConstants.ADMIN_EMAIL)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-artifacts-before.sql", "/sql/create-user-before.sql", "/sql/create-orders-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-orders-after.sql", "/sql/create-user-after.sql", "/sql/create-artifacts-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[200] GET /admin/artifacts - Get artifacts")
    public void getartifacts() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/artifacts"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ADMIN_ARTIFACTS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(12))));
    }

    @Test
    @DisplayName("[200] GET /admin/artifacts/search - Search artifacts By artifacter")
    public void searchartifacts_Byartifacter() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/artifacts/search")
                        .param("searchType", "artifacter")
                        .param("text", "Creed"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ADMIN_ARTIFACTS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(7))));
    }

    @Test
    @DisplayName("[200] GET /admin/artifacts/search - Search artifacts By country")
    public void searchartifacts_ByCountry() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/artifacts/search")
                        .param("searchType", "country")
                        .param("text", "Spain"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ADMIN_ARTIFACTS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(2))));
    }

    @Test
    @DisplayName("[200] GET /admin/artifacts/search - Search artifacts By artifactTitle")
    public void searchartifacts_artifactTitle() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/artifacts/search")
                        .param("searchType", "artifactTitle")
                        .param("text", "Aventus"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ADMIN_ARTIFACTS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(2))));
    }

    @Test
    @DisplayName("[200] GET /admin/artifacts/search - Get Users")
    public void getUsers() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/users"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ADMIN_ALL_USERS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(3))));
    }

    @Test
    @DisplayName("[200] GET /admin/users/search - Search Users By email")
    public void searchUsers_ByEmail() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/users/search")
                        .param("searchType", "email")
                        .param("text", TestConstants.USER_EMAIL))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ADMIN_ALL_USERS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(1))));
    }

    @Test
    @DisplayName("[200] GET /admin/users/search - Search Users By First Name")
    public void searchUsers_ByFirstName() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/users/search")
                        .param("searchType", "firstName")
                        .param("text", TestConstants.USER_FIRST_NAME))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ADMIN_ALL_USERS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(2))));
    }

    @Test
    @DisplayName("[200] GET /admin/users/search - Search Users By Last Name")
    public void searchUsers_ByLastName() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/users/search")
                        .param("searchType", "lastName")
                        .param("text", TestConstants.USER_LAST_NAME))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ADMIN_ALL_USERS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(2))));
    }

    @Test
    @DisplayName("[200] GET /admin/order/111 - Get Order")
    public void getOrder() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/order/{orderId}", 111))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ORDER))
                .andExpect(model().attribute("order", hasProperty("id", Matchers.is(TestConstants.ORDER_ID))))
                .andExpect(model().attribute("order", hasProperty("totalPrice", Matchers.is(TestConstants.ORDER_TOTAL_PRICE))))
                .andExpect(model().attribute("order", hasProperty("firstName", Matchers.is(TestConstants.ORDER_FIRST_NAME))))
                .andExpect(model().attribute("order", hasProperty("lastName", Matchers.is(TestConstants.ORDER_LAST_NAME))))
                .andExpect(model().attribute("order", hasProperty("city", Matchers.is(TestConstants.ORDER_CITY))))
                .andExpect(model().attribute("order", hasProperty("address", Matchers.is(TestConstants.ORDER_ADDRESS))))
                .andExpect(model().attribute("order", hasProperty("email", Matchers.is(TestConstants.ORDER_EMAIL))))
                .andExpect(model().attribute("order", hasProperty("phoneNumber", Matchers.is(TestConstants.ORDER_PHONE_NUMBER))))
                .andExpect(model().attribute("order", hasProperty("postIndex", Matchers.is(TestConstants.ORDER_POST_INDEX))))
                .andExpect(model().attribute("order", hasProperty("artifacts", hasSize(2))));
    }

    @Test
    @DisplayName("[200] GET /admin/orders - Get Orders")
    public void getOrders() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ORDERS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(1))));
    }

    @Test
    @DisplayName("[200] GET /admin/orders/search - Search Orders By Email")
    public void searchOrders_ByEmail() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/orders/search")
                        .param("searchType", "email")
                        .param("text", TestConstants.USER_EMAIL))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ORDERS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(1))));
    }

    @Test
    @DisplayName("[200] GET /admin/orders/search - Search Orders bt First Name")
    public void searchOrders_ByFirstName() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/orders/search")
                        .param("searchType", "firstName")
                        .param("text", TestConstants.USER_FIRST_NAME))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ORDERS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(1))));
    }

    @Test
    @DisplayName("[200] GET /admin/orders/search - Search Orders By Last Name")
    public void searchOrders_ByLastName() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/orders/search")
                        .param("searchType", "lastName")
                        .param("text", TestConstants.USER_LAST_NAME))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ORDERS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(1))));
    }

    @Test
    @DisplayName("[200] GET /admin/artifact/1 - Get artifact")
    public void getArtifact() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PathConstants.ADMIN + "/artifact/{artifactId}", TestConstants.ARTIFACT_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ADMIN_EDIT_ARTIFACT))
                .andExpect(model().attribute("artifact", hasProperty("id", Matchers.is(TestConstants.ARTIFACT_ID))))
                .andExpect(model().attribute("artifact", hasProperty("artifactTitle", Matchers.is(TestConstants.ARTIFACT_TITLE))))
                .andExpect(model().attribute("artifact", hasProperty("artifacter", Matchers.is(TestConstants.ARTIFACTER))))
                .andExpect(model().attribute("artifact", hasProperty("year", Matchers.is(TestConstants.YEAR))))
                .andExpect(model().attribute("artifact", hasProperty("country", Matchers.is(TestConstants.COUNTRY))))
                .andExpect(model().attribute("artifact", hasProperty("artifactGender", Matchers.is(TestConstants.ARTIFACT_GENDER))))
                .andExpect(model().attribute("artifact", hasProperty("productTopNotes", Matchers.is(TestConstants.PRODUCT_TOP_NOTES))))
                .andExpect(model().attribute("artifact", hasProperty("productMiddleNotes", Matchers.is(TestConstants.PRODUCT_MIDDLE_NOTES))))
                .andExpect(model().attribute("artifact", hasProperty("productBaseNotes", Matchers.is(TestConstants.PRODUCT_BASE_NOTES))))
                .andExpect(model().attribute("artifact", hasProperty("filename", Matchers.is(TestConstants.FILENAME))))
                .andExpect(model().attribute("artifact", hasProperty("price", Matchers.is(TestConstants.PRICE))))
                .andExpect(model().attribute("artifact", hasProperty("volume", Matchers.is(TestConstants.VOLUME))))
                .andExpect(model().attribute("artifact", hasProperty("type", Matchers.is(TestConstants.TYPE))));
    }

    @Test
    @DisplayName("[404] GET /admin/artifact/111 - Get artifact Not Found")
    public void getartifact_NotFound() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/artifact/{artifactId}", 111))
                .andExpect(status().isNotFound())
                .andExpect(status().reason(ErrorMessage.ARTIFACT_NOT_FOUND));
    }

    @Test
    @DisplayName("[300] POST /admin/edit/artifact - Edit artifact")
    public void editartifact() throws Exception {
        mockMvc.perform(multipart(PathConstants.ADMIN + "/edit/artifact")
                        .file(mockFile())
                        .param("id", String.valueOf(TestConstants.ARTIFACT_ID))
                        .param("artifactTitle", TestConstants.ARTIFACT_TITLE)
                        .param("artifacter", TestConstants.ARTIFACTER)
                        .param("year", String.valueOf(TestConstants.YEAR))
                        .param("country", TestConstants.COUNTRY)
                        .param("artifactGender", TestConstants.ARTIFACT_GENDER)
                        .param("productTopNotes", TestConstants.PRODUCT_TOP_NOTES)
                        .param("productMiddleNotes", TestConstants.PRODUCT_MIDDLE_NOTES)
                        .param("productBaseNotes", TestConstants.PRODUCT_BASE_NOTES)
                        .param("price", String.valueOf(TestConstants.PRICE))
                        .param("volume", TestConstants.VOLUME)
                        .param("type", TestConstants.TYPE))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/artifacts"))
                .andExpect(flash().attribute("messageType", "alert-success"))
                .andExpect(flash().attribute("message", SuccessMessage.ARTIFACT_EDITED));
    }

    @Test
    @DisplayName("[200] POST /admin/edit/artifact - Edit artifact Return Input Errors")
    public void editartifact_ReturnInputErrors() throws Exception {
        mockMvc.perform(multipart(PathConstants.ADMIN + "/edit/artifact")
                        .file(mockFile())
                        .param("id", String.valueOf(TestConstants.ARTIFACT_ID))
                        .param("artifactTitle", "")
                        .param("artifacter", "")
                        .param("year", "0")
                        .param("country", "")
                        .param("artifactGender", "")
                        .param("productTopNotes", "")
                        .param("productMiddleNotes", "")
                        .param("productBaseNotes", "")
                        .param("price", "0")
                        .param("volume", "")
                        .param("type", ""))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ADMIN_EDIT_ARTIFACT))
                .andExpect(model().attribute("artifactTitleError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("artifacterError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("yearError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("countryError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("artifactGenderError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("productTopNotesError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("productMiddleNotesError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("productBaseNotesError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("priceError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("volumeError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("typeError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)));
    }

    @Test
    @DisplayName("[200] GET /admin/add/artifact - Get Add artifact Page")
    public void getAddartifactPage() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/add/artifact"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ADMIN_ADD_ARTIFACT));
    }

    @Test
    @DisplayName("[300] POST /admin/add/artifact - Add artifact")
    public void addartifact() throws Exception {
        mockMvc.perform(multipart(PathConstants.ADMIN + "/add/artifact")
                        .file(mockFile())
                        .param("artifactTitle", TestConstants.ARTIFACT_TITLE)
                        .param("artifacter", TestConstants.ARTIFACTER)
                        .param("year", String.valueOf(TestConstants.YEAR))
                        .param("country", TestConstants.COUNTRY)
                        .param("artifactGender", TestConstants.ARTIFACT_GENDER)
                        .param("productTopNotes", TestConstants.PRODUCT_TOP_NOTES)
                        .param("productMiddleNotes", TestConstants.PRODUCT_MIDDLE_NOTES)
                        .param("productBaseNotes", TestConstants.PRODUCT_BASE_NOTES)
                        .param("price", String.valueOf(TestConstants.PRICE))
                        .param("volume", TestConstants.VOLUME)
                        .param("type", TestConstants.TYPE))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/artifacts"))
                .andExpect(flash().attribute("messageType", "alert-success"))
                .andExpect(flash().attribute("message", SuccessMessage.ARTIFACT_ADDED));
    }

    @Test
    @DisplayName("[200] POST /admin/add/artifact - Add artifact Return Input Errors")
    public void addartifact_ReturnInputErrors() throws Exception {
        mockMvc.perform(multipart(PathConstants.ADMIN + "/add/artifact")
                        .file(mockFile())
                        .param("artifactTitle", "")
                        .param("artifacter", "")
                        .param("year", "0")
                        .param("country", "")
                        .param("artifactGender", "")
                        .param("productTopNotes", "")
                        .param("productMiddleNotes", "")
                        .param("productBaseNotes", "")
                        .param("price", "0")
                        .param("volume", "")
                        .param("type", ""))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ADMIN_ADD_ARTIFACT))
                .andExpect(model().attribute("artifactTitleError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("artifacterError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("yearError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("countryError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("artifactGenderError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("productTopNotesError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("productMiddleNotesError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("productBaseNotesError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("priceError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("volumeError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)))
                .andExpect(model().attribute("typeError", is(ErrorMessage.FILL_IN_THE_INPUT_FIELD)));
    }

    @Test
    @DisplayName("[200] GET /admin/user/122 - Get User By Id")
    public void getUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PathConstants.ADMIN + "/user/{artifactId}", TestConstants.USER_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ADMIN_USER_DETAIL))
                .andExpect(model().attribute("user", hasProperty("id", Matchers.is(TestConstants.USER_ID))))
                .andExpect(model().attribute("user", hasProperty("email", Matchers.is(TestConstants.USER_EMAIL))))
                .andExpect(model().attribute("user", hasProperty("firstName", Matchers.is(TestConstants.USER_FIRST_NAME))))
                .andExpect(model().attribute("user", hasProperty("lastName", Matchers.is(TestConstants.USER_LAST_NAME))))
                .andExpect(model().attribute("user", hasProperty("city", Matchers.is(TestConstants.USER_CITY))))
                .andExpect(model().attribute("user", hasProperty("address", Matchers.is(TestConstants.USER_ADDRESS))))
                .andExpect(model().attribute("user", hasProperty("phoneNumber", Matchers.is(TestConstants.USER_PHONE_NUMBER))))
                .andExpect(model().attribute("user", hasProperty("postIndex", Matchers.is(TestConstants.USER_POST_INDEX))))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(1))));
    }

    @Test
    @DisplayName("[404] GET /admin/user/123 - Get User By Id Not Found")
    public void getUserById_NotFound() throws Exception {
        mockMvc.perform(get(PathConstants.ADMIN + "/user/{artifactId}", 123))
                .andExpect(status().isNotFound())
                .andExpect(status().reason(ErrorMessage.USER_NOT_FOUND));
    }

    private MockMultipartFile mockFile() throws IOException {
        FileInputStream inputFile = new FileInputStream(new File(TestConstants.FILE_PATH));
        return new MockMultipartFile("file", TestConstants.FILE_NAME, MediaType.MULTIPART_FORM_DATA_VALUE, inputFile);
    }
}
