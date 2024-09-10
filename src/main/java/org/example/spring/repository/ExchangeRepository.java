package org.example.spring.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.example.spring.domain.exchange.Exchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * DeletedAt이 null인 게시물을 대상으로 글을 가져옵니다. 게시물 삭제는 Soft Delete를 사용합니다. 여러 개 게시물을 조회하는 메서드는 기본적으로 생성일 기준
 * 내림차순으로 글을 가져옵니다.
 */
@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
	// 삭제되지 않은 모든 글 조회
	Page<Exchange> findByDeletedAtIsNullOrderByCreatedAtDesc(Pageable pageable);

	// 검색 키워드에 포함되어 있는 게시글 목록 조회
	Page<Exchange> findByTitleContainingAndDeletedAtIsNullOrderByCreatedAtDesc(String title, Pageable pageable);

	// 최근 5개의 게시글 조회
	List<Exchange> findTop5ByDeletedAtIsNullOrderByCreatedAtDesc();

	// 특정 회원이 작성한 게시글 조회
	Page<Exchange> findByMemberIdAndDeletedAtIsNullOrderByCreatedAtDesc(Long memberId, Pageable pageable);

	// reviewedAt 필드가 주어진 두 Timestamp 사이에 있는 Exchange를 조회
	List<Exchange> findByReviewedAtBetween(Timestamp startTimestamp, Timestamp endTimestamp);

	// 삭제되지 않은 1개의 게시글 조회
	@Query("SELECT e FROM Exchange e WHERE e.id = :id AND e.deletedAt IS NULL")
	@EntityGraph(attributePaths = {"member"})
	Optional<Exchange> findByIdAndDeletedAtIsNull(Long id);

	// updated_at field에 반영되지 않도록 조회수 증가 처리
	@Modifying
	@Query("UPDATE Exchange e SET e.viewCount = e.viewCount + 1 WHERE e.id = :id")
	void incrementViewCount(@Param("id") Long id);
}
