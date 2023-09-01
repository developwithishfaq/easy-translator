package com.easy.translator

import android.content.Context
import android.view.View
import android.webkit.WebView

class EasyTranslator constructor(
    mContext: Context
) : WebView(mContext) {
    override fun setVisibility(visibility: Int) {
        super.setVisibility(View.GONE)
    }

    override fun getVisibility(): Int {
        return View.GONE
    }

    private val extractor = TranslationHelper(mContext)


    fun translate(
        text: String,
        fromLanguageShortCode: String,
        toLanguageShortCode: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
        timeout: Long = 20000
    ) {
        extractor.extractLink(
            this,
            text,
            fromLanguageShortCode,
            toLanguageShortCode,
            onSuccess = { onSuccess.invoke(it) },
            onError = { onError.invoke(it) },
        )
    }

    fun translate(
        text: String,
        from: LanguagesModel,
        to: LanguagesModel,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
        timeout: Long = 20000
    ) {
        if (to == LanguagesModel.AUTO_DETECT) {
            onError.invoke("Second language cannot be auto detect")
        } else {
            extractor.extractLink(
                this,
                text,
                from.shortCode,
                to.shortCode,
                onSuccess = { onSuccess.invoke(it) }, onError = { onError.invoke(it) }, timeout
            )
        }
    }

    fun getLanguagesList(): List<LanguagesModel> {
        return LanguagesModel.values().toList()
    }
}