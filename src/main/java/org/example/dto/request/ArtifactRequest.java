package org.example.dto.request;

import org.example.constants.ErrorMessage;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class ArtifactRequest {
    private Long id;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String artifactTitle;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String artifacter;

    @NotNull(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Min(value = 4, message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    private Integer year;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String country;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String artifactGender;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String productTopNotes;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String productMiddleNotes;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String productBaseNotes;

    private String description;
    private String filename;

    @NotNull(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Min(value = 1, message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    private Integer price;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String volume;

    @NotBlank(message = ErrorMessage.FILL_IN_THE_INPUT_FIELD)
    @Length(max = 255)
    private String type;
}
