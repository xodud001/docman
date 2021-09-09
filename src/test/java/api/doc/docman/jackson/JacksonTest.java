package api.doc.docman.jackson;

import api.doc.docman.domain.Car;
import api.doc.docman.domain.Owner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.hamcrest.MatcherAssert.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class JacksonTest {

    private Owner createOwner(){
        Car car1 = Car.builder().name("K5").carNumber("1234").type("sedan").build();
        Car car2 = Car.builder().name("K5").carNumber("1234").type("sedan").build();
        return Owner.builder()
                .name("김태영")
                .age(10)
                .cars(Arrays.asList(car1, car2))
                .build();
    }
    @DisplayName("1. Object to Json")
    @Test
    void test1() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Owner owner= createOwner();
        String json = objectMapper.writeValueAsString(owner);
        System.out.println(json);
    }


    @DisplayName("2. ObjectMapper Data to JsonNode And Extract Data From JsonNode")
    @Test
    void test2() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Owner owner= createOwner();
        String json = objectMapper.writeValueAsString(owner);
        JsonNode jsonNode = objectMapper.readTree(json);
        String name = jsonNode.get("name").asText();
        int age = jsonNode.get("age").asInt();

        assertThat(name, is(equalTo("김태영")));
        assertThat(age, is(equalTo(10)));
    }

    @DisplayName("3. Convert Json String to Car List")
    @Test
    void test3() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Owner owner= createOwner();
        String json = objectMapper.writeValueAsString(owner);
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode cars = jsonNode.get("cars");
        ArrayNode arrayNode = (ArrayNode) cars;
        List<Car> carList = objectMapper.convertValue(arrayNode, new TypeReference<List<Car>>() {});
        System.out.println(carList);

        assertThat(carList.size(), is(equalTo(2)));
    }

    @DisplayName("4. Using Object Node To Change JsonNode Value")
    @Test
    void test4() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Owner owner= createOwner();

        String json = objectMapper.writeValueAsString(owner);
        JsonNode jsonNode = objectMapper.readTree(json);

        ObjectNode objectNode = (ObjectNode) jsonNode;
        objectNode.put("name", "김동영");
        objectNode.put("age", 20);

        System.out.println(objectNode);

        assertThat(objectNode.get("name").asText(), is(equalTo("김동영")));
        assertThat(objectNode.get("age").asInt(), is(equalTo(20)));

    }

}
