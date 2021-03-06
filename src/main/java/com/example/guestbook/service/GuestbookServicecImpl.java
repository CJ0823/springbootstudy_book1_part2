package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class GuestbookServicecImpl implements GuestbookService{
  @Override
  public Long register(GuestbookDTO dto) {
    return null;
  }
}
