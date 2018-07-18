package demo.springboot.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: wq
 * @date: 2018/7/17 15:27
 */
public interface ReadingListRepository extends JpaRepository<Book, Long> {

    List<Book> findByReader(String reader);
}
