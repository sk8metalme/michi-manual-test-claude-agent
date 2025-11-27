package com.example.tetris.domain;

/**
 * レベル管理を行うPure Functionクラス。
 *
 * <p>このクラスはレベル計算と落下間隔計算のロジックを提供する純粋関数です。</p>
 *
 * <h3>レベル計算ルール:</h3>
 * <ul>
 *   <li>10ラインクリアごとにレベル+1</li>
 *   <li>最大レベル: 10</li>
 *   <li>初期レベル: 1</li>
 * </ul>
 *
 * <h3>落下間隔計算ルール:</h3>
 * <ul>
 *   <li>レベル1: 1000ms(1秒)</li>
 *   <li>レベルが上がるごとに100ms減少(10%増速)</li>
 *   <li>最小間隔: 100ms(レベル10以上)</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-27
 */
public final class LevelManager {

    /**
     * 最大レベル。
     */
    private static final int MAX_LEVEL = 10;

    /**
     * 基準落下間隔(ミリ秒)。レベル1の落下間隔。
     */
    private static final int BASE_DROP_INTERVAL_MS = 1000;

    /**
     * レベルごとの落下間隔減少量(ミリ秒)。
     */
    private static final int DECREASE_PER_LEVEL_MS = 100;

    /**
     * 最小落下間隔(ミリ秒)。
     */
    private static final int MIN_DROP_INTERVAL_MS = 100;

    /**
     * ユーティリティクラスのため、インスタンス化を禁止。
     */
    private LevelManager() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * クリア済みライン数からレベルを計算します。
     *
     * <p>10ラインごとにレベル+1、最大レベル10となります。</p>
     *
     * @param totalLinesCleared クリア済みライン数
     * @return 現在のレベル(1-10)
     * @throws IllegalArgumentException クリア済みライン数が負の値の場合
     */
    public static int calculateLevel(int totalLinesCleared) {
        if (totalLinesCleared < 0) {
            throw new IllegalArgumentException("totalLinesCleared must be non-negative");
        }

        // 10ラインごとにレベル+1、初期レベルは1
        int level = (totalLinesCleared / 10) + 1;

        // 最大レベル10に制限
        return Math.min(level, MAX_LEVEL);
    }

    /**
     * レベルに応じた落下間隔(ミリ秒)を計算します。
     *
     * <p>レベルが上がるにつれて落下間隔が短くなり、ゲームが難しくなります。</p>
     * <ul>
     *   <li>レベル1: 1000ms</li>
     *   <li>レベル2: 900ms</li>
     *   <li>レベル3: 800ms</li>
     *   <li>...</li>
     *   <li>レベル10以上: 100ms</li>
     * </ul>
     *
     * @param level 現在のレベル
     * @return 落下間隔(ミリ秒)
     * @throws IllegalArgumentException レベルが1未満の場合
     */
    public static int calculateDropInterval(int level) {
        if (level < 1) {
            throw new IllegalArgumentException("level must be at least 1");
        }

        // レベル1: 1000ms, レベル10: 100ms
        int interval = BASE_DROP_INTERVAL_MS - (level - 1) * DECREASE_PER_LEVEL_MS;

        // 最小間隔100msに制限
        return Math.max(interval, MIN_DROP_INTERVAL_MS);
    }
}
