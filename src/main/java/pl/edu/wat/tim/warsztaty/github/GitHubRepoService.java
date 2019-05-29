package pl.edu.wat.tim.warsztaty.github;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.edu.wat.tim.warsztaty.rest.github.dto.GitHubRepoDetailsDTO;
import pl.edu.wat.tim.warsztaty.rest.github.dto.GitHubRepoDetailsResponseDTO;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class GitHubRepoService {
  private final String GITHUB_ROOT_API_URL = "https://api.github.com";
  private final String REPOSITORIES_URL = "/repos";

  private final RestTemplate restTemplate;

  public GitHubRepoDetailsResponseDTO getDetails(String owner, String repositoryName) {
    GitHubRepoDetailsDTO dto = callService(buildDetailsUrl(owner, repositoryName));
    log.debug("GET DETAILS FROM API: {}", dto.toString());
    GitHubRepoDetailsResponseDTO responseDTO = dto.getResponseDTO();

    log.info("GIVEN DETAILS: {}", responseDTO.toString());
    return responseDTO;
  }

  private GitHubRepoDetailsDTO callService(String url) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<>(headers);

    log.info("CALL GITHUB SERVICE: [url={}]", url);
    ResponseEntity<GitHubRepoDetailsDTO> response;
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, entity, GitHubRepoDetailsDTO.class);
    } catch (HttpClientErrorException e) {
      checkStatusCode(e);
      throw e;
    }

    return response.getBody();
  }

  private void checkStatusCode(HttpClientErrorException e) {
    if (e.getStatusCode().value() == 404) {
      throw new GitHubRepoNotFoundException();
    }
  }

  private String buildDetailsUrl(String owner, String repository) {
    StringBuilder sb = new StringBuilder();
    return sb
            .append(GITHUB_ROOT_API_URL)
            .append(REPOSITORIES_URL)
            .append("/")
            .append(owner)
            .append("/")
            .append(repository)
            .toString();
  }
}
