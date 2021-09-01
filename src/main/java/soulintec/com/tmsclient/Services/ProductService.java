package soulintec.com.tmsclient.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soulintec.com.tmsclient.Entities.ProductDTO;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    public Optional<ProductDTO> findById(long parseLong) {
        return null;
    }

    public void save(ProductDTO productDTO) {

    }

    public void deleteById(long parseLong) {

    }

    public List<ProductDTO> findAll() {
        return null;
    }

    public ProductDTO findByName(String text) {
        return null;
    }
}
