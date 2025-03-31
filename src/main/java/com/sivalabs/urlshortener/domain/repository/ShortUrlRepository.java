package com.sivalabs.urlshortener.domain.repository;

import com.sivalabs.urlshortener.domain.entity.ShortUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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

    void deleteByIdInAndCreatedById(Collection<Long> ids, Long userId);
}
