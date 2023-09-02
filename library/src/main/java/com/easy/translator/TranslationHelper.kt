package com.easy.translator

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import org.jsoup.Jsoup


open class TranslationHelper  constructor(private var mContext: Context) : WebViewClient() {
    private var mainWebView: WebView? = null
    private var checked = false
    private var text = ""

    private var mOnSuccessListener: ((String) -> Unit)? = null
    private var mOnErrorListener: ((String) -> Unit)? = null

    private val handler = Handler(Looper.getMainLooper())


    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun extractLink(
        mWebView: EasyTranslator,
        text: String,
        from: String,
        to: String,
        onSuccess: ((String) -> Unit)? = null,
        onError: ((String) -> Unit)? = null,
        timeout: Long = 20000
    ) {

        mOnSuccessListener = onSuccess
        mOnErrorListener = onError
        startTimeoutHandler(timeout)
        this.text = text
        mContext.let {
            mainWebView = mWebView
            mainWebView!!.settings.apply {
                domStorageEnabled = true
                javaScriptEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                databaseEnabled = true
                cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                allowContentAccess = true
                allowFileAccess = true
                displayZoomControls = false
                setSupportMultipleWindows(true)
                javaScriptCanOpenWindowsAutomatically = true
                mixedContentMode = 0
            }
            mainWebView!!.addJavascriptInterface(
                JavaScriptInjector(), "HtmlViewer"
            )
            mainWebView!!.webViewClient = this
            val link =
                "https://translate.google.com/?sl=${from.lowercase()}&tl=${to.lowercase()}&text=${
                    getText(text)
                }&op=translate"
            mainWebView!!.loadUrl(link)
        }
    }

    private fun getText(text: String): String {
        return java.net.URLEncoder.encode(text, "UTF-8")
    }

    override fun onLoadResource(view: WebView?, url: String?) {
//        Log.d(TAG, "onLoadResource: $view")
        view?.loadUrl("javascript:window.HtmlViewer.extractedHtml('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
        super.onLoadResource(view, url)
    }

    inner class JavaScriptInjector {
        @JavascriptInterface
        fun extractedHtml(html: String?) {
            html?.let {
//                Log.d(TAG, "extractedHtml: Called")
                val jsoup = Jsoup.parse(html)
                val element = jsoup.getElementsByClass("ryNqvb")
                if (element.text().isNotBlank()) {
//                    Log.d(TAG, "extractedHtml: Found")
                    if (!checked) {
                        checked = true
                        cancelHandler()
                        mOnSuccessListener?.invoke(element.text())
                    }
                }
            }
        }
    }

    private fun startTimeoutHandler(timeout: Long) {
        handler.postDelayed({
            mOnErrorListener?.invoke("Timeout")
            cancelProcess()
        }, timeout)
    }

    private fun cancelHandler() {
        handler.removeCallbacksAndMessages(null)
    }

    fun cancelProcess() {
        try {
            cancelHandler()
            mOnErrorListener = null
            mOnSuccessListener = null
            mainWebView?.stopLoading()
        } catch (_: Exception) {
        }
    }

}