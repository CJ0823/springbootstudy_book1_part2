package com.example.guestbook.repository;

import com.example.guestbook.entity.Guestbook;

import com.example.guestbook.entity.QGuestbook;
import com.example.guestbook.repository.GuestbookRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {
    @Autowired
    private GuestbookRepository guestbookRepository;
    @Test
    public void testClass(){
        System.out.println(guestbookRepository.getClass());
    }
    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title..." + i)
                    .content("Content" + i)
                    .writer("user" + (i % 10))
                    .build();
//            guestbookRepository.save(guestbook);
            System.out.println(guestbookRepository.save(guestbook));
        });
    }
    @Test
    public void updateTest() {
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        if(result.isPresent()) {
            Guestbook guestbook = result.get(); // get()은 Optional의 메서드

            guestbook.changeTitle("바뀐 제목");
            guestbook.changeContent("바뀐 내용");

            guestbookRepository.save(guestbook);
        }
    }
    @Test
    public void testQuery1() {
        //pageable 지정 시 size는 DB를 한페이지에 몇개씩 나눌 것인지, page는 나눠진 페이지들 중 몇번째 페이지를 불러올 것인지를 나타낸다.
        Pageable pageable = PageRequest.of(1, 50, Sort.by("gno").descending());
        //1. Q 도메인 클래스를 불러와서 엔티티 클래스에 선언된 title, content 같은 필드들을 변수로 활용한다.
        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";
        //2. where문에 들어가는 조건들을 넣어주는 컨테이너를 생성한다.
        BooleanBuilder builder = new BooleanBuilder();
        //3. 조건을 만든다. 먼저 선언한 keyword와 조합한다.
        BooleanExpression expression = qGuestbook.title.contains(keyword);
        //4. 만들어진 조건문을 and 또는 or와 결합하여 builder에 넣는다.
        builder.and(expression);
        //5. QuerydslPredicateExecutor 인터페이스의 findAll() 메서드를 이용한다.
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
    @Test
    public void testQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);
        //lt는 less than 으로 gno가 200보다 작다라는 조건을 넣어주었다.
        builder.and(qGuestbook.gno.lt(200L));
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook ->  {
            System.out.println(guestbook);
        });
    }
}
