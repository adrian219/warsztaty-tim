package pl.edu.wat.tim.warsztaty.rest.github.dto;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class GitHubRepoDetailsMapUT {

  @Test
  public void checkCorrectData() {
    //given
    String fullName = "adrian219/xml-visual-random";
    String description = "random-remmeme";
    String cloneUrl = "sdkjnsidn";
    Integer stars = 0;
    LocalDateTime createdAt = LocalDateTime.of(2019, 1, 1, 1, 1);
    GitHubRepoDetailsDTO dto = new GitHubRepoDetailsDTO(fullName, description, cloneUrl, stars, createdAt);

    //when
    GitHubRepoDetailsResponseDTO responseDTO = dto.getResponseDTO();

    //then
    assertEquals(fullName, responseDTO.getFullName());
    assertEquals(description, responseDTO.getDescription());
    assertEquals(cloneUrl, responseDTO.getCloneUrl());
    assertEquals(stars, responseDTO.getStars());
    assertEquals(createdAt, responseDTO.getCreatedAt());
  }
}
