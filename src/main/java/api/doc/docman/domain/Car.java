package api.doc.docman.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Car {
    private String name
            ;
    @JsonProperty("car_number")
    private String carNumber;

    @JsonProperty("TYPE")
    private String type;
}
