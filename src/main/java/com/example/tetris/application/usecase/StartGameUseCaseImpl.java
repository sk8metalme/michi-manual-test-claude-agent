package com.example.tetris.application.usecase;

import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.application.mapper.GameStateMapper;
import com.example.tetris.domain.GameState;
import org.springframework.stereotype.Component;

/**
 * StartGameUseCaseの実装クラス。
 *
 * <p>このクラスは、新しいテトリスゲームを初期化し、
 * 初期状態のGameStateDTOを返す責務を持ちます。</p>
 *
 * <h3>処理フロー:</h3>
 * <ol>
 *   <li>GameState.initialize()を呼び出してドメインモデルを初期化</li>
 *   <li>GameStateMapperでドメインモデルをDTOに変換</li>
 *   <li>変換されたDTOを呼び出し元に返却</li>
 * </ol>
 *
 * <h3>初期状態の仕様:</h3>
 * <ul>
 *   <li>status: PLAYING</li>
 *   <li>currentTetromino: ランダムに生成</li>
 *   <li>nextTetromino: ランダムに生成</li>
 *   <li>field: 空（20行×10列、すべてnull）</li>
 *   <li>score: 0</li>
 *   <li>level: 1</li>
 *   <li>totalLinesCleared: 0</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
@Component
public class StartGameUseCaseImpl implements StartGameUseCase {

    /**
     * 新しいゲームを開始します。
     *
     * <p>GameState.initialize()でドメインモデルを初期化し、
     * GameStateMapperでDTOに変換して返します。</p>
     *
     * @return 初期化されたゲーム状態のDTO
     */
    @Override
    public GameStateDTO startGame() {
        // 1. ドメインモデルの初期化
        GameState gameState = GameState.initialize();

        // 2. DTOに変換して返却
        return GameStateMapper.toDTO(gameState);
    }
}
