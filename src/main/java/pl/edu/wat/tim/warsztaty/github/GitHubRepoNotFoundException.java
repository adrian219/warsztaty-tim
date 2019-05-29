package pl.edu.wat.tim.warsztaty.github;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GitHubRepoNotFoundException extends RuntimeException {
}
