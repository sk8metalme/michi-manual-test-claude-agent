package com.example.tetris.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link ClearResult}クラスの単体テスト。
 *
 * <p>このテストクラスは、ClearResultレコードの基本的な機能を検証します。</p>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
class ClearResultTest {

    /**
     * ClearResultの生成とフィールド取得が正しく動作することを検証します。
     */
    @Test
    void testClearResultCreation() {
        // Arrange
        GameField emptyField = GameField.createEmpty();
        int clearedLines = 2;

        // Act
        ClearResult result = new ClearResult(emptyField, clearedLines);

        // Assert
        assertNotNull(result, "ClearResultインスタンスはnullであってはならない");
        assertEquals(emptyField, result.updatedField(), "updatedFieldは正しく取得できるべき");
        assertEquals(clearedLines, result.clearedLineCount(), "clearedLineCountは正しく取得できるべき");
    }

    /**
     * 消去ライン数が0のClearResultを生成できることを検証します。
     */
    @Test
    void testClearResultWithNoLinesCleared() {
        // Arrange
        GameField field = GameField.createEmpty();

        // Act
        ClearResult result = new ClearResult(field, 0);

        // Assert
        assertEquals(0, result.clearedLineCount(), "消去ライン数が0のClearResultを生成できるべき");
    }

    /**
     * 消去ライン数が1-4の範囲のClearResultを生成できることを検証します。
     */
    @Test
    void testClearResultWithValidLineCounts() {
        // Arrange
        GameField field = GameField.createEmpty();

        // Act & Assert
        for (int i = 1; i <= 4; i++) {
            ClearResult result = new ClearResult(field, i);
            assertEquals(i, result.clearedLineCount(), i + "ライン消去のClearResultを生成できるべき");
        }
    }

    /**
     * 同じフィールドと消去ライン数を持つClearResultが等価と判定されることを検証します。
     */
    @Test
    void testClearResultEquality() {
        // Arrange
        GameField field1 = GameField.createEmpty();
        ClearResult result1 = new ClearResult(field1, 3);
        ClearResult result2 = new ClearResult(field1, 3);  // 同じインスタンスを使用
        ClearResult result3 = new ClearResult(field1, 1);

        // Act & Assert
        assertEquals(result1, result2, "同じフィールドインスタンスと消去ライン数を持つClearResultは等価であるべき");
        assertNotEquals(result1, result3, "異なる消去ライン数を持つClearResultは等価ではないべき");
    }

    /**
     * ClearResultのハッシュコードが正しく生成されることを検証します。
     */
    @Test
    void testClearResultHashCode() {
        // Arrange
        GameField field1 = GameField.createEmpty();
        ClearResult result1 = new ClearResult(field1, 4);
        ClearResult result2 = new ClearResult(field1, 4);  // 同じインスタンスを使用

        // Act & Assert
        assertEquals(result1.hashCode(), result2.hashCode(),
                "同じフィールドインスタンスと消去ライン数を持つClearResultは同じハッシュコードを持つべき");
    }

    /**
     * ClearResultのtoString()メソッドが有意義な文字列を返すことを検証します。
     */
    @Test
    void testClearResultToString() {
        // Arrange
        GameField field = GameField.createEmpty();
        ClearResult result = new ClearResult(field, 2);

        // Act
        String str = result.toString();

        // Assert
        assertNotNull(str, "toString()はnullを返してはならない");
        assertTrue(str.contains("ClearResult"), "toString()は'ClearResult'という文字列を含むべき");
        assertTrue(str.contains("2"), "toString()は消去ライン数'2'を含むべき");
    }
}
