package com.example.tetris.application.usecase;

import com.example.tetris.adapter.outbound.ScoreEntity;
import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.application.dto.ScoreDTO;
import com.example.tetris.application.port.ScoreRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * SaveScoreUseCaseの実装クラス。
 *
 * <p>このクラスは、ゲームオーバー時に最終スコアをデータベースに保存する責務を持ちます。</p>
 *
 * <h3>処理フロー:</h3>
 * <ol>
 *   <li>GameStateDTOからスコア、レベル、消去ライン数を抽出</li>
 *   <li>現在時刻をタイムスタンプとして付与</li>
 *   <li>ScoreEntityを生成</li>
 *   <li>ScoreRepositoryPortを介してデータベースに保存</li>
 *   <li>保存結果をScoreDTOに変換して返却</li>
 * </ol>
 *
 * <h3>要件充足:</h3>
 * <ul>
 *   <li>要件7.1: ゲームオーバー時に最終スコア、レベル、消去ライン数を保存</li>
 *   <li>要件7.2: スコア保存時にタイムスタンプを付与</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-28
 */
public class SaveScoreUseCaseImpl implements SaveScoreUseCase {

    private final ScoreRepositoryPort scoreRepositoryPort;

    /**
     * コンストラクタ。
     *
     * @param scoreRepositoryPort スコアリポジトリのポート
     */
    public SaveScoreUseCaseImpl(ScoreRepositoryPort scoreRepositoryPort) {
        this.scoreRepositoryPort = scoreRepositoryPort;
    }

    /**
     * ゲーム状態からスコアを保存します。
     *
     * <p>このメソッドは{@code @Transactional}により、
     * データベーストランザクション内で実行されます。</p>
     *
     * @param gameStateDTO ゲーム状態DTO（スコア、レベル、消去ライン数を含む）
     * @return 保存されたスコアDTO（IDとタイムスタンプが付与済み）
     */
    @Override
    @Transactional
    public ScoreDTO saveScore(GameStateDTO gameStateDTO) {
        // 1. タイムスタンプを付与（要件7.2）
        LocalDateTime timestamp = LocalDateTime.now();

        // 2. ScoreEntityを生成（要件7.1）
        ScoreEntity scoreEntity = new ScoreEntity(
                null,  // IDは自動採番
                gameStateDTO.score(),
                gameStateDTO.level(),
                gameStateDTO.totalLinesCleared(),
                timestamp
        );

        // 3. リポジトリに保存
        ScoreEntity savedEntity = scoreRepositoryPort.save(scoreEntity);

        // 4. ScoreDTOに変換して返却
        return new ScoreDTO(
                savedEntity.getId(),
                savedEntity.getScore(),
                savedEntity.getLevel(),
                savedEntity.getTotalLinesCleared(),
                savedEntity.getTimestamp()
        );
    }
}
