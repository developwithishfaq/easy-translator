# Easy Translator

Easily integrate translation features into your Android projects with the Easy Translation library. This Kotlin-based library simplifies the translation process, allowing developers to seamlessly add multilingual support to their apps.

**Support All Languages Translation Available On Google Translator**

## Features

- **Simple API**: Just call the library and provide the text you want to translate, along with the source and target languages.
- **Callbacks**: Receive callbacks for successful translations and error handling.
- **Developer-Friendly**: Designed with ease of use in mind, making it accessible to both experienced and beginner developers.
- **Customizable**: Tailor the translation process to your app's specific needs.

## Usage

1. Add the Easy Translation library to your project.
2. Call the translation function, providing the text, source language, and target language.
3. Handle the success and error callbacks as needed.

## Getting Started

To get started with Easy Translation in your Android project, follow these steps:

   > Add the Easy Translation library to your project by including it in your Gradle dependencies.

   ```gradle
   implementation 'com.github.developwithishfaq:easy-translator:latest_version'
   ```
   > Create Instance of Easy Translator
   ```
   val translator = EasyTranslator(mContext)
   ```
   > Call translate function
   - Provide text you want to translate
   - Provide Source Language Model or Short code like "auto"
   - Provide Target Language Model or Short code like "en"
   - Get results through callback same for errors handling
   ```
         translator.translate(
            text = "text", 
            fromLang = LanguagesModel.AUTO_DETECT,
            toLang = LanguagesModel.ENGLISH,
            { result ->

            },
            { error ->

            })
   ```
## Extras

It contains all languages list so you can easy get that list by calling:
```
translator.getLanguagesList()
```

**Happy Coding**
