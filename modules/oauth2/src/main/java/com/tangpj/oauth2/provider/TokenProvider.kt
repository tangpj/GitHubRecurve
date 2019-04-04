package com.tangpj.oauth2.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.tangpj.oauth2.domain.GithubToken

/**
 * 组建模式下提供Token数据用于其它组建的登陆
 *
 * @ClassName: TokenProvider
 * @author create by Tang
 * @date 2019-04-02 21:55
 */
class TokenProvider : ContentProvider(){

    companion object {
        const val AUTHORITY = "com.tangpj.oauth2.provider"
        private const val CODE_TOKEN_ITEM: Int = 1
        val URI_TOKEN = Uri.parse("content://$AUTHORITY/${GithubToken.TABLE_NAME}") ?: ""

    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        uriMatcher.addURI(AUTHORITY, GithubToken.TABLE_NAME, CODE_TOKEN_ITEM)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}