package com.example.guestbook.repository;

import com.example.guestbook.entity.Guestbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {
    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies(){
//        IntStream.rangeClosed(1, 300).forEach(i -> {
//            Guestbook guestbook = Guestbook.builder()
//                    .title("Title..." + i)
//                    .content("Content" + i)
//                    .writer("user" + (i % 10))
//                    .build();
//
//            System.out.println(i);
//            guestbookRepository.save(guestbook);
////            System.out.println(guestbookRepository.save(guestbook));
//        });
                    Guestbook guestbook = Guestbook.builder()
                    .title("Title...")
                    .content("Content" )
                    .writer("user")
                    .build();
        guestbookRepository.save(guestbook);
    }

}
