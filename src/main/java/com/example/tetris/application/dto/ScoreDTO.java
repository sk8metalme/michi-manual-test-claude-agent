package com.example.tetris.application.dto;

import java.time.LocalDateTime;

/**
 * スコア情報を表すDTO(Data Transfer Object)。
 *
 * <p>スコア保存と取得のためのデータ転送オブジェクトです。
 * データベースのScoreEntityとアプリケーション層の境界でデータを転送します。</p>
 *
 * <h3>フィールド:</h3>
 * <ul>
 *   <li>id: スコアID（保存時はnull、取得時は自動採番値）</li>
 *   <li>score: 獲得スコア</li>
 *   <li>level: 到達レベル</li>
 *   <li>totalLinesCleared: 累計クリア済みライン数</li>
 *   <li>timestamp: スコア記録日時（保存時は自動付与）</li>
 * </ul>
 *
 * @param id スコアID
 * @param score 獲得スコア
 * @param level 到達レベル
 * @param totalLinesCleared 累計クリア済みライン数
 * @param timestamp スコア記録日時
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-28
 */
public record ScoreDTO(
        Long id,
        int score,
        int level,
        int totalLinesCleared,
        LocalDateTime timestamp
) {
}
