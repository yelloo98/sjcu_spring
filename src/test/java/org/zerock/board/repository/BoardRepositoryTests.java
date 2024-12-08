package org.zerock.board.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.service.BoardService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
//@EnableJpaAuditing
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

	  @Test
	  public void insertBoard() {
		  IntStream.rangeClosed(1,100).forEach(i -> {
			  Member member = Member.builder().email("user"+i+"@aaa.com").build();
			  Board board = Board.builder() .title("Title..."+i) .content("Content...."+ i).writer(member).build();
			  boardRepository.save(board);
		  });
	  }

    @Transactional
    @Test
    public void testRead1() {
    	//조인명령을 활용한다.
        Optional<Board> result = boardRepository.findById(105L); //데이터베이스에 존재하는 번호

        Board board = result.get();
        
        System.out.println("testRead1()..............");
        System.out.println(board);
        System.out.println(board.getWriter());

    }

    @Test
    public void getListTest() {
        System.out.println("getList()..............Start");
        List<Board> boardList = boardRepository.findAll()
                .stream()
                .sorted(Comparator.reverseOrder())
                .limit(10)
                .collect(Collectors.toList());

        System.out.println(boardList);
        System.out.println("getList()..............End");
    }

    @Test
    public void modifyTest() {
        System.out.println("modifyTest()..............Start");
        Member member = Member.builder().email("user100@aaa.com").build();
        Board board = Board.builder()
                .bno(205L)
                .title("modifyTest...")
                .content("modifyTestContent....")
                .writer(member)
                .build();
        boardRepository.save(board);
        System.out.println("modifyTest()..............End");
    }

    @Test
    public void removeTest() {
        System.out.println("removeTest()..............Start");
        boardRepository.deleteById(205L);
        System.out.println("removeTest()..............End");
    }

    
}
