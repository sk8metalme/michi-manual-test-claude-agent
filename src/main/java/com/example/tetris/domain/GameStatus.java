package com.example.tetris.domain;

/**
 * ゲームの進行状態を表す列挙型。
 *
 * <p>テトリスゲームの現在の状態を管理します。</p>
 *
 * <h3>状態の種類：</h3>
 * <ul>
 *   <li>PLAYING: ゲーム進行中</li>
 *   <li>GAME_OVER: ゲームオーバー</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public enum GameStatus {
    /**
     * ゲーム進行中の状態。
     */
    PLAYING,

    /**
     * ゲームオーバーの状態。
     */
    GAME_OVER
}
