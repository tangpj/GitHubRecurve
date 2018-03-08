package tang.com.recurve.util

import android.support.annotation.DrawableRes
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.CropSquareTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * Created by tang on 2018/3/8.
 */

@JvmOverloads fun RequestOptions
        .default(@DrawableRes  placeholderRes: Int = 0
                 , @DrawableRes  fallbackRes: Int  = 0
                 , @DrawableRes error: Int = 0): RequestOptions
        = this.placeholder(placeholderRes)
        .fallback(fallbackRes)
        .error(error)

@JvmOverloads fun RequestOptions
        .circle(@DrawableRes  placeholderRes: Int = 0
                , @DrawableRes  fallbackRes: Int = 0
                , @DrawableRes error: Int = 0)

        = this.placeholder(placeholderRes)
        .fallback(fallbackRes)
        .circleCrop()
        .error(error)

@JvmOverloads fun RequestOptions
        .square(@DrawableRes  placeholderRes: Int = 0
                , @DrawableRes  fallbackRes: Int = 0
                , @DrawableRes error: Int = 0)

        = RequestOptions.bitmapTransform(CropSquareTransformation())
        .placeholder(placeholderRes)
        .fallback(fallbackRes)
        .error(error)


@JvmOverloads fun RequestOptions
        .rounded(@DrawableRes placeholderRes: kotlin.Int = 0
                 , @DrawableRes fallbackRes: kotlin.Int = 0
                 , @DrawableRes error: kotlin.Int = 0
                 ,radius: Int = 4, margin: Int = 0)

        = RequestOptions.bitmapTransform(RoundedCornersTransformation(radius,margin))
        .placeholder(placeholderRes)
        .fallback(fallbackRes)
        .error(error)