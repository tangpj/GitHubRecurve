package com.tangpj.github.domain;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

import static com.tangpj.github.domain.RepoFlag.*;

@IntDef({CREATE,
        STAR,
        FORKED,
        WATCH,
        QUERY})
@Retention(RetentionPolicy.SOURCE)
public @interface RepoFlag{
    int CREATE = 0;
    int STAR = 1;
    int FORKED = 2;
    int WATCH = 3;
    int QUERY = 4;
}

