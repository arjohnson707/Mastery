package learn.dontWreckMyHouse.data;

import learn.dontWreckMyHouse.models.Guest;
import learn.dontWreckMyHouse.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {

    //header because I am writing to a file:
    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findByHostId(String hostId) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostId)))) {
            reader.readLine();

            for(String line = reader.readLine();line !=null;line =reader.readLine()) {
                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Could not find file!");
            //e.printStackTrace();
        }

        return result;
    }

    @Override
    public Reservation findById(int id, String hostId) {
        return findByHostId(hostId).stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }


    @Override
    public Reservation add(Reservation reservation, String hostId) throws DataException {
        List<Reservation> all = findByHostId(hostId);

        //getting id
        int nextId = all.stream()
                .mapToInt(Reservation::getId)
                .max()
                .orElse(0) + 1;

        reservation.setId(nextId);

        //adding and writing
        all.add(reservation);
        writeAll(all,hostId);

        return reservation;
    }

    @Override
    public boolean update(Reservation reservation, String hostId) throws DataException {
        if(reservation == null) {
            return false;
        }

        List<Reservation> all = findByHostId(hostId);
        for(int i = 0; i < all.size(); i++){
            if(reservation.getId() == all.get(i).getId()){
                all.set(i, reservation);
                writeAll(all, hostId);
                return true;
            }
        }

        return false;
    }


    //may need to just send an id through to delete and not an entire reservation? Or a guestID?
    @Override
    public boolean deleteById(int id, String hostId) throws DataException {
        List<Reservation> all = findByHostId(hostId);
        for(int i = 0; i< all.size(); i++){
            if(id == all.get(i).getId()) {
                all.remove(i);
                writeAll(all,hostId);
                return true;
            }
        }
        return false;
    }




    private String getFilePath(String hostId) {
        return Paths.get(directory, hostId + ".csv").toString();
    }

    private void writeAll(List<Reservation> reservations, String hostId) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostId))) {
            writer.println(HEADER);

            for(Reservation reservation: reservations){
                writer.println(serialize(reservation));
            }
        } catch (FileNotFoundException e) {
            throw new DataException(e);
        }
    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuest().getGuestId(),
                reservation.getTotal());
    }


    private Reservation deserialize(String[] fields) {
        Reservation result = new Reservation();
        result.setId(Integer.parseInt(fields[0]));
        result.setStartDate(LocalDate.parse(fields[1]));
        result.setEndDate(LocalDate.parse(fields[2]));
        result.setTotal(new BigDecimal(fields[4]));

        Guest guest = new Guest();
        guest.setGuestId(Integer.parseInt(fields[3]));
        result.setGuest(guest);

        return result;
    }
}
