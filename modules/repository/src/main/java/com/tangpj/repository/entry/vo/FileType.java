package com.tangpj.repository.entry.vo;

import androidx.annotation.StringDef;


@StringDef({FileType.TREE, FileType.BLOB})
public @interface FileType {
    String TREE = "tree";
    String BLOB = "blob";
}
