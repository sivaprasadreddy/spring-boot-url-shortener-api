package com.sivalabs.urlshortener.domain.models;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record PagedResult<T>(
        List<T> data,
        int pageNumber,
        int totalPages,
        long totalElements,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious) {

    public static <T> PagedResult<T> from(Page<T> page) {
        return new PagedResult<>(
                page.getContent(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }

    public <R> PagedResult<R> map(Function<T, R> converter) {
        return new PagedResult<>(
                this.data.stream().map(converter).toList(),
                this.pageNumber,
                this.totalPages,
                this.totalElements,
                this.isFirst,
                this.isLast,
                this.hasNext,
                this.hasPrevious);
    }
}
