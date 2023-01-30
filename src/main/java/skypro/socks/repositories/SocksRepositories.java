package skypro.socks.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import skypro.socks.models.Socks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SocksRepositories extends JpaRepository<Socks, Long> {
    Optional<Socks> findSocksByColorAndCottonPart(String color, Integer cottonPart);

    @Query("SELECT SUM(s.quantity) FROM Socks s WHERE s.color = :color and s.cottonPart < :cottonPart")
    Optional<Integer> findQuantityOfSocksByColorAndCottonPartLessThanCurrentCondition(
            @Param("color") String color,
            @Param("cottonPart") Integer cottonPart
    );

    @Query("SELECT SUM(s.quantity) FROM Socks s WHERE s.color = :color and s.cottonPart > :cottonPart")
    Optional<Integer> findQuantityOfSocksByColorAndCottonPartGreaterThanCurrentCondition(
            @Param("color") String color,
            @Param("cottonPart") Integer cottonPart
    );

    @Query("SELECT SUM(s.quantity) FROM Socks s WHERE s.color = :color and s.cottonPart = :cottonPart")
    Optional<Integer> findQuantityOfSocksByColorAndCottonPartEqualsToCurrentCondition(
            @Param("color") String color,
            @Param("cottonPart") Integer cottonPart
    );

}
