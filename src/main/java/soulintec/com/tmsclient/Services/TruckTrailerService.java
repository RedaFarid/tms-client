package soulintec.com.tmsclient.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soulintec.com.tmsclient.Entities.TruckContainerDTO;
import soulintec.com.tmsclient.Entities.TruckTrailerDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TruckTrailerService {
    public Collection<TruckTrailerDTO> findByLicence(long parseLong) {
        return null;
    }

    public void deleteById(long parseLong) {

    }

    public Optional<TruckTrailerDTO> findById(long parseLong) {
        return null;
    }

    public void save(TruckTrailerDTO trailer) {

    }

    public List<TruckTrailerDTO> findAll() {
        return new ArrayList<>();
    }
}
