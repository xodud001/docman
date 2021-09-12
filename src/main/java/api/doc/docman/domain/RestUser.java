package api.doc.docman.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RestUser {

    private String id;
    private String pw;

    @NotBlank
    private String name;

    @Min(value = 0)
    private int age;

    @Email
    private String email;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxx[x]-xxxx")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Size(max = 6, min = 6)
    private String reqYearMonth;// yyyyMM

    @AssertTrue(message ="날짜 형식이 맞지않습니다. yyyyMM")
    public boolean isReqYearMonthValidation(){
        try{
            LocalDate localDate = LocalDate.parse(getReqYearMonth() + "01", DateTimeFormatter.ofPattern("yyyyMMdd"));
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
