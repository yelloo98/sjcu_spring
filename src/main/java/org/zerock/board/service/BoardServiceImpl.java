package org.zerock.board.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


import org.springframework.stereotype.Service;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.repository.BoardRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository repository;


    @Override
    public List<Board> getBoardList() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.reverseOrder())
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Board> getBoardByBno(Long bno) {
        return repository.findById(bno);
    }

    @Override
    public Long register(BoardDTO dto) {

        log.info(dto);

        Board board  = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Board board  = dtoToEntity(boardDTO);
        repository.save(board);
    }

    @Override
    public void remove(Long bno) {
        repository.deleteById(bno);
    }


};
