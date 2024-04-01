package com.hit.playpal.game.domain.usecases;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.hit.playpal.entities.games.Game;
import com.hit.playpal.game.data.repositories.GameRepository;

public class GetGameInfoUseCase {
    private static GameRepository sGameRepository = new GameRepository();
    public void execute(String iGameId, OnSuccessListener<Game> iOnSuccessListener, OnFailureListener iOnFailureListener)
    {
        sGameRepository.getGameInfo(iGameId).
                addOnSuccessListener(iOnSuccessListener).
                addOnFailureListener(iOnFailureListener);
    }
}
