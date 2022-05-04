object Versions {
    val hiltNavigationCompose = "1.0.0-alpha03"
    val activityCompose = "1.4.0"
    val kotlin = "1.5.31"
    val coroutines = "1.5.2"
    val materialDialogs = "0.6.1"

    val core = "1.7.0-rc01"
    val material = "1.5.0-alpha04"
    val appCompat = "1.4.0-alpha02"
    val constraintLayout = "2.0.4"
    val lifecycle = "2.4.0-alpha02"
    val viewModelCompose = "2.4.0"
    val fragment = "1.4.0-alpha03"
    val mockk = "1.12.0"
    val hilt = "2.39.1"
    val testRunner = "1.3.0"
    val hiltJetpack = "1.0.0"
    val testJetpack = "2.1.0"
    val timber = "5.0.1"
    val initializer = "1.0.0-alpha01"
    val compose = "1.1.0-beta01"
    val hiltWork = "1.0.0"
    val hiltJetpackViewModel = "1.0.0-alpha03"
    val accompanistCoil = "0.15.0"
    val accompanistPager = "0.20.0"
    val accompanistSystemUi = "0.22.0-rc"
    val accompanistInsetsUi = "0.22.0-rc"
    val workManager = "2.7.1"
    val room = "2.4.0-alpha03"

    val preferences = "1.1.1"
    val preferencesDataStore = "1.0.0"
    val truth = "1.4.0"
    val turbine = "0.6.1"
    val composeCalendar = "0.5.1"
    val numberPicker = "1.0.3"
    val lottie = "4.2.2"
    val androidJunit = "1.1.3"
    val firebaseBom = "29.0.3"
    val composeDestinations = "1.2.1-beta"
    val robolectric = "4.7.3"
    val shot = "5.14.0"
}


object Deps {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val core = "androidx.core:core-ktx:${Versions.core}"
    val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewModelCompose}"
    val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val material = "com.google.android.material:material:${Versions.material}"
    val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    val hiltJetpackCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltJetpack}"
    val hiltJetpackViewModel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltJetpackViewModel}"
    val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"
    val hiltWork = "androidx.hilt:hilt-work:${Versions.hiltWork}"
    val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    val appInitializer = "androidx.startup:startup-runtime:${Versions.initializer}"
    val preferences = "androidx.preference:preference-ktx:${Versions.preferences}"
    val preferencesDataStore = "androidx.datastore:datastore-preferences:${Versions.preferencesDataStore}"
    val composeActivity = "androidx.activity:activity-compose:${Versions.activityCompose}"
    val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    val composeLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
    val composeRuntime = "androidx.compose.runtime:runtime:${Versions.compose}"
    val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    val composeMaterialIcons =
        "androidx.compose.material:material-icons-extended:${Versions.compose}"
    val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    val accompanistCoil = "com.google.accompanist:accompanist-coil:${Versions.accompanistCoil}"
    val accompanistPager = "com.google.accompanist:accompanist-pager:${Versions.accompanistPager}"
    val accompanistPagerIndicators = "com.google.accompanist:accompanist-pager-indicators:${Versions.accompanistPager}"
    val workManager = "androidx.work:work-runtime-ktx:${Versions.workManager}"
    val workManagerTesting = "androidx.work:work-testing:${Versions.workManager}"
    val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    val roomRuntinme = "androidx.room:room-runtime:${Versions.room}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    val materialDialogsDatetime = "io.github.vanpra.compose-material-dialogs:datetime:${Versions.materialDialogs}"
    val composeCalendar = "io.github.boguszpawlowski.composecalendar:composecalendar:${Versions.composeCalendar}"
    val numberPicker = "com.chargemap.compose:numberpicker:${Versions.numberPicker}"
    val lottie = "com.airbnb.android:lottie-compose:${Versions.lottie}"
    val androidJunit = "androidx.test.ext:junit-ktx:${Versions.androidJunit}"
    val accompanistSystemUi = "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanistSystemUi}"
    val accompanistInsetsUi = "com.google.accompanist:accompanist-insets-ui:${Versions.accompanistInsetsUi}"
    val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics"
    val composeDestinations = "io.github.raamcosta.compose-destinations:animations-core:${Versions.composeDestinations}"
    val composeDestinationsKsp = "io.github.raamcosta.compose-destinations:ksp:${Versions.composeDestinations}"
    val composeUiTest = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    val composeUiManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"

    val junit = "junit:junit:4.13"
    val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    val mockk = "io.mockk:mockk:${Versions.mockk}"
    val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    val truth = "androidx.test.ext:truth:${Versions.truth}"
    val test = "androidx.arch.core:core-testing:${Versions.testJetpack}"
    val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
    val androidTestRunner = "androidx.test:runner:${Versions.testRunner}"
    val androidTestRules = "androidx.test:rules:${Versions.testRunner}"
    val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
}
