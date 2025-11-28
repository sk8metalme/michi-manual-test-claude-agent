package com.example.tetris.application.usecase;

import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.application.mapper.GameStateMapper;
import com.example.tetris.domain.GameState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RotateTetrominoUseCaseの実装クラス。
 *
 * <p>このクラスは、WebSocketセッションIDに紐づくゲーム状態を管理し、
 * テトリミノを時計回りに90度回転する責務を持ちます。</p>
 *
 * <h3>処理フロー:</h3>
 * <ol>
 *   <li>セッションIDに紐づくGameStateを取得（存在しない場合は初期化）</li>
 *   <li>GameState.rotateTetromino()を呼び出してテトリミノを回転</li>
 *   <li>回転後のGameStateをセッション管理領域に保存</li>
 *   <li>GameStateMapperでドメインモデルをDTOに変換</li>
 *   <li>変換されたDTOを呼び出し元に返却</li>
 * </ol>
 *
 * <h3>セッション管理:</h3>
 * <ul>
 *   <li>セッションIDをキー、GameStateを値とするMapで管理</li>
 *   <li>スレッドセーフ性を確保するためConcurrentHashMapを使用</li>
 *   <li>セッションが存在しない場合は自動的にGameState.initialize()を実行</li>
 * </ul>
 *
 * <h3>衝突判定:</h3>
 * <ul>
 *   <li>回転可能な場合：GameStateは回転後の向きのテトリミノを返す</li>
 *   <li>回転不可能な場合：GameStateは元の状態をそのまま返す（回転無効化）</li>
 *   <li>衝突判定ロジックはGameStateドメインモデルに委譲</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public class RotateTetrominoUseCaseImpl implements RotateTetrominoUseCase {

    /**
     * セッションIDをキー、GameStateを値とするマップ。
     *
     * <p>複数のWebSocketセッションを同時にサポートするため、
     * スレッドセーフなConcurrentHashMapを使用します。</p>
     */
    private final Map<String, GameState> sessionStates = new ConcurrentHashMap<>();

    /**
     * セッションIDの最大長（文字数）。
     */
    private static final int MAX_SESSION_ID_LENGTH = 256;

    /**
     * セッションの最大数。
     */
    private static final int MAX_SESSIONS = 10000;

    /**
     * テトリミノを時計回りに90度回転します。
     *
     * <p>セッションIDに紐づくGameStateを取得し、存在しない場合は初期化します。
     * その後、GameState.rotateTetromino()でテトリミノを回転し、
     * 回転後のGameStateを保存してDTOに変換して返します。</p>
     *
     * @param sessionId WebSocketセッションID（非null）
     * @return 回転後のゲーム状態のDTO
     * @throws NullPointerException sessionIdがnullの場合
     */
    @Override
    public GameStateDTO execute(String sessionId) {
        // 1. sessionIdのバリデーション
        validateSessionId(sessionId);

        // 2. セッションIDに紐づくGameStateを取得（存在しない場合は初期化）
        // computeIfAbsent内でセッション数チェックを行うことで、アトミックな操作を実現
        GameState currentState = sessionStates.computeIfAbsent(sessionId, k -> {
            // 新規セッション作成時のみサイズチェック
            if (sessionStates.size() >= MAX_SESSIONS) {
                throw new IllegalStateException("Maximum session limit reached: " + MAX_SESSIONS);
            }
            return GameState.initialize();
        });

        // 3. テトリミノを回転（衝突判定結果は GameState.rotateTetromino() 内でハンドリング）
        GameState newState = currentState.rotateTetromino();

        // 4. 回転後のGameStateをセッション管理領域に保存
        sessionStates.put(sessionId, newState);

        // 5. DTOに変換して返却
        return GameStateMapper.toDTO(newState);
    }

    @Override
    public void removeSession(String sessionId) {
        if (sessionId == null) {
            throw new NullPointerException("sessionId must not be null");
        }
        sessionStates.remove(sessionId);
    }

    /**
     * sessionIdのバリデーションを行います。
     *
     * @param sessionId 検証するセッションID
     * @throws NullPointerException sessionIdがnullの場合
     * @throws IllegalArgumentException sessionIdが空文字列、または最大長を超える場合
     */
    private void validateSessionId(String sessionId) {
        if (sessionId == null) {
            throw new NullPointerException("sessionId must not be null");
        }
        if (sessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("sessionId must not be empty");
        }
        if (sessionId.length() > MAX_SESSION_ID_LENGTH) {
            throw new IllegalArgumentException(
                    "sessionId exceeds maximum length (" + MAX_SESSION_ID_LENGTH + "): " + sessionId.length());
        }
    }
}
