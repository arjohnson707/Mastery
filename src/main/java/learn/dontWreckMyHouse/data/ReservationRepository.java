package learn.dontWreckMyHouse.data;

import learn.dontWreckMyHouse.models.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByHostId(String hostId);

    Reservation findById(int id, String hostId);

    Reservation add(Reservation reservation, String hostId) throws DataException;

    boolean update(Reservation reservation, String hostId) throws DataException;

    //may need to just send an id through to delete and not an entire reservation? Or a guestID?
    boolean deleteById(int id, String hostId) throws DataException;
}
