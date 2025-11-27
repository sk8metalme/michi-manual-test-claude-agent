package com.example.tetris.domain;

/**
 * スコア計算を行うPure Functionクラス。
 *
 * <p>このクラスはスコア計算ロジックを提供する純粋関数です。</p>
 *
 * <h3>スコア計算ルール:</h3>
 * <ul>
 *   <li>0行消去: 0点</li>
 *   <li>1行消去: 100点</li>
 *   <li>2行消去: 300点</li>
 *   <li>3行消去: 500点</li>
 *   <li>4行消去(テトリス): 800点</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public final class ScoreCalculator {

    /**
     * ユーティリティクラスのため、インスタンス化を禁止。
     */
    private ScoreCalculator() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * 消去ライン数に応じてスコアを計算します。
     *
     * <p>Pattern Matchingを使用して、消去ライン数別の加算値を返却します。</p>
     *
     * @param clearedLines 消去されたライン数
     * @return 獲得スコア
     * @throws IllegalArgumentException 消去ライン数が負の値の場合
     */
    public static int calculateScore(int clearedLines) {
        if (clearedLines < 0) {
            throw new IllegalArgumentException("clearedLines must be non-negative");
        }

        return switch (clearedLines) {
            case 0 -> 0;
            case 1 -> 100;
            case 2 -> 300;
            case 3 -> 500;
            case 4 -> 800;
            default -> clearedLines * 100;  // 5行以上は1行100点で計算
        };
    }
}
