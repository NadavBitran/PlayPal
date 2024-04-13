package com.hit.playpal.auth.domain.usecases;

import com.hit.playpal.auth.domain.repositories.IAuthRepository;
import com.hit.playpal.auth.domain.utils.enums.AuthServerFailure;
import com.hit.playpal.utils.UseCaseResult;
import com.hit.playpal.entities.settings.Settings;

import java.util.concurrent.CompletableFuture;

public class StoreUserDefaultSettingsDataUseCase {
    private IAuthRepository mRepository;

    public StoreUserDefaultSettingsDataUseCase(IAuthRepository iRepository) {
        mRepository = iRepository;
    }

    public CompletableFuture<UseCaseResult<Void, AuthServerFailure>> execute(String iUid, Settings iSettings) {
        CompletableFuture<UseCaseResult<Void, AuthServerFailure>> future = new CompletableFuture<>();

        mRepository.storeUserDefaultSettingsData(iUid, iSettings).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                future.complete(new UseCaseResult<Void, AuthServerFailure>());
            } else {
                future.complete(new UseCaseResult<Void, AuthServerFailure>(AuthServerFailure.INTERNAL_DB_ERROR));
            }
        });

        return future;
    }
}
