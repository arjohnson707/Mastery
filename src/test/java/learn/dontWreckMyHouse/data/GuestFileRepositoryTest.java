package learn.dontWreckMyHouse.data;

import learn.dontWreckMyHouse.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    static final String SEED_PATH_FILE = "./data/guests-seed.csv";
    static final String TEST_PATH_FILE = "./data/guests-test.csv";

    GuestFileRepository repo = new GuestFileRepository(TEST_PATH_FILE);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH_FILE);
        Path testPath = Paths.get(TEST_PATH_FILE);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findAll() {
        List<Guest> all = repo.findAll();
        assertNotNull(all);
        assertEquals(1000 ,all.size());
    }

    @Test
    void findById() {
        Guest guest  = repo.findById(88);
        assertNotNull(guest);
        assertEquals("Kellby",guest.getFirstName());
    }


    @Test
    void findByIdReturnsNull() {
        Guest guest  = repo.findById(1088);
        assertNull(guest);
    }

    @Test
    void findByEmail() {
        Guest email = repo.findByEmail("ogecks1@dagondesign.com");
        assertNotNull(email);
        assertEquals(2,email.getGuestId());
    }


    @Test
    void findByEmailReturnsNull() {
        Guest email = repo.findByEmail("doesntexisting@gmail.com");
        assertNull(email);
    }

    @Test
    void findByLastName() {
        List<Guest> lastName = repo.findByLastName("Lomas");
        assertNotNull(lastName);
        assertEquals(1, lastName.size());
    }
}