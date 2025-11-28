package com.example.tetris.adapter.outbound;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ScoreEntityのJPAリポジトリインターフェース。
 *
 * <p>このインターフェースは、Spring Data JPAによって自動実装されます。</p>
 *
 * <h3>責務:</h3>
 * <ul>
 *   <li>ScoreEntityの永続化操作（CRUD）</li>
 *   <li>スコアの降順でのTop10取得</li>
 * </ul>
 *
 * <h3>クエリメソッド命名規則:</h3>
 * <p>findTop10ByOrderByScoreDescTimestampDesc():</p>
 * <ul>
 *   <li>findTop10By - 最初の10件を取得</li>
 *   <li>OrderByScoreDesc - スコアの降順でソート</li>
 *   <li>TimestampDesc - 同点の場合はタイムスタンプの降順</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-28
 */
public interface ScoreJpaRepository extends JpaRepository<ScoreEntity, Long> {

    /**
     * 過去10件の高スコアをスコア降順、同点の場合はタイムスタンプ降順で取得します。
     *
     * <p>このメソッドはSpring Data JPAのクエリメソッド命名規則に従い、
     * 自動的に以下のSQLが生成されます：</p>
     * <pre>
     * SELECT * FROM scores
     * ORDER BY score DESC, timestamp DESC
     * LIMIT 10
     * </pre>
     *
     * @return 過去10件のスコアエンティティリスト（スコア降順、同点時はタイムスタンプ降順）
     */
    List<ScoreEntity> findTop10ByOrderByScoreDescTimestampDesc();
}
