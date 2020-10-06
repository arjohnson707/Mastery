package learn.dontWreckMyHouse.data;

import learn.dontWreckMyHouse.models.Guest;
import learn.dontWreckMyHouse.models.Host;
import learn.dontWreckMyHouse.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_PATH_FILE = "./data/reservations-test/2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c-seed.csv";
    static final String TEST_PATH_FILE = "./data/reservations-test/2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c.csv";
    static final String TEST_DIRECTORY_PATH = "./data/reservations-test";

    ReservationFileRepository repo = new ReservationFileRepository(TEST_DIRECTORY_PATH);

    final String hostId = "2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c";
    final LocalDate start = LocalDate.of(2020, 10,1);
    final LocalDate end = LocalDate.of(2020,10,30);

    @BeforeEach
    void setUp() throws IOException {
        Path seedPath = Paths.get(SEED_PATH_FILE);
        Path testPath = Paths.get(TEST_PATH_FILE);
        Files.copy(seedPath,testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findByHostId() {
        List<Reservation> reservations = repo.findByHostId(hostId);
        assertEquals(13, reservations.size());
    }

    @Test
    void couldNotFindByHostId() {
        List<Reservation> reservations = repo.findByHostId("NOOO");
        assertEquals(0, reservations.size());
    }

    @Test
    void findById() {
        Reservation reservation = new Reservation();
        reservation = repo.findById(5,hostId);
        assertNotNull(reservation);
        assertEquals(new BigDecimal(540), reservation.getTotal());
    }


    @Test
    void add() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(start);
        reservation.setEndDate(end);
        reservation.setTotal(new BigDecimal(300));

        Guest guest = new Guest();
        guest.setGuestId(13);
        reservation.setGuest(guest);


        assertNotNull(reservation);
        reservation = repo.add(reservation,hostId);


        assertEquals(14 ,reservation.getId());
    }

    @Test
    void update() throws DataException {
        Reservation reservation = new Reservation();

        reservation.setId(13);
        reservation.setTotal(new BigDecimal(500));
        reservation.setStartDate(LocalDate.of(2020,11,20));
        reservation.setEndDate(LocalDate.of(2020,11,28));


        Guest guest = new Guest();
        guest.setGuestId(656);
        reservation.setGuest(guest);

        boolean success = repo.update(reservation,hostId);
        assertTrue(success);

        Reservation actual = repo.findById(13,hostId);
        assertEquals(656,actual.getGuest().getGuestId());
    }



    @Test
    void deleteById() throws DataException {
        boolean actual = repo.deleteById(13, hostId);
        assertTrue(actual);

    }
}