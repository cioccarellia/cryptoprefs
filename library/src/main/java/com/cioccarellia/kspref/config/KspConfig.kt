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
package com.cioccarellia.kspref.config

import android.content.Context
import androidx.annotation.IntRange

data class KspConfig(
  @IntRange(from = 0x0000, to = 0x0010)
  var mode: Int = Context.MODE_PRIVATE,
  var autoSave: AutoSavePolicy = AutoSavePolicy.SAVE_ON_COMMAND,
  var transformation: ByteTransformationStrategy = ByteTransformationStrategy.PLAIN_TEXT
)