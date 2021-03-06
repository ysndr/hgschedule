package de.ysndr.android.hgschedule.state.models;

import com.google.gson.annotations.SerializedName;

import org.immutables.value.Value;

import java.io.Serializable;
import java.util.List;

import fj.data.Option;

/**
 * Created by yannik on 1/10/17.
 */

@Value.Immutable
public abstract class School implements Serializable {

    public static School empty() {
        return ImmutableSchool.builder()
                .name("")
                .id("")
                .variant("")
                .build();
    }

    public abstract String name();

    public abstract String variant();

    @SerializedName("schoolId")
    public abstract String id();

    public abstract List<Teacher> teachers();

    public Option<String> summary() {
        if (name().equals("") && variant().equals("") && id().equals("")) return Option.none();
        return Option.some(name() + " - " + variant());
    }


}
