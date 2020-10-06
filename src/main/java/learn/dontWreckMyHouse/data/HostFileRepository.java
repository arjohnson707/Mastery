package learn.dontWreckMyHouse.data;

import learn.dontWreckMyHouse.models.Host;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HostFileRepository implements HostRepository {

    private final String filePath;

    public HostFileRepository(String filePath) {
        this.filePath = filePath;
    }


    @Override
    public List<Host> findAll() {
        ArrayList<Host> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader((new FileReader(filePath)))) {
            reader.readLine(); //this is to read over header.

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if (fields.length == 10) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            System.out.println("ERROR: Could Not Find File!");
        }
        return result;
    }

    @Override
    public Host findById(String id) {
        return findAll().stream()
                .filter(h -> h.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Host findByEmail(String email) {
        return findAll().stream()
                .filter(h -> h.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Host> findByState(String state) {
        return findAll().stream()
                .filter(h -> h.getState().equalsIgnoreCase(state))
                .collect(Collectors.toList());
    }

    @Override
    public List<Host> findByLastName(String lastName) {
        return findAll().stream()
                .filter(h -> h.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    private Host deserialize(String[] fields) {
        Host result = new Host();
        result.setId(fields[0]);
        result.setLastName(fields[1]);
        result.setEmail(fields[2]);
        result.setPhone(fields[3]);
        result.setAddress(fields[4]);
        result.setCity(fields[5]);
        result.setState(fields[6]);
        result.setPostalCode(Integer.parseInt(fields[7]));
        result.setStandardRate(new BigDecimal(fields[8]));
        result.setWeekendRate(new BigDecimal(fields[9]));
        return result;
    }
}
