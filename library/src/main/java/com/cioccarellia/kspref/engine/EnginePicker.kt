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
package com.cioccarellia.kspref.engine

import android.content.Context
import com.cioccarellia.kspref.KsPrefs
import com.cioccarellia.kspref.config.EncryptionType
import com.cioccarellia.kspref.engine.engine.aes.AesCbcEngine
import com.cioccarellia.kspref.engine.engine.aes.AesEcbEngine
import com.cioccarellia.kspref.engine.engine.base64.Base64Engine
import com.cioccarellia.kspref.engine.engine.keystore.AndroidKeystoreEngine
import com.cioccarellia.kspref.engine.engine.plaintext.PlainTextEngine
import com.cioccarellia.kspref.extensions.toSymmetricKey

internal object EnginePicker {
    private val config = KsPrefs.config.encryptionType

    fun select(
        context: Context
    ): Engine = when (config) {
        is EncryptionType.PlainText -> PlainTextEngine()
        is EncryptionType.Base64 -> Base64Engine(config.flags)
        is EncryptionType.AesEcb -> {
            val key = EncryptionKeyChecker.approve(
                config.key.toSymmetricKey(), config.keySize
            )

            AesEcbEngine(
                key,
                keyByteCount = key.byteCount(),
                base64Flags = config.base64Flags
            )
        }
        is EncryptionType.AesCbc -> {
            val key = EncryptionKeyChecker.approve(
                config.key.toSymmetricKey(), config.keySize
            )

            AesCbcEngine(
                key,
                keyByteCount = key.byteCount(),
                base64Flags = config.base64Flags,
                iv = config.iv
            )
        }
        is EncryptionType.KeyStore -> {
            val alias = config.alias

            AndroidKeystoreEngine(
                context,
                alias,
                keyTagSizeInBits = config.keyTagSize.bitCount()
            )
        }
    }
}