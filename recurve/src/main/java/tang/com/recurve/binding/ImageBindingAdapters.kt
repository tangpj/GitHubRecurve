/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tang.com.recurve.binding

import android.databinding.BindingAdapter
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import tang.com.recurve.util.default
import javax.inject.Inject


/**
 * Created by tang on 2018/3/7.
 * Binding adapters that work with a fragment instance.
 */
class ImageBindingAdapters @Inject @JvmOverloads constructor(private val requestManager: RequestManager
                                                             , @DrawableRes private val placeholderRes: Int = 0
                                                             , @DrawableRes private val fallbackRes: Int = 0
                                                             , @DrawableRes private val error: Int = 0) {

    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String) {
        requestManager.load(url).default(placeholderRes,fallbackRes,error).into(imageView)
    }


}
