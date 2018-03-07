package tang.com.recurve.util

import android.support.annotation.DrawableRes
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.DrawableTypeRequest

/**
 * Created by tang on 2018/3/8.
 */

fun <ModelType> DrawableTypeRequest<ModelType>
        .default(@DrawableRes  placeholderRes: Int
                 , @DrawableRes  fallbackRes: Int
                 , @DrawableRes error: Int = 0): DrawableRequestBuilder<ModelType> =
        this.placeholder(placeholderRes).fallback(fallbackRes).error(error)

//fun  <ModelType> DrawableTypeRequest<ModelType>