package learn.dontWreckMyHouse.data;

import learn.dontWreckMyHouse.models.Host;

import java.util.List;

public interface HostRepository {
    List<Host> findAll();

    Host findById(String id);

    Host findByEmail(String email);

    List<Host> findByState(String state);

    List<Host> findByLastName(String lastName);
}
