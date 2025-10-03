package com.sivalabs.urlshortener.domain.repositories;

import com.sivalabs.urlshortener.domain.entities.ShortUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findByShortKey(String shortKey);

    boolean existsByShortKey(String shortKey);

    Page<ShortUrl> findByCreatedById(Long userId, Pageable pageable);

    @Query("select u from ShortUrl u where u.isPrivate = false")
    Page<ShortUrl> findPublicShortUrls(Pageable pageable);

    @Query("select u from ShortUrl u left join fetch u.createdBy")
    Page<ShortUrl> findAllShortUrls(Pageable pageable);

    @Modifying
    @Query("delete from ShortUrl u where u.id in :ids and u.createdBy.id = :userId")
    int deleteByIdInAndCreatedById(Collection<Long> ids, Long userId);
}
