package com.tangpj.repository.entity.file;

import androidx.annotation.StringDef;


@StringDef({FileType.TREE, FileType.BLOB})
public @interface FileType {
    String TREE = "tree";
    String BLOB = "blob";
}
