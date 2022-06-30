package basic.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class RegisterRequest {
    public static final String EMAIL_REGEX = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
    @Size(min = 2, max = 30)
    @NotBlank(message = "Name length should be in between 2-30")
    private String firstName;
    @Size(min = 2, max = 30)
    @NotBlank
    private String lastName;
    @Pattern(regexp = EMAIL_REGEX, message = "Must be a valid email")
    @NotBlank
    private String email;
}
