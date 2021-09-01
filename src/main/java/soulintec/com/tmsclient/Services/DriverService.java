package soulintec.com.tmsclient.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soulintec.com.tmsclient.Entities.DriverDTO;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverService {
    public Optional<DriverDTO> findByLicenceId(String text) {
        return null;
    }

    public void save(DriverDTO driver) {

    }

    public List<DriverDTO> findAll() {
        return null;
    }

    public void deleteById(long parseLong) {

    }
}
