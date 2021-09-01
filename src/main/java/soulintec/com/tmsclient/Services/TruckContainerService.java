package soulintec.com.tmsclient.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soulintec.com.tmsclient.Entities.TruckContainerDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TruckContainerService {
    public Collection<TruckContainerDTO> findByLicence(long parseLong) {

        return null;
    }

    public void save(TruckContainerDTO container) {

    }

    public void deleteById(long parseLong) {

    }

    public Collection<TruckContainerDTO> findById(long parseLong) {
        return null;
    }

    public List<TruckContainerDTO> findAll() {
        return new ArrayList<>();
    }
}
