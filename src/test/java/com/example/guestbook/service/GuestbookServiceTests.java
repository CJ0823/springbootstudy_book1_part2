package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTests {
  //Autowired 이기 떄문에 GuestbookService의 register 메서드를 Override한 부분을 자동으로 찾아서 적용한다.
  //GuestbookServiceImpl에서 register 메서드를 정의했으며, 유일하기 때문에 자동 조회 및 적용된다.
  //register 메서드를 정의하는 클래스가 여러 개 일때는 .xml 파일에서 bean의 id를 명시하는 방법이 있다.
  @Autowired
  private GuestbookService service;

  @Test
  public void testRegister(){
    GuestbookDTO guestbookDTO = GuestbookDTO.builder()
            .title("Post하는 타임리프 코드 수정 후!2")
            .content("Sample content....")
            .writer("user0")
            .build();
    System.out.println(service.register(guestbookDTO));
  }
  @Test
  public void testList() {
    PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
    PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

    System.out.println("PREV: " +resultDTO.isPrev());
    System.out.println("NEXT: " +resultDTO.isNext());
    System.out.println("TOTAL: " +resultDTO.getTotalPage());
    System.out.println("-------------------------------");
    for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
      System.out.println(guestbookDTO);
    }
    System.out.println("-------------------------------");
    resultDTO.getPageList().forEach(i -> System.out.println(i));
  }
  @Test
  public void testSearch() {
    PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
        .page(1)
        .size(10)
        .type("tc") //검색 조건 지정 t, c, w, tc, tcw...
        .keyword("한글") //검색 키워드 지정
        .build();
    PageResultDTO<GuestbookDTO, Guestbook> resultDTO =service.getList(pageRequestDTO);
    System.out.println("PREV: " +resultDTO.isPrev());
    System.out.println("NEXT: " +resultDTO.isNext());
    System.out.println("TOTAL: " +resultDTO.getTotalPage());
    System.out.println("-----------------------------");
    for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
      System.out.println(guestbookDTO);
    }
    System.out.println("================================");
    resultDTO.getPageList().forEach(i -> System.out.println(i));
  }
}
