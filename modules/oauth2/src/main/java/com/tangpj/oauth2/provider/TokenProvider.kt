package com.tangpj.oauth2.provider

import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.tangpj.oauth2.db.GithubDb
import com.tangpj.oauth2.db.GithubTokenDao
import com.tangpj.oauth2.domain.GithubToken
import dagger.android.DaggerContentProvider
import javax.inject.Inject

/**
 * 组建模式下提供Token数据用于其它组建的登陆
 *
 * @ClassName: TokenProvider
 * @author create by Tang
 * @date 2019-04-02 21:55
 */
class TokenProvider : DaggerContentProvider(){

    companion object {
        const val AUTHORITY = "com.tangpj.oauth2.provider.tokenProvider"
        private const val CODE_TOKEN_ITEM: Int = 1
        val URI_TOKEN = Uri.parse("content://$AUTHORITY/${GithubToken.TABLE_NAME}") ?: ""

    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    @Inject
    lateinit var githubTokenDao: GithubTokenDao


    init {
        uriMatcher.addURI(AUTHORITY, GithubToken.TABLE_NAME, CODE_TOKEN_ITEM)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(uri: Uri, projection: Array<String>?,
                       selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor?
        = when(uriMatcher.match(uri)){
            CODE_TOKEN_ITEM ->{
                //只会保存一条token
                githubTokenDao.loadTokenCursor()
            }
            else ->{
                throw IllegalArgumentException("Unknown URI: $uri")
            }
        }


    override fun update(
            uri: Uri,
            values: ContentValues?, selection: String?, selectionArgs: Array<String>?)
            = -1

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int
            = -1

    override fun getType(uri: Uri): String?
            = when(uriMatcher.match(uri)){
        CODE_TOKEN_ITEM -> "vnd.android.cursor.item/$AUTHORITY.${GithubToken.TABLE_NAME}"
        else -> throw IllegalArgumentException("Unknown URI: $uri")
    }






}