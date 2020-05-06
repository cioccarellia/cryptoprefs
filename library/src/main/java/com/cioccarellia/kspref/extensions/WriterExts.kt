/**
 * Designed and developed by Andrea Cioccarelli (@cioccarellia)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cioccarellia.kspref.extensions

import android.content.SharedPreferences
import com.cioccarellia.kspref.KsPrefs
import com.cioccarellia.kspref.config.model.AutoSavePolicy
import com.cioccarellia.kspref.config.model.CommitStrategy

internal typealias Writer = SharedPreferences.Editor

internal fun Writer.write(
    key: String,
    value: ByteArray
): Writer = putString(key, value.string())

internal fun Writer.delete(
    key: String
): Writer = this.remove(key)

internal fun Writer.finalize(
    commitStrategy: CommitStrategy
): Writer {
    if (KsPrefs.config.autoSave == AutoSavePolicy.AUTO) {
        forceFinalization(commitStrategy)
    }

    return this
}

internal fun Writer.forceFinalization(
    commitStrategy: CommitStrategy
): Writer {
    when (commitStrategy) {
        CommitStrategy.ASYNC_APPLY -> apply()
        CommitStrategy.SYNC_COMMIT -> commit()
        CommitStrategy.NONE -> {
        }
    }

    return this
}