
package tang.com.mvvmrecurve.api

import android.arch.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Path
import tang.com.mvvmrecurve.GitHubApiResponse
import tang.com.mvvmrecurve.pojo.Repo

/**
 * Created by tang on 2018/3/1.
 * REST API access points
 */
interface GithubService {

    @GET("users/{login}/repos")
    fun getRepos(@Path("login") login: String): LiveData<GitHubApiResponse<List<Repo>>>

    @GET("repos/{owner}/{name}")
    fun getRepo(@Path("owner") owner: String, @Path("name") name: String): LiveData<GitHubApiResponse<Repo>>

}
