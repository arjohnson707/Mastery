package learn.dontWreckMyHouse.data;

import learn.dontWreckMyHouse.models.Guest;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAll();

    Guest findById(int id);

    Guest findByEmail(String email);

    List<Guest> findByLastName(String lastName);
}
