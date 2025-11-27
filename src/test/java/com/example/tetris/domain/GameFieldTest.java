package com.example.tetris.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link GameField}クラスの単体テスト。
 *
 * <p>このテストクラスは、GameFieldの境界判定、衝突判定、ライン消去ロジックを検証します。</p>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
class GameFieldTest {

    /**
     * 空のGameFieldを生成できることを検証します。
     */
    @Test
    void testCreateEmpty() {
        // Act
        GameField field = GameField.createEmpty();

        // Assert
        assertNotNull(field, "空のGameFieldを生成できるべき");
        assertNotNull(field.grid(), "gridはnullであってはならない");
        assertEquals(GameField.HEIGHT, field.grid().length, "グリッドの高さは20であるべき");
        assertEquals(GameField.WIDTH, field.grid()[0].length, "グリッドの幅は10であるべき");

        // すべてのセルが空（null）であることを確認
        for (int y = 0; y < GameField.HEIGHT; y++) {
            for (int x = 0; x < GameField.WIDTH; x++) {
                assertNull(field.grid()[y][x], "初期状態ではすべてのセルがnull（空）であるべき");
            }
        }
    }

    /**
     * isOccupied() メソッドがフィールド内の空のセルに対してfalseを返すことを検証します。
     */
    @Test
    void testIsOccupied_EmptyCell() {
        // Arrange
        GameField field = GameField.createEmpty();

        // Act & Assert
        assertFalse(field.isOccupied(0, 0), "空のセルは占有されていないべき");
        assertFalse(field.isOccupied(5, 10), "空のセルは占有されていないべき");
        assertFalse(field.isOccupied(9, 19), "空のセルは占有されていないべき");
    }

    /**
     * isOccupied() メソッドがフィールド外の座標に対してfalseを返すことを検証します（境界外チェック）。
     */
    @Test
    void testIsOccupied_OutOfBounds() {
        // Arrange
        GameField field = GameField.createEmpty();

        // Act & Assert - 境界外の座標
        assertFalse(field.isOccupied(-1, 0), "x座標が負の場合は占有されていないとみなすべき");
        assertFalse(field.isOccupied(10, 0), "x座標がWIDTH以上の場合は占有されていないとみなすべき");
        assertFalse(field.isOccupied(0, -1), "y座標が負の場合は占有されていないとみなすべき");
        assertFalse(field.isOccupied(0, 20), "y座標がHEIGHT以上の場合は占有されていないとみなすべき");
    }

    /**
     * isOccupied() メソッドが占有されたセルに対してtrueを返すことを検証します。
     */
    @Test
    void testIsOccupied_OccupiedCell() {
        // Arrange
        GameField field = GameField.createEmpty();
        Block[][] grid = field.grid();
        grid[5][3] = new Block(TetrominoType.I);  // 座標(3, 5)を占有
        GameField fieldWithBlock = new GameField(grid);

        // Act & Assert
        assertTrue(fieldWithBlock.isOccupied(3, 5), "ブロックがあるセルは占有されているべき");
        assertFalse(fieldWithBlock.isOccupied(4, 5), "隣接する空のセルは占有されていないべき");
    }

    /**
     * canPlace() メソッドが空のフィールドにテトリミノを配置可能と判定することを検証します。
     */
    @Test
    void testCanPlace_EmptyField() {
        // Arrange
        GameField field = GameField.createEmpty();
        Tetromino tetromino = new Tetromino(TetrominoType.I, new Position(5, 0), Rotation.DEG_0);

        // Act & Assert
        assertTrue(field.canPlace(tetromino), "空のフィールドにはテトリミノを配置可能であるべき");
    }

    /**
     * canPlace() メソッドがフィールド境界外のテトリミノを配置不可と判定することを検証します。
     */
    @Test
    void testCanPlace_OutOfBounds() {
        // Arrange
        GameField field = GameField.createEmpty();

        // Act & Assert - x座標が負
        Tetromino tetrominoLeft = new Tetromino(TetrominoType.I, new Position(-2, 0), Rotation.DEG_0);
        assertFalse(field.canPlace(tetrominoLeft), "x座標が負のテトリミノは配置不可であるべき");

        // Act & Assert - x座標がフィールド幅を超える
        Tetromino tetrominoRight = new Tetromino(TetrominoType.I, new Position(11, 0), Rotation.DEG_0);
        assertFalse(field.canPlace(tetrominoRight), "x座標がフィールド幅を超えるテトリミノは配置不可であるべき");

        // Act & Assert - y座標がフィールド高さを超える
        Tetromino tetrominoBottom = new Tetromino(TetrominoType.I, new Position(5, 21), Rotation.DEG_0);
        assertFalse(field.canPlace(tetrominoBottom), "y座標がフィールド高さを超えるテトリミノは配置不可であるべき");
    }

    /**
     * canPlace() メソッドが他のブロックと衝突するテトリミノを配置不可と判定することを検証します。
     */
    @Test
    void testCanPlace_CollidesWithExistingBlock() {
        // Arrange
        GameField field = GameField.createEmpty();
        Block[][] grid = field.grid();
        grid[10][5] = new Block(TetrominoType.T);  // 座標(5, 10)を占有
        GameField fieldWithBlock = new GameField(grid);

        // Act & Assert
        Tetromino tetromino = new Tetromino(TetrominoType.O, new Position(5, 9), Rotation.DEG_0);
        assertFalse(fieldWithBlock.canPlace(tetromino), "既存のブロックと衝突するテトリミノは配置不可であるべき");
    }

