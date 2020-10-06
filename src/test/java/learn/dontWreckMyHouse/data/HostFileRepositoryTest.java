package learn.dontWreckMyHouse.data;

import learn.dontWreckMyHouse.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    static final String SEED_PATH_FILE = "./data/hosts-seed.csv";
    static final String TEST_PATH_FILE = "./data/hosts-test.csv";

    HostFileRepository repo = new HostFileRepository(TEST_PATH_FILE);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH_FILE);
        Path testPath = Paths.get(TEST_PATH_FILE);
        Files.copy(seedPath,testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findAll() {
        List<Host> all = repo.findAll();
        assertNotNull(all);
        assertEquals(1000,all.size());
    }

    @Test
    void findById() {
        Host randomIdFromFile = repo.findById("7704ff8f-2fe3-46ce-83d8-be1310d9728a");
        assertNotNull(randomIdFromFile);
        assertEquals("Glennie", randomIdFromFile.getLastName());
    }

    @Test
    void findByIdReturnsNull() {
        Host randomId = repo.findById("asfsafagfawa11111");
        assertNull(randomId);
    }

    @Test
    void findByEmail() {
        Host randomEmailFromFile = repo.findByEmail("ddavidsen2r@timesonline.co.uk");
        assertNotNull(randomEmailFromFile);
        assertEquals("e71f45ab-3b34-424f-bab7-bfd78925558f",
                randomEmailFromFile.getId());
    }

    @Test
    void findByEmailReturnsNull() {
        Host randomEmail = repo.findByEmail("randomEmail@doesNot.exist");
        assertNull(randomEmail);
    }

    @Test
    void findByState() {
        List<Host> stateIndiana = repo.findByState("IN");
        assertEquals(23,stateIndiana.size());
    }

    @Test
    void findByMadeUpState() {
        List<Host> stateMadeUp = repo.findByState("ZZ");
        assertEquals(0,stateMadeUp.size());
    }

    @Test
    void findByLastName() {
        List<Host> randomLastName = repo.findByLastName("McGrady");
        assertEquals(2, randomLastName.size());
    }

    @Test
    void findByLastNameNotInFile() {
        List<Host> randomLastName = repo.findByLastName("Johnson");
        assertEquals(0, randomLastName.size());
    }
}