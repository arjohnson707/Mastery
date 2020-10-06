package learn.dontWreckMyHouse.data;

import learn.dontWreckMyHouse.models.Guest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuestFileRepository implements GuestRepository {

//    private static final String HEADER = "guest_id,first_name,last_name,email,phone,state";
    private final String filePath;

    public GuestFileRepository(String filePath){
        this.filePath = filePath;
    }

    @Override
    public List<Guest> findAll() {
        ArrayList<Guest> result = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); //reads header line and ignores

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",",-1);
                if(fields.length == 6){
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            System.out.println("ERROR: Could Not Find File!");
        }
        return result;
    }

    @Override
    public Guest findById(int id){
        return findAll().stream()
                .filter(i -> i.getGuestId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest findByEmail(String email){
        return findAll().stream()
                .filter(g -> g.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Guest> findByLastName(String lastName) {
        return findAll().stream()
                .filter(g -> g.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }



    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setGuestId(Integer.parseInt(fields[0]));
        result.setFirstName(fields[1]);
        result.setLastName(fields[2]);
        result.setEmail(fields[3]);
        result.setPhone(fields[4]);
        result.setState(fields[5]);
        return result;
    }


}
