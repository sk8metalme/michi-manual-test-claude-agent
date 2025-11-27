package com.example.tetris.application.mapper;

import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.application.dto.TetrominoDTO;
import com.example.tetris.domain.Block;
import com.example.tetris.domain.GameField;
import com.example.tetris.domain.GameState;

/**
 * GameStateドメインオブジェクトと GameStateDTO の変換を行うマッパークラス。
 *
 * <p>このクラスは、ドメイン層のGameStateをアプリケーション層のDTOに変換します。
 * すべてのメソッドはstaticであり、状態を持ちません。</p>
 *
 * <h3>変換仕様：</h3>
 * <ul>
 *   <li>status: GameStatus → GameStatus (そのまま)</li>
 *   <li>currentTetromino: Tetromino → TetrominoDTO (TetrominoMapperを使用)</li>
 *   <li>nextTetromino: Tetromino → TetrominoDTO (TetrominoMapperを使用)</li>
 *   <li>field: GameField → String[][] (Block.type().name() or null)</li>
 *   <li>score: int → int (そのまま)</li>
 *   <li>level: int → int (そのまま)</li>
 *   <li>totalLinesCleared: int → int (そのまま)</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public final class GameStateMapper {

    /**
     * ユーティリティクラスのため、インスタンス化を禁止します。
     */
    private GameStateMapper() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * GameStateドメインオブジェクトをGameStateDTOに変換します。
     *
     * @param gameState 変換元のGameState
     * @return 変換されたGameStateDTO
     * @throws NullPointerException gameStateがnullの場合
     */
    public static GameStateDTO toDTO(GameState gameState) {
        if (gameState == null) {
            throw new NullPointerException("gameState must not be null");
        }

        // TetrominoをDTOに変換
        TetrominoDTO currentTetrominoDTO = TetrominoMapper.toDTO(gameState.currentTetromino());
        TetrominoDTO nextTetrominoDTO = TetrominoMapper.toDTO(gameState.nextTetromino());

        // GameFieldをString[][]に変換
        String[][] fieldArray = convertFieldToArray(gameState.field());

        return new GameStateDTO(
                gameState.status(),
                currentTetrominoDTO,
                nextTetrominoDTO,
                fieldArray,
                gameState.score(),
                gameState.level(),
                gameState.totalLinesCleared()
        );
    }

    /**
     * GameFieldをString[][]に変換します。
     *
     * <p>変換仕様：</p>
     * <ul>
     *   <li>Block → TetrominoType.name() (例: "I", "O", "T")</li>
     *   <li>null → null (空のセル)</li>
     * </ul>
     *
     * @param field 変換元のGameField
     * @return 変換されたString[][] (20行×10列)
     */
    private static String[][] convertFieldToArray(GameField field) {
        Block[][] grid = field.grid();
        String[][] result = new String[GameField.HEIGHT][GameField.WIDTH];

        for (int y = 0; y < GameField.HEIGHT; y++) {
            for (int x = 0; x < GameField.WIDTH; x++) {
                Block block = grid[y][x];
                result[y][x] = (block != null) ? block.type().name() : null;
            }
        }

        return result;
    }
}
