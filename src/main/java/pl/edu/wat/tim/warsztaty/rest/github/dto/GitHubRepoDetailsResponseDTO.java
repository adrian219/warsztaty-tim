package pl.edu.wat.tim.warsztaty.rest.github.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GitHubRepoDetailsResponseDTO implements Serializable {
  private String fullName;
  private String description;
  private String cloneUrl;
  private Integer stars;
  private LocalDateTime createdAt;
}
