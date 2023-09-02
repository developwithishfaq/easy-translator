package com.easy.translator

import android.content.Context
import android.view.View
import android.webkit.WebView
import com.easy.translator.EasyUtils.connectionAvailable

class EasyTranslator constructor(
    private val mContext: Context
) : WebView(mContext) {

    private var text: String = ""
    private var from: String = "auto"
    private var to: String = "en"
    private var timeOut: Long = 20000

    private var mOnSuccessListener: ((String) -> Unit)? = null
    private var mOnErrorListener: ((String) -> Unit)? = null

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
        this.text = text
        this.from = fromLanguageShortCode
        this.to = toLanguageShortCode
        this.mOnSuccessListener = onSuccess
        this.mOnErrorListener = onError
        this.timeOut = timeout

        coreTranslation()

    }

    fun translate(
        text: String,
        fromLang: LanguagesModel,
        toLang: LanguagesModel,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
        timeout: Long = 20000
    ) {
        this.text = text
        this.from = fromLang.shortCode
        this.to = toLang.shortCode
        this.mOnSuccessListener = onSuccess
        this.mOnErrorListener = onError
        this.timeOut = timeout
        coreTranslation()
    }


    fun translate(
        text: String,
        fromLang: LanguagesModel,
        toLanguageShortCode: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
        timeout: Long = 20000
    ) {
        this.text = text
        this.from = fromLang.shortCode
        this.to = toLanguageShortCode
        this.mOnSuccessListener = onSuccess
        this.mOnErrorListener = onError
        this.timeOut = timeout
        coreTranslation()
    }

    fun translate(
        text: String,
        fromLanguageShortCode: String,
        toLang: LanguagesModel,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
        timeout: Long = 20000
    ) {
        this.text = text
        this.from = fromLanguageShortCode
        this.to = toLang.shortCode
        this.mOnSuccessListener = onSuccess
        this.mOnErrorListener = onError
        this.timeOut = timeout
        coreTranslation()
    }

    private fun coreTranslation() {
        if (!connectionAvailable(mContext)) {
            mOnErrorListener?.invoke("No internet connection")
            return
        }
        if (from.isBlank()) {
            mOnErrorListener?.invoke("Invalid first language code")
            return
        }
        if (to.isBlank()) {
            mOnErrorListener?.invoke("Invalid second language code")
            return
        }
        if (to.equals("auto", true)) {
            mOnErrorListener?.invoke("Second language cannot be auto detect")
            return
        }
        extractor.extractLink(
            this,
            text,
            from,
            to,
            onSuccess = { mOnSuccessListener?.invoke(it) },
            onError = { mOnErrorListener?.invoke(it) },
            timeOut
        )
    }

    fun getLanguagesList(): List<LanguagesModel> {
        return LanguagesModel.values().toList()
    }
}