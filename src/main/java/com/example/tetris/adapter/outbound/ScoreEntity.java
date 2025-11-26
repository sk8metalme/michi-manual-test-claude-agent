package com.example.tetris.adapter.outbound;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * スコア情報を保持するJPAエンティティ。
 *
 * <p>Tetrisゲームのプレイ結果を永続化するためのエンティティクラスです。
 * データベースの{@code scores}テーブルにマッピングされます。</p>
 *
 * <p>主な機能：</p>
 * <ul>
 *   <li>スコアランキングの保存と取得</li>
 *   <li>スコア降順インデックス（{@code idx_score_desc}）による高速検索</li>
 *   <li>ゲーム統計情報（レベル、消去ライン数）の記録</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-26
 * @see jakarta.persistence.Entity
 * @see jakarta.persistence.Table
 */
@Entity
@Table(name = "scores", indexes = {
    @Index(name = "idx_score_desc", columnList = "score DESC")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreEntity {

    /**
     * スコアID（自動生成）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 獲得スコア
     */
    @Column(nullable = false)
    private int score;

    /**
     * 到達レベル
     */
    @Column(nullable = false)
    private int level;

    /**
     * 消去した累計ライン数
     */
    @Column(nullable = false)
    private int totalLinesCleared;

    /**
     * スコア記録日時
     */
    @Column(nullable = false)
    private LocalDateTime timestamp;
}
