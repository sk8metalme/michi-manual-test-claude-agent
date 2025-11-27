package com.example.tetris.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link Direction}列挙型の単体テスト。
 *
 * <p>このテストクラスは、Direction列挙型の基本的な機能を検証します。</p>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
class DirectionTest {

    /**
     * Direction列挙型が期待される値を持つことを検証します。
     */
    @Test
    void testDirectionValues() {
        // Assert
        assertEquals(3, Direction.values().length, "Directionは3つの値を持つべき");
        assertNotNull(Direction.valueOf("LEFT"), "LEFT値が存在するべき");
        assertNotNull(Direction.valueOf("RIGHT"), "RIGHT値が存在するべき");
        assertNotNull(Direction.valueOf("DOWN"), "DOWN値が存在するべき");
    }

    /**
     * LEFT方向が正しく取得できることを検証します。
     */
    @Test
    void testLeftDirection() {
        // Act
        Direction direction = Direction.LEFT;

        // Assert
        assertNotNull(direction, "LEFT方向はnullであってはならない");
        assertEquals("LEFT", direction.name(), "LEFT方向の名前は'LEFT'であるべき");
    }

    /**
     * RIGHT方向が正しく取得できることを検証します。
     */
    @Test
    void testRightDirection() {
        // Act
        Direction direction = Direction.RIGHT;

        // Assert
        assertNotNull(direction, "RIGHT方向はnullであってはならない");
        assertEquals("RIGHT", direction.name(), "RIGHT方向の名前は'RIGHT'であるべき");
    }

    /**
     * DOWN方向が正しく取得できることを検証します。
     */
    @Test
    void testDownDirection() {
        // Act
        Direction direction = Direction.DOWN;

        // Assert
        assertNotNull(direction, "DOWN方向はnullであってはならない");
        assertEquals("DOWN", direction.name(), "DOWN方向の名前は'DOWN'であるべき");
    }
}
