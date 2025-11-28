package com.example.tetris.application.usecase;

import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.application.mapper.GameStateMapper;
import com.example.tetris.domain.GameState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ProcessAutoDropUseCaseの実装クラス。
 *
 * <p>このクラスは、WebSocketセッションIDに紐づくゲーム状態を管理し、
 * テトリミノの自動落下処理を実行する責務を持ちます。</p>
 *
 * <h3>処理フロー:</h3>
 * <ol>
 *   <li>セッションIDに紐づくGameStateを取得（存在しない場合は初期化）</li>
 *   <li>GameState.processAutoDropTick()を呼び出してテトリミノを自動落下</li>
 *   <li>自動落下処理後のGameStateをセッション管理領域に保存</li>
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
 * <h3>自動落下の動作:</h3>
 * <ul>
 *   <li>移動可能な場合：テトリミノを1マス下に移動したGameStateを返す</li>
 *   <li>移動不可能な場合：ハードドロップを実行
 *     <ul>
 *       <li>テトリミノをフィールドに固定</li>
 *       <li>ライン消去処理を実行</li>
 *       <li>スコアを加算（ScoreCalculatorを使用）</li>
 *       <li>レベルを更新（LevelManagerを使用）</li>
 *       <li>次のテトリミノを生成</li>
 *       <li>ゲームオーバー判定（新規テトリミノが配置不可能な場合）</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-28
 */
public class ProcessAutoDropUseCaseImpl implements ProcessAutoDropUseCase {

    /**
     * セッションIDをキー、GameStateを値とするマップ。
     *
     * <p>複数のWebSocketセッションを同時にサポートするため、
     * スレッドセーフなConcurrentHashMapを使用します。</p>
     */
    private final Map<String, GameState> sessionStates = new ConcurrentHashMap<>();

    /**
     * 自動落下処理を実行します。
     *
     * <p>セッションIDに紐づくGameStateを取得し、存在しない場合は初期化します。
     * その後、GameState.processAutoDropTick()でテトリミノを自動落下し、
     * 処理後のGameStateを保存してDTOに変換して返します。</p>
     *
     * <p>自動落下処理（GameState.processAutoDropTick()）は以下を実行します：</p>
     * <ol>
     *   <li>テトリミノを1マス下に移動を試みる</li>
     *   <li>移動可能な場合：移動後のGameStateを返す</li>
     *   <li>移動不可能な場合：
     *     <ul>
     *       <li>テトリミノをフィールドに固定</li>
     *       <li>ライン消去処理を実行（GameField.clearLines()）</li>
     *       <li>スコアを加算（ScoreCalculatorで計算）</li>
     *       <li>レベルを更新（LevelManagerで計算）</li>
     *       <li>次のテトリミノを生成</li>
     *       <li>ゲームオーバー判定</li>
     *     </ul>
     *   </li>
     * </ol>
     *
     * @param sessionId WebSocketセッションID（非null）
     * @return 自動落下処理後のゲーム状態のDTO
     * @throws NullPointerException sessionIdがnullの場合
     */
    @Override
    public GameStateDTO execute(String sessionId) {
        // 1. null チェック
        if (sessionId == null) {
            throw new NullPointerException("sessionId must not be null");
        }

        // 2. セッションIDに紐づくGameStateを取得（存在しない場合は初期化）
        GameState currentState = sessionStates.computeIfAbsent(sessionId, k -> GameState.initialize());

        // 3. 自動落下処理を実行
        // processAutoDropTick()は以下を内部で実行します：
        // - テトリミノを1マス下に移動を試みる
        // - 移動不可能な場合は固定→ライン消去→スコア加算→レベル更新→次テトリミノ生成
        GameState newState = currentState.processAutoDropTick();

        // 4. 処理後のGameStateをセッション管理領域に保存
        sessionStates.put(sessionId, newState);

        // 5. DTOに変換して返却
        return GameStateMapper.toDTO(newState);
    }
}
