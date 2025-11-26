package com.example.tetris.adapter.outbound;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * スコア情報を保持するJPAエンティティ
 * scoresテーブルにマッピングされる
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
