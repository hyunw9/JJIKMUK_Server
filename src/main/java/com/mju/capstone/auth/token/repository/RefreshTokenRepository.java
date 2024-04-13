package com.mju.capstone.auth.token.repository;

import com.mju.capstone.auth.token.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken ,String> {

  Optional<RefreshToken> findByKey(String key);

}
