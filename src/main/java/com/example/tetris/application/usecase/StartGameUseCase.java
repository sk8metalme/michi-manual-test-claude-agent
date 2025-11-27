package com.example.tetris.application.usecase;

import com.example.tetris.application.dto.GameStateDTO;

/**
 * ゲーム開始ユースケースのインターフェース。
 *
 * <p>このユースケースは、新しいテトリスゲームを初期化し、
 * 初期状態のGameStateDTOを返します。</p>
 *
 * <h3>責務:</h3>
 * <ul>
 *   <li>GameState.initialize()を呼び出してドメインモデルを初期化</li>
 *   <li>ドメインモデルをGameStateDTOに変換</li>
 *   <li>初期状態をクライアントに返却</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public interface StartGameUseCase {

    /**
     * 新しいゲームを開始します。
     *
     * <p>このメソッドは以下の処理を実行します：</p>
     * <ol>
     *   <li>GameState.initialize()でゲーム状態を初期化</li>
     *   <li>初期化されたGameStateをGameStateDTOに変換</li>
     *   <li>DTOをクライアントに返却</li>
     * </ol>
     *
     * @return 初期化されたゲーム状態のDTO
     */
    GameStateDTO startGame();
}
