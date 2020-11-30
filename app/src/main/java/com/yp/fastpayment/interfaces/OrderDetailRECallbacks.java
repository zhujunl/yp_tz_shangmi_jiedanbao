package com.yp.fastpayment.interfaces;

import androidx.annotation.NonNull;

public interface OrderDetailRECallbacks {
    void onResponse(@NonNull byte[] value);

    void onError(@NonNull Throwable throwable);
}
