package com.example.tetris.application.usecase;

import com.example.tetris.application.dto.GameStateDTO;
import com.example.tetris.application.dto.ScoreDTO;
import com.example.tetris.application.port.ScoreRepositoryPort;
import com.example.tetris.domain.Score;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * SaveScoreUseCaseの実装クラス。
 *
 * <p>このクラスは、ゲームオーバー時に最終スコアをデータベースに保存する責務を持ちます。</p>
 *
 * <h3>処理フロー:</h3>
 * <ol>
 *   <li>GameStateDTOからスコア、レベル、消去ライン数を抽出</li>
 *   <li>現在時刻をタイムスタンプとして付与</li>
 *   <li>Scoreドメインモデルを生成</li>
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
 * <h3>クリーンアーキテクチャ:</h3>
 * <ul>
 *   <li>ドメインモデル（Score）を使用</li>
 *   <li>永続化の詳細（ScoreEntity）には依存しない</li>
 *   <li>アダプター層（ScoreRepositoryAdapter）でマッピングを実施</li>
 * </ul>
 *
 * @author AI-DLC Development Team
 * @version 1.0.0
 * @since 2025-11-28
 */
@Component
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
     * @throws NullPointerException gameStateDTOがnullの場合
     */
    @Override
    @Transactional
    public ScoreDTO saveScore(GameStateDTO gameStateDTO) {
        // 0. 入力検証
        Objects.requireNonNull(gameStateDTO, "gameStateDTO must not be null");

        // 1. タイムスタンプを付与（要件7.2）
        LocalDateTime timestamp = LocalDateTime.now();

        // 2. Scoreドメインモデルを生成（要件7.1）
        Score score = Score.create(
                gameStateDTO.score(),
                gameStateDTO.level(),
                gameStateDTO.totalLinesCleared(),
                timestamp
        );

        // 3. リポジトリに保存
        Score savedScore = scoreRepositoryPort.save(score);

        // 4. ScoreDTOに変換して返却
        return new ScoreDTO(
                savedScore.id(),
                savedScore.score(),
                savedScore.level(),
                savedScore.totalLinesCleared(),
                savedScore.timestamp()
        );
    }
}
