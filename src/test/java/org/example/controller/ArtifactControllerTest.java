package org.example.controller;

import org.example.constants.ErrorMessage;
import org.example.constants.Pages;
import org.example.constants.PathConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.example.util.TestConstants.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-artifacts-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-artifacts-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ArtifactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[200] GET /artifact/1 - Get artifacts")
    public void getArtifactById() throws Exception {
        mockMvc.perform(get(PathConstants.ARTIFACT + "/{artifactId}", ARTIFACT_ID))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ARTIFACT))
                .andExpect(model().attribute("artifact", hasProperty("id", is(ARTIFACT_ID))))
                .andExpect(model().attribute("artifact", hasProperty("artifactTitle", is(ARTIFACT_TITLE))))
                .andExpect(model().attribute("artifact", hasProperty("artifactr", is(ARTIFACTER))))
                .andExpect(model().attribute("artifact", hasProperty("year", is(YEAR))))
                .andExpect(model().attribute("artifact", hasProperty("country", is(COUNTRY))))
                .andExpect(model().attribute("artifact", hasProperty("artifactGender", is(ARTIFACT_GENDER))))
                .andExpect(model().attribute("artifact", hasProperty("productTopNotes", is(PRODUCT_TOP_NOTES))))
                .andExpect(model().attribute("artifact", hasProperty("productMiddleNotes", is(PRODUCT_MIDDLE_NOTES))))
                .andExpect(model().attribute("artifact", hasProperty("productBaseNotes", is(PRODUCT_BASE_NOTES))))
                .andExpect(model().attribute("artifact", hasProperty("filename", is(FILENAME))))
                .andExpect(model().attribute("artifact", hasProperty("price", is(PRICE))))
                .andExpect(model().attribute("artifact", hasProperty("volume", is(VOLUME))))
                .andExpect(model().attribute("artifact", hasProperty("type", is(TYPE))));
    }

    @Test
    @DisplayName("[404] GET /artifact/111 - Get artifact By Id NotFound")
    public void getArtifactById_NotFound() throws Exception {
        mockMvc.perform(get(PathConstants.ARTIFACT + "/{artifactId}", 111))
                .andExpect(status().isNotFound())
                .andExpect(status().reason(ErrorMessage.ARTIFACT_NOT_FOUND));
    }

    @Test
    @DisplayName("[200] GET /artifact - Get artifacts By Filter Params")
    public void getArtifactsByFilterParams() throws Exception {
        mockMvc.perform(get(PathConstants.ARTIFACT))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ARTIFACTS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(12))));
    }

    @Test
    @DisplayName("[200] GET /artifact - Get artifacts By Filter Params: artifacters")
    public void getArtifactsByFilterParams_Artifactrs() throws Exception {
        mockMvc.perform(get(PathConstants.ARTIFACT)
                        .param("artifactrs", "Creed"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ARTIFACTS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(7))));
    }

    @Test
    @DisplayName("[200] GET /artifact - Get artifacts By Filter Params: artifacters, genders")
    public void getArtifactsByFilterParams_ArtifactrsAndGenders() throws Exception {
        mockMvc.perform(get(PathConstants.ARTIFACT)
                        .param("artifactrs", "Creed")
                        .param("genders", "male"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ARTIFACTS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(3))));
    }

    @Test
    @DisplayName("[200] GET /artifact - Get artifacts By Filter Params: artifacters, genders, price")
    public void getArtifactsByFilterParams_artifActrsAndGendersAndPrice() throws Exception {
        mockMvc.perform(get(PathConstants.ARTIFACT)
                        .param("artifactrs", "Creed", "Dior")
                        .param("genders", "male")
                        .param("price", "60"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ARTIFACTS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(5))));
    }

    @Test
    @DisplayName("[200] GET /artifact/search - Search artifacts By artifacter")
    public void searchArtifacts_ByArtifactr() throws Exception {
        mockMvc.perform(get(PathConstants.ARTIFACT + "/search")
                        .param("searchType", "artifactr")
                        .param("text", "Creed"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ARTIFACTS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(7))));
    }

    @Test
    @DisplayName("[200] GET /artifact/search - Search artifacts By Country")
    public void searchArtifacts_ByCountry() throws Exception {
        mockMvc.perform(get(PathConstants.ARTIFACT + "/search")
                        .param("searchType", "country")
                        .param("text", "Spain"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ARTIFACTS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(2))));
    }

    @Test
    @DisplayName("[200] GET /artifact/search - Search artifacts By artifact Title")
    public void searchArtifacts_artifactTitle() throws Exception {
        mockMvc.perform(get(PathConstants.ARTIFACT + "/search")
                        .param("searchType", "artifactTitle")
                        .param("text", "Aventus"))
                .andExpect(status().isOk())
                .andExpect(view().name(Pages.ARTIFACTS))
                .andExpect(model().attribute("page", hasProperty("content", hasSize(2))));
    }
}
