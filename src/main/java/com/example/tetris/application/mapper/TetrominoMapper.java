package com.example.tetris.application.mapper;

import com.example.tetris.application.dto.TetrominoDTO;
import com.example.tetris.domain.Rotation;
import com.example.tetris.domain.Tetromino;

/**
 * Tetrominoドメインオブジェクトと TetrominoDTO の変換を行うマッパークラス。
 *
 * <p>このクラスは、ドメイン層のTetrominoをアプリケーション層のDTOに変換します。
 * すべてのメソッドはstaticであり、状態を持ちません。</p>
 *
 * <h3>変換仕様：</h3>
 * <ul>
 *   <li>type: TetrominoType → String (enum名)</li>
 *   <li>x: Position.x() → int</li>
 *   <li>y: Position.y() → int</li>
 *   <li>rotation: Rotation → int (度数: 0, 90, 180, 270)</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public final class TetrominoMapper {

    /**
     * ユーティリティクラスのため、インスタンス化を禁止します。
     */
    private TetrominoMapper() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * TetrominoドメインオブジェクトをTetrominoDTOに変換します。
     *
     * @param tetromino 変換元のTetromino
     * @return 変換されたTetrominoDTO
     * @throws NullPointerException tetrominoがnullの場合
     */
    public static TetrominoDTO toDTO(Tetromino tetromino) {
        if (tetromino == null) {
            throw new NullPointerException("tetromino must not be null");
        }

        return new TetrominoDTO(
                tetromino.type().name(),
                tetromino.position().x(),
                tetromino.position().y(),
                rotationToDegrees(tetromino.rotation())
        );
    }

    /**
     * Rotation列挙型を度数（int）に変換します。
     *
     * @param rotation 変換元のRotation
     * @return 度数（0, 90, 180, 270）
     */
    private static int rotationToDegrees(Rotation rotation) {
        return switch (rotation) {
            case DEG_0 -> 0;
            case DEG_90 -> 90;
            case DEG_180 -> 180;
            case DEG_270 -> 270;
        };
    }
}
