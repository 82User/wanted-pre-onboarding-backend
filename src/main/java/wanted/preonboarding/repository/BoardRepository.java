package wanted.preonboarding.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.preonboarding.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
