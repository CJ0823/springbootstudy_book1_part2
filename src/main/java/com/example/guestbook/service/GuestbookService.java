package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;

public interface GuestbookService {
  Long register(GuestbookDTO dto);
  PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);
  GuestbookDTO read(Long gno);
  void remove(Long gno);
  void modify(GuestbookDTO dto);
  //Service layer에서는 DTO로 파라미터를 받도록하였고, 실제 Repo.와 연결된 변수들은 Entity(Guestbook)이기 때문에,
  //DTO를 entity 타입으로 바꿔주는 작업을 한다. 이후에 이 entity를 가지고 실제 Repo.에 적용되도록 한다.
  //엔티티는 JPA에서 영속성을 갖는 계층이고 엔티티 매니저가 관리하는 반면
  //DTO는 계층간 데이터 전달을 위해 일회성으로 사용된다. DTO를 사용하면 엔티티 객체의 범위를 한정지을 수 있으므로
  //더 안전한 코드를 사용할 수 있고 화면에 사용되는 데이터는 DTO, DB에서 사용되는 데이터는 엔티티로 본래 취지에 맞게 된다.
  default Guestbook dtoToEntity(GuestbookDTO dto) {
    Guestbook entity = Guestbook.builder()
            .gno(dto.getGno())
            .title(dto.getTitle())
            .content(dto.getContent())
            .writer(dto.getWriter())
            .build();
    return entity;
  }

  default GuestbookDTO entityToDto(Guestbook entity) {
    GuestbookDTO dto = GuestbookDTO.builder()
        .gno(entity.getGno())
        .title(entity.getTitle())
        .content(entity.getContent())
        .writer(entity.getWriter())
        .regDate(entity.getRegDate())
        .modDate(entity.getModDate())
        .build();
    return dto;
  }
}
