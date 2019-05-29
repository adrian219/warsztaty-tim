package pl.edu.wat.tim.warsztaty.rest.github;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.tim.warsztaty.github.GitHubRepoService;
import pl.edu.wat.tim.warsztaty.rest.github.dto.GitHubRepoDetailsResponseDTO;

@RestController
@RequestMapping("/repositories")
@RequiredArgsConstructor
@Slf4j
public class GitHubRepoController {
  private final GitHubRepoService gitHubRepoService;

  @GetMapping("/{owner}/{repository-name}")
  public GitHubRepoDetailsResponseDTO getDetails(@PathVariable("owner") String owner, @PathVariable("repository-name") String repositoryName) {
    log.debug("GET DETAILS [owner={}, repositoryName={}]", owner, repositoryName);
    return gitHubRepoService.getDetails(owner, repositoryName);
  }
}
