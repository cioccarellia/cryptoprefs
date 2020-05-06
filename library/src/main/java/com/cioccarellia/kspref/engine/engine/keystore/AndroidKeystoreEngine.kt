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
package com.cioccarellia.kspref.engine.engine.keystore

import android.content.Context
import com.cioccarellia.kspref.engine.CryptoEngine
import com.cioccarellia.kspref.engine.Engine
import com.cioccarellia.kspref.engine.Transmission
import com.cioccarellia.kspref.engine.secondary.SecondaryEnginePicker
import java.security.KeyStore

internal class AndroidKeystoreEngine(
    private val context: Context,
    private val alias: String,
    private val keyTagSizeInBits: Int
) : Engine(), CryptoEngine {

    private val KEYSTORE_TYPE = "AndroidKeyStore"

    private val keyStore: KeyStore
        get() = KeyStore.getInstance(KEYSTORE_TYPE).also {
            it.load(null)
        }

    private val subEngine
        get() = SecondaryEnginePicker.select(alias, context, keyStore, keyTagSizeInBits)

    override fun derive(incoming: Transmission) = Transmission(
        encrypt(incoming.payload)
    )

    override fun integrate(outgoing: Transmission) = Transmission(
        decrypt(outgoing.payload)
    )

    override fun encrypt(input: ByteArray) = subEngine.encrypt(input)

    override fun decrypt(cipherText: ByteArray) = subEngine.decrypt(cipherText)
}