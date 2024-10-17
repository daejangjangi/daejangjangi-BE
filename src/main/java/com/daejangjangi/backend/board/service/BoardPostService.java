package com.daejangjangi.backend.board.service;

import com.daejangjangi.backend.board.repository.BoardPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardPostService {

  private final BoardPostRepository boardPostRepository;
}
