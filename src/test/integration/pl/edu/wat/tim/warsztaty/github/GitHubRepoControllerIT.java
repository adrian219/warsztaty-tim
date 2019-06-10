package pl.edu.wat.tim.warsztaty.github;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.wat.tim.warsztaty.rest.github.GitHubRepoController;
import pl.edu.wat.tim.warsztaty.rest.github.dto.GitHubRepoDetailsResponseDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = GitHubRepoController.class)
public class GitHubRepoControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GitHubRepoService gitHubRepoService;

  @DisplayName("when correct GET /repositories/{owner}/{repository}, then returns details of given GitHub repository")
  @Test
  public void givenOwnerAndRepository_whenCallService_thenReturnNotNullData() throws Exception {
    //given
    String owner = "adrian219";
    String repository = "xml-visual-editor-app";
    String description = "Xml Visual Editor Application";
    String cloneUrl = "https://github.com/adrian219/xml-visual-editor-app.git";
    Integer stars = 0;
    LocalDateTime createdAt = LocalDateTime.of(2019, 1, 1, 1, 1, 0);
    when(gitHubRepoService.getDetails(owner, repository))
            .thenReturn(getResponseDTO(owner, repository, description, cloneUrl, stars, createdAt));

    //when
    // @formatter:off
    mockMvc.perform(get(getUriAddress(owner, repository)))

    //then
    .andExpect(status().isOk())
    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    .andExpect(jsonPath("fullName", is(getFullName(owner, repository))))
    .andExpect(jsonPath("description", is(description)))
    .andExpect(jsonPath("cloneUrl", is(cloneUrl)))
    .andExpect(jsonPath("stars", is(stars)))
    .andExpect(jsonPath("createdAt", is(createdAt.format(DateTimeFormatter.ISO_DATE_TIME))));
    // @formatter:on
  }

  private String getUriAddress(String owner, String repository) {
    StringBuilder sb = new StringBuilder();
    return sb
            .append("/repositories/")
            .append(owner)
            .append("/")
            .append(repository)
            .toString();
  }

  private GitHubRepoDetailsResponseDTO getResponseDTO(String owner, String repository, String description, String cloneUrl, Integer stars, LocalDateTime createdAt) {
    return new GitHubRepoDetailsResponseDTO(
            getFullName(owner, repository),
            description,
            cloneUrl,
            stars,
            createdAt);
  }

  private String getFullName(String owner, String repository) {
    StringBuilder sb = new StringBuilder();
    return sb
            .append(owner)
            .append("/")
            .append(repository)
            .toString();
  }
}
