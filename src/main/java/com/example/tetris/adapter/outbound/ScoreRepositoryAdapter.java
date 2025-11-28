package com.example.tetris.adapter.outbound;

import com.example.tetris.application.port.ScoreRepositoryPort;
import com.example.tetris.domain.Score;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ScoreRepositoryPortの実装クラス（アダプター）。
 *
 * <p>このクラスは、ヘキサゴナルアーキテクチャにおける「アダプター」として、
 * ドメイン層（Score）と永続化層（ScoreEntity）の間のマッピングを担当します。</p>
 *
 * <h3>責務:</h3>
 * <ul>
 *   <li>ScoreドメインモデルとScoreEntityのマッピング</li>
 *   <li>ScoreJpaRepositoryを介したデータベース操作</li>
 *   <li>ドメイン層の永続化要件を満たす</li>
 * </ul>
 *
 * <h3>クリーンアーキテクチャ:</h3>
 * <ul>
 *   <li>ドメイン層はこのクラスの存在を知らない</li>
 *   <li>ドメインモデル（Score）はJPAアノテーションを持たない</li>
 *   <li>永続化の詳細はこの層で隠蔽される</li>
 * </ul>
 *
 * <h3>設計パターン:</h3>
 * <ul>
 *   <li>Repository Pattern（データアクセス抽象化）</li>
 *   <li>Adapter Pattern（ドメインと永続化層の変換）</li>
 *   <li>Dependency Inversion（上位層が下位層に依存しない）</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-28
 * @see ScoreRepositoryPort
 * @see Score
 * @see ScoreEntity
 * @see ScoreJpaRepository
 */
@Component
public class ScoreRepositoryAdapter implements ScoreRepositoryPort {

    private final ScoreJpaRepository scoreJpaRepository;

    /**
     * コンストラクタ。
     *
     * @param scoreJpaRepository ScoreEntityのJPAリポジトリ
     */
    public ScoreRepositoryAdapter(ScoreJpaRepository scoreJpaRepository) {
        this.scoreJpaRepository = scoreJpaRepository;
    }

    /**
     * Scoreドメインモデルをデータベースに保存します。
     *
     * <p>処理フロー:</p>
     * <ol>
     *   <li>ScoreドメインモデルをScoreEntityに変換</li>
     *   <li>ScoreJpaRepositoryで保存（IDが自動採番される）</li>
     *   <li>保存されたScoreEntityをScoreドメインモデルに変換</li>
     *   <li>Scoreドメインモデルを返却</li>
     * </ol>
     *
     * @param score 保存するスコアドメインモデル
     * @return 保存されたスコアドメインモデル（IDが採番済み）
     */
    @Override
    public Score save(Score score) {
        ScoreEntity entity = toEntity(score);
        ScoreEntity savedEntity = scoreJpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    /**
     * 過去10件の高スコアを降順で取得します。
     *
     * <p>処理フロー:</p>
     * <ol>
     *   <li>ScoreJpaRepositoryから上位10件を取得</li>
     *   <li>各ScoreEntityをScoreドメインモデルに変換</li>
     *   <li>Scoreドメインモデルのリストを返却</li>
     * </ol>
     *
     * @return 過去10件のスコアドメインモデルリスト（スコア降順）
     */
    @Override
    public List<Score> findTop10ByOrderByScoreDesc() {
        return scoreJpaRepository.findTop10ByOrderByScoreDescTimestampDesc()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * ScoreドメインモデルをScoreEntityに変換します。
     *
     * <p>マッピングルール:</p>
     * <ul>
     *   <li>id: そのまま変換（新規の場合はnull）</li>
     *   <li>score, level, totalLinesCleared: そのまま変換</li>
     *   <li>timestamp: そのまま変換</li>
     * </ul>
     *
     * @param score Scoreドメインモデル
     * @return ScoreEntity
     */
    private ScoreEntity toEntity(Score score) {
        return new ScoreEntity(
                score.id(),
                score.score(),
                score.level(),
                score.totalLinesCleared(),
                score.timestamp()
        );
    }

    /**
     * ScoreEntityをScoreドメインモデルに変換します。
     *
     * <p>マッピングルール:</p>
     * <ul>
     *   <li>id: そのまま変換（データベースで採番済み）</li>
     *   <li>score, level, totalLinesCleared: そのまま変換</li>
     *   <li>timestamp: そのまま変換</li>
     * </ul>
     *
     * @param entity ScoreEntity
     * @return Scoreドメインモデル
     */
    private Score toDomain(ScoreEntity entity) {
        return Score.of(
                entity.getId(),
                entity.getScore(),
                entity.getLevel(),
                entity.getTotalLinesCleared(),
                entity.getTimestamp()
        );
    }
}
