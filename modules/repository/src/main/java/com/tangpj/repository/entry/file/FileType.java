package com.tangpj.repository.entry.file;

import androidx.annotation.StringDef;


@StringDef({FileType.TREE, FileType.BLOB})
public @interface FileType {
    String TREE = "tree";
    String BLOB = "blob";
}
