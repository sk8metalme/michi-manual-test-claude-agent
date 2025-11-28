package com.example.tetris.domain;

import java.time.LocalDateTime;

/**
 * スコアドメインモデル。
 *
 * <p>このクラスは、テトリスゲームのスコア情報を表すドメインモデルです。
 * 永続化層の実装詳細（JPA、データベース等）から独立しています。</p>
 *
 * <h3>クリーンアーキテクチャ:</h3>
 * <ul>
 *   <li>ドメイン層に配置され、フレームワークに依存しない</li>
 *   <li>ビジネスロジックの中核を表現</li>
 *   <li>アダプター層（ScoreEntity）とのマッピングは外側で実施</li>
 * </ul>
 *
 * @param id スコアID（保存時はnull、取得時は自動採番値）
 * @param score 獲得スコア
 * @param level 到達レベル
 * @param totalLinesCleared 累計クリア済みライン数
 * @param timestamp スコア記録日時
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-28
 */
public record Score(
        Long id,
        int score,
        int level,
        int totalLinesCleared,
        LocalDateTime timestamp
) {
    /**
     * 新規スコアを生成します（IDなし、タイムスタンプ指定あり）。
     *
     * <p>このメソッドは、スコア保存前にドメインモデルを生成する際に使用します。
     * IDは保存時にデータベースで自動採番されるため、nullで生成します。</p>
     *
     * @param score 獲得スコア
     * @param level 到達レベル
     * @param totalLinesCleared 累計クリア済みライン数
     * @param timestamp スコア記録日時（保存時に付与される現在時刻）
     * @return 新規スコアドメインモデル（IDはnull）
     */
    public static Score create(int score, int level, int totalLinesCleared, LocalDateTime timestamp) {
        return new Score(null, score, level, totalLinesCleared, timestamp);
    }

    /**
     * 保存済みスコアを生成します（IDとタイムスタンプあり）。
     *
     * @param id スコアID
     * @param score 獲得スコア
     * @param level 到達レベル
     * @param totalLinesCleared 累計クリア済みライン数
     * @param timestamp スコア記録日時
     * @return 保存済みスコアドメインモデル
     */
    public static Score of(Long id, int score, int level, int totalLinesCleared, LocalDateTime timestamp) {
        return new Score(id, score, level, totalLinesCleared, timestamp);
    }
}
