package soulintec.com.tmsclient.Services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import soulintec.com.tmsclient.Entities.MaterialDTO;
import soulintec.com.tmsclient.Graphics.Controls.Utilities;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String save(MaterialDTO materialDTO) {
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP +"/saveMaterial",materialDTO, String.class);
        return deleteResponseEntity.getBody();
    }

    public List<MaterialDTO> findAll() {
        ResponseEntity<Materials> forEntity = restTemplate.getForEntity(Utilities.iP +"/material", Materials.class);
       Materials body = forEntity.getBody();
        return body.getMaterials();
    }

    public Optional<MaterialDTO> findByName(String name) {
        ResponseEntity<MaterialDTO> forEntity = restTemplate.getForEntity(Utilities.iP +"/materialByName/"+name, MaterialDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public Optional<MaterialDTO> findById(Long id) {
        ResponseEntity<MaterialDTO> forEntity = restTemplate.getForEntity(Utilities.iP +"/materialByID/"+id, MaterialDTO.class);

        return Optional.ofNullable(forEntity.getBody());
    }

    public String deleteById(Long id) {
        ResponseEntity<String> deleteResponseEntity = restTemplate.postForEntity(Utilities.iP +"/deleteMaterialByID",id,  String.class);
        return deleteResponseEntity.getBody();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Materials {
        private List<MaterialDTO> materials;
    }
}
