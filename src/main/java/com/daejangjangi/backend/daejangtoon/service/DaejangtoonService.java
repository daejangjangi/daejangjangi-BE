package com.daejangjangi.backend.daejangtoon.service;

import com.daejangjangi.backend.daejangtoon.domain.dto.DaejangtoonImageDto;
import com.daejangjangi.backend.daejangtoon.domain.entity.Daejangtoon;
import com.daejangjangi.backend.daejangtoon.domain.entity.DaejangtoonImage;
import com.daejangjangi.backend.daejangtoon.domain.mapper.DaejangtoonImageMapper;
import com.daejangjangi.backend.daejangtoon.repository.DaejangtoonImageRepository;
import com.daejangjangi.backend.daejangtoon.repository.DaejangtoonRepository;
import com.daejangjangi.backend.file.service.FileValidator;
import com.daejangjangi.backend.file.service.S3Manager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DaejangtoonService {

  private final DaejangtoonRepository daejangtoonRepository;
  private final DaejangtoonImageRepository daejangtoonImageRepository;
  private final FileValidator fileValidator;
  private final S3Manager s3Manager;

  @Transactional
  public void save(Daejangtoon daejangtoon, MultipartFile profileImage,
      List<MultipartFile> toonImages) {
    fileValidator.validate(profileImage);
    fileValidator.validate(toonImages);
    String profileUrl = s3Manager.upload(profileImage);
    List<DaejangtoonImageDto> images =
        s3Manager.upload("대장툰/" + daejangtoon.getChapter(), toonImages);
    List<DaejangtoonImage> daejangtoonImages = DaejangtoonImageMapper.INSTANCE.dtoToEntity(images);
    daejangtoon.updateProfile(profileUrl);
    daejangtoon.addImages(daejangtoonImages);
    daejangtoonRepository.save(daejangtoon);
  }

  public List<Daejangtoon> daejangtoons() {
    return daejangtoonRepository.findAllByOrderByIdDesc();
  }

  /*--------------Private----------------------------Private----------------------------Private---*/

  private void saveImages(List<DaejangtoonImage> images) {
    daejangtoonImageRepository.saveAll(images);
  }
}
