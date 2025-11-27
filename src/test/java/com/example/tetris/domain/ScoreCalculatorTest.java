package com.example.tetris.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ScoreCalculatorの単体テスト。
 *
 * <p>スコア計算ロジックの正確性を検証します。</p>
 *
 * <h3>テストケース:</h3>
 * <ul>
 *   <li>0行消去: 0点</li>
 *   <li>1行消去: 100点</li>
 *   <li>2行消去: 300点</li>
 *   <li>3行消去: 500点</li>
 *   <li>4行消去: 800点</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
class ScoreCalculatorTest {

    @Test
    void testCalculateScore_ZeroLines_ReturnsZero() {
        // Arrange
        int clearedLines = 0;

        // Act
        int score = ScoreCalculator.calculateScore(clearedLines);

        // Assert
        assertEquals(0, score, "0行消去時は0点であるべき");
    }

    @Test
    void testCalculateScore_OneLine_Returns100() {
        // Arrange
        int clearedLines = 1;

        // Act
        int score = ScoreCalculator.calculateScore(clearedLines);

        // Assert
        assertEquals(100, score, "1行消去時は100点であるべき");
    }

    @Test
    void testCalculateScore_TwoLines_Returns300() {
        // Arrange
        int clearedLines = 2;

        // Act
        int score = ScoreCalculator.calculateScore(clearedLines);

        // Assert
        assertEquals(300, score, "2行消去時は300点であるべき");
    }

    @Test
    void testCalculateScore_ThreeLines_Returns500() {
        // Arrange
        int clearedLines = 3;

        // Act
        int score = ScoreCalculator.calculateScore(clearedLines);

        // Assert
        assertEquals(500, score, "3行消去時は500点であるべき");
    }

    @Test
    void testCalculateScore_FourLines_Returns800() {
        // Arrange
        int clearedLines = 4;

        // Act
        int score = ScoreCalculator.calculateScore(clearedLines);

        // Assert
        assertEquals(800, score, "4行消去時は800点であるべき(テトリス)");
    }

    @Test
    void testCalculateScore_NegativeLines_ThrowsException() {
        // Arrange
        int clearedLines = -1;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> ScoreCalculator.calculateScore(clearedLines),
                "負の値は IllegalArgumentException をスローすべき");
    }
}