    /**
     * place() メソッドがテトリミノをフィールドに固定することを検証します。
     */
    @Test
    void testPlace() {
        // Arrange
        GameField field = GameField.createEmpty();
        Tetromino tetromino = new Tetromino(TetrominoType.O, new Position(5, 10), Rotation.DEG_0);

        // Act
        GameField updatedField = field.place(tetromino);

        // Assert
        assertNotNull(updatedField, "place()は新しいGameFieldを返すべき");
        assertNotSame(field, updatedField, "place()は元のGameFieldとは異なる新しいインスタンスを返すべき");

        // テトリミノのブロック位置が占有されていることを確認
        List<Position> blockPositions = tetromino.getBlockPositions();
        for (Position pos : blockPositions) {
            assertTrue(updatedField.isOccupied(pos.x(), pos.y()),
                    "テトリミノのブロック位置 (" + pos.x() + ", " + pos.y() + ") は占有されているべき");
            assertEquals(TetrominoType.O, updatedField.grid()[pos.y()][pos.x()].type(),
                    "ブロックの型はテトリミノの型と一致するべき");
        }
    }

    /**
     * clearLines() メソッドが横一列埋まったラインを検出・消去することを検証します。
     */
    @Test
    void testClearLines_OneLine() {
        // Arrange - 最下行(y=19)を完全に埋める
        GameField field = GameField.createEmpty();
        Block[][] grid = field.grid();
        for (int x = 0; x < GameField.WIDTH; x++) {
            grid[19][x] = new Block(TetrominoType.I);
        }
        GameField fieldWithFullLine = new GameField(grid);

        // Act
        ClearResult result = fieldWithFullLine.clearLines();

        // Assert
        assertEquals(1, result.clearedLineCount(), "1ライン消去されるべき");

        // 最下行がすべて空になっていることを確認
        for (int x = 0; x < GameField.WIDTH; x++) {
            assertNull(result.updatedField().grid()[19][x],
                    "消去された最下行はすべて空（null）であるべき");
        }
    }

    /**
     * clearLines() メソッドが複数ライン同時消去を処理することを検証します。
     */
    @Test
    void testClearLines_MultipleLines() {
        // Arrange - 最下2行(y=18, 19)を完全に埋める
        GameField field = GameField.createEmpty();
        Block[][] grid = field.grid();
        for (int y = 18; y <= 19; y++) {
            for (int x = 0; x < GameField.WIDTH; x++) {
                grid[y][x] = new Block(TetrominoType.T);
            }
        }
        GameField fieldWithFullLines = new GameField(grid);

        // Act
        ClearResult result = fieldWithFullLines.clearLines();

        // Assert
        assertEquals(2, result.clearedLineCount(), "2ライン消去されるべき");
    }

    /**
     * clearLines() メソッドがライン消去後に上部のブロックを落下させることを検証します。
     */
    @Test
    void testClearLines_BlocksFallDown() {
        // Arrange
        GameField field = GameField.createEmpty();
        Block[][] grid = field.grid();

        // y=18にブロックを1つ配置
        grid[18][5] = new Block(TetrominoType.Z);

        // y=19（最下行）を完全に埋める
        for (int x = 0; x < GameField.WIDTH; x++) {
            grid[19][x] = new Block(TetrominoType.I);
        }

        GameField fieldBeforeClear = new GameField(grid);

        // Act
        ClearResult result = fieldBeforeClear.clearLines();

        // Assert
        assertEquals(1, result.clearedLineCount(), "1ライン消去されるべき");

        // y=18にあったブロックがy=19に落下していることを確認
        assertNull(result.updatedField().grid()[18][5], "元の位置(5, 18)は空になっているべき");
        assertNotNull(result.updatedField().grid()[19][5], "ブロックは(5, 19)に落下しているべき");
        assertEquals(TetrominoType.Z, result.updatedField().grid()[19][5].type(),
                "落下したブロックの型は元の型と一致するべき");
    }

    /**
     * clearLines() メソッドがライン消去なしの場合に正しく処理することを検証します。
     */
    @Test
    void testClearLines_NoLinesCleared() {
        // Arrange - 完全に埋まった行がない
        GameField field = GameField.createEmpty();
        Block[][] grid = field.grid();
        grid[19][0] = new Block(TetrominoType.L);  // 1つだけブロックを配置（ライン未完成）
        GameField fieldWithPartialLine = new GameField(grid);

        // Act
        ClearResult result = fieldWithPartialLine.clearLines();

        // Assert
        assertEquals(0, result.clearedLineCount(), "ライン消去なしの場合は0を返すべき");
        assertEquals(fieldWithPartialLine, result.updatedField(),
                "ライン消去なしの場合は元のフィールドと同じインスタンスを返すべき");
    }

    /**
     * clearLines() メソッドが最大4ライン同時消去（テトリス）を処理することを検証します。
     */
    @Test
    void testClearLines_Tetris() {
        // Arrange - 最下4行(y=16-19)を完全に埋める
        GameField field = GameField.createEmpty();
        Block[][] grid = field.grid();
        for (int y = 16; y <= 19; y++) {
            for (int x = 0; x < GameField.WIDTH; x++) {
                grid[y][x] = new Block(TetrominoType.I);
            }
        }
        GameField fieldWith4Lines = new GameField(grid);

        // Act
        ClearResult result = fieldWith4Lines.clearLines();

        // Assert
        assertEquals(4, result.clearedLineCount(), "4ライン同時消去（テトリス）が処理されるべき");

        // 最下4行がすべて空になっていることを確認
        for (int y = 16; y <= 19; y++) {
            for (int x = 0; x < GameField.WIDTH; x++) {
                assertNull(result.updatedField().grid()[y][x],
                        "消去された行(" + x + ", " + y + ")はすべて空（null）であるべき");
            }
        }
    }
}
