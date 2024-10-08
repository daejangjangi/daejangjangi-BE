package com.daejangjangi.backend.disease;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseService {

  private final DiseaseRepository diseaseRepository;

  public List<Disease> findByNames(List<String> names) {
    List<Disease> diseases = diseaseRepository.findByNameIn(names);
    if (names.size() != diseases.size()) {
      throw new NotManagedDiseaseException();
    }
    return diseases;
  }
}
