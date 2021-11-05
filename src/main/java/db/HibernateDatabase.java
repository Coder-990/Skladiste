package main.java.db;

import main.java.dto.PoduzeceToDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HibernateDatabase extends JpaRepository<PoduzeceToDto, Long> {
}
