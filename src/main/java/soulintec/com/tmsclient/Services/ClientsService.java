package soulintec.com.tmsclient.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import soulintec.com.tmsclient.Entities.ClientDTO;
import soulintec.com.tmsclient.Entities.ClientLocationsDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClientsService {
    public Optional<ClientDTO> findById(Long clientID) {
        return null;
    }

    public List<ClientDTO> findAll() {

        return new ArrayList<>();
    }

    public void save(ClientDTO location) {

    }

    public void deleteById(long parseLong) {

    }

    public void saveLocation(ClientLocationsDTO location) {

    }

    public void deleteByClientName(long parseLong) {

    }

    public List<ClientLocationsDTO> findAllLocations() {
        return null;
    }
}
