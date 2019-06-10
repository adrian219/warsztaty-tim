package pl.edu.wat.tim.warsztaty.github;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import pl.edu.wat.tim.warsztaty.rest.github.dto.GitHubRepoDetailsDTO;
import pl.edu.wat.tim.warsztaty.rest.github.dto.GitHubRepoDetailsResponseDTO;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class GitHubRepoFT {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private GitHubRepoService gitHubRepoService;

  private MockRestServiceServer mockServer;
  private ObjectMapper objectMapper = new ObjectMapper();


  @Before
  public void init() {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    mockServer = MockRestServiceServer.createServer(restTemplate);
  }

  @Test
  public void givenOnwnerAndRepository_whenCallservice_thenReturnDetails() throws JsonProcessingException {
    //given
    String owner = "dijuijnsdjnisd";
    String repositoryName = "kuskdjisdjn";

    String fullName = "sduisdfu";
    String description = "dshncjsdnics";
    String cloneUrl = "kjsdnijdsn";
    Integer stars = 0;
    LocalDateTime date = LocalDateTime.of(2019, 2, 2, 2, 1);

    GitHubRepoDetailsDTO dto = new GitHubRepoDetailsDTO(fullName, description, cloneUrl, stars, date);

    mockServer.expect(ExpectedCount.once(),
            requestTo("https://api.github.com/repos/" + owner + "/" + repositoryName))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(objectMapper.writeValueAsString(dto))
            );

    //when
    GitHubRepoDetailsResponseDTO responseDTO = gitHubRepoService.getDetails(owner, repositoryName);
    mockServer.verify();

    //then
    assertNotNull(dto);
    assertEquals(dto.getFullName(), responseDTO.getFullName());
    assertEquals(dto.getDescription(), responseDTO.getDescription());
    assertEquals(dto.getCloneUrl(), responseDTO.getCloneUrl());
    assertEquals(dto.getStars(), responseDTO.getStars());
    assertEquals(dto.getCreatedAt(), responseDTO.getCreatedAt());
  }
}
