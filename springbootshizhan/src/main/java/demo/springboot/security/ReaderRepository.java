package demo.springboot.security;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: wq
 * @date: 2018/7/18 9:18
 */
public interface ReaderRepository extends JpaRepository<Reader, String> {
}
