import com.fasterxml.jackson.databind.ObjectMapper;
import model.JsonModel;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class ParsingJsonTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void jsonReadTest() throws Exception {

        File file = new File("src/test/resources/info.json");
        JsonModel json = objectMapper.readValue(file, JsonModel.class);

        assertAll(
                () -> assertNotNull(json.getId(),
                        "ID is null"),
                () -> assertEquals("Bruce Wayne", json.getName(),
                        "Name is different"),
                () -> assertTrue(json.isActive(),
                        "User is not active"),
                () -> assertNotNull(json.getAccounts().get(0).getNumber(),
                        "Number of account is null"),
                () -> assertEquals("US", json.getAccounts().get(1).getCurrency(),
                        "Currency is absent"),
                () -> assertEquals("VTB", json.getAccounts().get(0).getBank())
        );
    }
}
