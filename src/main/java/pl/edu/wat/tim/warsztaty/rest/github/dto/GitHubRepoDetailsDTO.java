package pl.edu.wat.tim.warsztaty.rest.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class GitHubRepoDetailsDTO implements Serializable {
  @JsonProperty("full_name")
  private String fullName;
  private String description;
  @JsonProperty("clone_url")
  private String cloneUrl;
  @JsonProperty("stargazers_count")
  private Integer stars;
  @JsonProperty("created_at")
  private LocalDateTime createdAt;

  public GitHubRepoDetailsResponseDTO getResponseDTO() {
    return new GitHubRepoDetailsResponseDTO(
            fullName,
            description,
            cloneUrl,
            stars,
            createdAt
            );
  }
}
