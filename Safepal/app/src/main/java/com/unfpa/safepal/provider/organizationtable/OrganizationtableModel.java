package com.unfpa.safepal.provider.organizationtable;

import com.unfpa.safepal.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code organizationtable} table.
 */
public interface OrganizationtableModel extends BaseModel {

    /**
     * Get the {@code serverid} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getServerid();

    /**
     * Get the {@code facility_name} value.
     * Can be {@code null}.
     */
    @Nullable
    String getFacilityName();

    /**
     * Get the {@code phone_number} value.
     * Can be {@code null}.
     */
    @Nullable
    String getPhoneNumber();

    /**
     * Get the {@code district} value.
     * Can be {@code null}.
     */
    @Nullable
    String getDistrict();

    /**
     * Get the {@code address} value.
     * Can be {@code null}.
     */
    @Nullable
    String getAddress();

    /**
     * Get the {@code open_hour} value.
     * Can be {@code null}.
     */
    @Nullable
    String getOpenHour();

    /**
     * Get the {@code close_hour} value.
     * Can be {@code null}.
     */
    @Nullable
    String getCloseHour();

    /**
     * Get the {@code link} value.
     * Can be {@code null}.
     */
    @Nullable
    String getLink();

    /**
     * Get the {@code latitude} value.
     * Can be {@code null}.
     */
    @Nullable
    Double getLatitude();

    /**
     * Get the {@code longitude} value.
     * Can be {@code null}.
     */
    @Nullable
    Double getLongitude();

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getCreatedAt();
}
