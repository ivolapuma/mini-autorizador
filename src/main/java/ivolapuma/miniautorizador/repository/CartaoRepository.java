package ivolapuma.miniautorizador.repository;

import ivolapuma.miniautorizador.entity.CartaoEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<CartaoEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CartaoEntity c WHERE c.numeroCartao = :numeroCartao")
    CartaoEntity findByIdWithLock(Long numeroCartao);

}
