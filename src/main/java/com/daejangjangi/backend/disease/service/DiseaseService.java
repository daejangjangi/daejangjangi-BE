package com.daejangjangi.backend.disease.service;

import com.daejangjangi.backend.disease.domain.Disease;
import com.daejangjangi.backend.disease.exception.NotManagedDiseaseException;
import com.daejangjangi.backend.disease.repository.DiseaseRepository;
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
