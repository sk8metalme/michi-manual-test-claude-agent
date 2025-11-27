package com.example.tetris.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LevelManagerの単体テスト。
 *
 * <p>レベル計算と落下間隔計算のロジックを検証します。</p>
 *
 * <h3>テストケース:</h3>
 * <ul>
 *   <li>レベル計算: 10ラインごとにレベル+1、最大レベル10</li>
 *   <li>落下間隔計算: レベルごとに10%増速、初期1秒(1000ms)</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
class LevelManagerTest {

    // ========================================
    // レベル計算のテスト
    // ========================================

    @Test
    void testCalculateLevel_ZeroLines_ReturnsLevel1() {
        // Arrange
        int totalLinesCleared = 0;

        // Act
        int level = LevelManager.calculateLevel(totalLinesCleared);

        // Assert
        assertEquals(1, level, "0行クリア時はレベル1であるべき");
    }

    @Test
    void testCalculateLevel_NineLines_ReturnsLevel1() {
        // Arrange
        int totalLinesCleared = 9;

        // Act
        int level = LevelManager.calculateLevel(totalLinesCleared);

        // Assert
        assertEquals(1, level, "9行クリア時はレベル1であるべき");
    }

    @Test
    void testCalculateLevel_TenLines_ReturnsLevel2() {
        // Arrange
        int totalLinesCleared = 10;

        // Act
        int level = LevelManager.calculateLevel(totalLinesCleared);

        // Assert
        assertEquals(2, level, "10行クリア時はレベル2であるべき");
    }

    @Test
    void testCalculateLevel_NineteenLines_ReturnsLevel2() {
        // Arrange
        int totalLinesCleared = 19;

        // Act
        int level = LevelManager.calculateLevel(totalLinesCleared);

        // Assert
        assertEquals(2, level, "19行クリア時はレベル2であるべき");
    }

    @Test
    void testCalculateLevel_TwentyLines_ReturnsLevel3() {
        // Arrange
        int totalLinesCleared = 20;

        // Act
        int level = LevelManager.calculateLevel(totalLinesCleared);

        // Assert
        assertEquals(3, level, "20行クリア時はレベル3であるべき");
    }

    @Test
    void testCalculateLevel_NinetyLines_ReturnsLevel10() {
        // Arrange
        int totalLinesCleared = 90;

        // Act
        int level = LevelManager.calculateLevel(totalLinesCleared);

        // Assert
        assertEquals(10, level, "90行クリア時はレベル10であるべき");
    }

    @Test
    void testCalculateLevel_OverHundredLines_ReturnsMaxLevel10() {
        // Arrange
        int totalLinesCleared = 150;

        // Act
        int level = LevelManager.calculateLevel(totalLinesCleared);

        // Assert
        assertEquals(10, level, "100行以上クリア時でも最大レベル10であるべき");
    }

    @Test
    void testCalculateLevel_NegativeLines_ThrowsException() {
        // Arrange
        int totalLinesCleared = -1;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> LevelManager.calculateLevel(totalLinesCleared),
                "負の値はIllegalArgumentExceptionをスローすべき");
    }

    // ========================================
    // 落下間隔計算のテスト
    // ========================================

    @Test
    void testCalculateDropInterval_Level1_Returns1000ms() {
        // Arrange
        int level = 1;

        // Act
        int interval = LevelManager.calculateDropInterval(level);

        // Assert
        assertEquals(1000, interval, "レベル1の落下間隔は1000msであるべき");
    }

    @Test
    void testCalculateDropInterval_Level2_Returns900ms() {
        // Arrange
        int level = 2;

        // Act
        int interval = LevelManager.calculateDropInterval(level);

        // Assert
        assertEquals(900, interval, "レベル2の落下間隔は900ms(10%増速)であるべき");
    }

    @Test
    void testCalculateDropInterval_Level3_Returns800ms() {
        // Arrange
        int level = 3;

        // Act
        int interval = LevelManager.calculateDropInterval(level);

        // Assert
        assertEquals(800, interval, "レベル3の落下間隔は800ms(20%増速)であるべき");
    }

    @Test
    void testCalculateDropInterval_Level10_Returns100ms() {
        // Arrange
        int level = 10;

        // Act
        int interval = LevelManager.calculateDropInterval(level);

        // Assert
        assertEquals(100, interval, "レベル10の落下間隔は100ms(最速)であるべき");
    }

    @Test
    void testCalculateDropInterval_LevelAbove10_Returns100ms() {
        // Arrange
        int level = 15;

        // Act
        int interval = LevelManager.calculateDropInterval(level);

        // Assert
        assertEquals(100, interval, "レベル10以上でも最小間隔100msであるべき");
    }

    @Test
    void testCalculateDropInterval_Level0_ThrowsException() {
        // Arrange
        int level = 0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> LevelManager.calculateDropInterval(level),
                "レベル0以下はIllegalArgumentExceptionをスローすべき");
    }

    @Test
    void testCalculateDropInterval_NegativeLevel_ThrowsException() {
        // Arrange
        int level = -1;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> LevelManager.calculateDropInterval(level),
                "負のレベルはIllegalArgumentExceptionをスローすべき");
    }
}
