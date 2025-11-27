package com.example.tetris.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link GameStatus}列挙型の単体テスト。
 *
 * <p>このテストクラスは、GameStatus列挙型の基本的な機能を検証します。</p>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
class GameStatusTest {

    /**
     * GameStatus列挙型が期待される値を持つことを検証します。
     */
    @Test
    void testGameStatusValues() {
        // Assert
        assertEquals(2, GameStatus.values().length, "GameStatusは2つの値を持つべき");
        assertNotNull(GameStatus.valueOf("PLAYING"), "PLAYING値が存在するべき");
        assertNotNull(GameStatus.valueOf("GAME_OVER"), "GAME_OVER値が存在するべき");
    }

    /**
     * PLAYING状態が正しく取得できることを検証します。
     */
    @Test
    void testPlayingStatus() {
        // Act
        GameStatus status = GameStatus.PLAYING;

        // Assert
        assertNotNull(status, "PLAYING状態はnullであってはならない");
        assertEquals("PLAYING", status.name(), "PLAYING状態の名前は'PLAYING'であるべき");
    }

    /**
     * GAME_OVER状態が正しく取得できることを検証します。
     */
    @Test
    void testGameOverStatus() {
        // Act
        GameStatus status = GameStatus.GAME_OVER;

        // Assert
        assertNotNull(status, "GAME_OVER状態はnullであってはならない");
        assertEquals("GAME_OVER", status.name(), "GAME_OVER状態の名前は'GAME_OVER'であるべき");
    }
}
