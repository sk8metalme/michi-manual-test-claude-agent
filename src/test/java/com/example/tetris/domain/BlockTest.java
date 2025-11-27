package com.example.tetris.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link Block}クラスの単体テスト。
 *
 * <p>このテストクラスは、Blockレコードの基本的な機能を検証します。</p>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
class BlockTest {

    /**
     * Blockの生成とtype取得が正しく動作することを検証します。
     */
    @Test
    void testBlockCreation() {
        // Arrange & Act
        Block block = new Block(TetrominoType.I);

        // Assert
        assertNotNull(block, "Blockインスタンスはnullであってはならない");
        assertEquals(TetrominoType.I, block.type(), "Blockのtypeは正しく取得できるべき");
    }

    /**
     * 同じTypeを持つBlockが等価と判定されることを検証します。
     *
     * <p>Recordの自動生成されたequals()メソッドの動作を確認します。</p>
     */
    @Test
    void testBlockEquality() {
        // Arrange
        Block block1 = new Block(TetrominoType.T);
        Block block2 = new Block(TetrominoType.T);
        Block block3 = new Block(TetrominoType.O);

        // Act & Assert
        assertEquals(block1, block2, "同じTypeを持つBlockは等価であるべき");
        assertNotEquals(block1, block3, "異なるTypeを持つBlockは等価ではないべき");
    }

    /**
     * Blockのハッシュコードが正しく生成されることを検証します。
     *
     * <p>Recordの自動生成されたhashCode()メソッドの動作を確認します。</p>
     */
    @Test
    void testBlockHashCode() {
        // Arrange
        Block block1 = new Block(TetrominoType.S);
        Block block2 = new Block(TetrominoType.S);

        // Act & Assert
        assertEquals(block1.hashCode(), block2.hashCode(),
                "同じTypeを持つBlockは同じハッシュコードを持つべき");
    }

    /**
     * BlockのtoString()メソッドが有意義な文字列を返すことを検証します。
     *
     * <p>Recordの自動生成されたtoString()メソッドの動作を確認します。</p>
     */
    @Test
    void testBlockToString() {
        // Arrange
        Block block = new Block(TetrominoType.Z);

        // Act
        String result = block.toString();

        // Assert
        assertNotNull(result, "toString()はnullを返してはならない");
        assertTrue(result.contains("Block"), "toString()は'Block'という文字列を含むべき");
        assertTrue(result.contains("Z"), "toString()はテトリミノタイプ'Z'を含むべき");
    }

    /**
     * すべてのTetrominoType でBlockを生成できることを検証します。
     */
    @Test
    void testBlockWithAllTypes() {
        // Arrange & Act & Assert
        for (TetrominoType type : TetrominoType.values()) {
            Block block = new Block(type);
            assertNotNull(block, type + "型のBlockが生成できるべき");
            assertEquals(type, block.type(), type + "型のBlockのtypeは正しく取得できるべき");
        }
    }
}
