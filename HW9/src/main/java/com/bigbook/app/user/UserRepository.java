package com.bigbook.app.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByUsername(String username);

    @Query("select u from UserEntity u left join fetch u.favoriteBooks where u.id=:id")
    Optional<UserEntity> findByIdWithFetchFavouriteBooks(@Param("id")Integer id);
}
