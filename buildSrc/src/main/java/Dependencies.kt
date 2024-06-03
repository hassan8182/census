/**
 * Project dependencies, makes it easy to include external binaries or
 * other library modules to build.
 */

object Dependencies {

    const val splashScreen =
        "androidx.core:core-splashscreen:${BuildDependenciesVersions.splashApiVersion}"

    const val kotlinCoreKtx = "androidx.core:core-ktx:${BuildDependenciesVersions.coreKtxVersion}"
    const val appCompatVersion =
        "androidx.appcompat:appcompat:${BuildDependenciesVersions.appCompatVersion}"
    const val materialLibVersion =
        "com.google.android.material:material:${BuildDependenciesVersions.materialVersion}"
    const val constraintLayoutVersion =
        "androidx.constraintlayout:constraintlayout:${BuildDependenciesVersions.constraintLayoutVersion}"
    const val vectorVersion =
        "androidx.vectordrawable:vectordrawable:${BuildDependenciesVersions.vectorVersion}"

    /* extensions */
    const val lifeCycleExtVersion =
        "androidx.lifecycle:lifecycle-extensions:${BuildDependenciesVersions.lifeCycleExtVersion}"
    const val liveDataKtxVersion =
        "androidx.lifecycle:lifecycle-livedata-ktx:${BuildDependenciesVersions.liveDataKtxVersion}"
    const val viewModelKtxVersion =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${BuildDependenciesVersions.viewModelKtxVersion}"
    const val navExtVersion =
        "androidx.navigation:navigation-ui-ktx:${BuildDependenciesVersions.navExtVersion}"
    const val navFragmentExtVersion =
        "androidx.navigation:navigation-fragment-ktx:${BuildDependenciesVersions.navExtVersion}"

    /* retrofit */
    const val retrofitVersion =
        "com.squareup.retrofit2:retrofit:${BuildDependenciesVersions.retrofitVersion}"
    const val retrofitConverterVersion =
        "com.squareup.retrofit2:converter-gson:${BuildDependenciesVersions.retrofitVersion}"
    const val retrofitCoroutinesVersion =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${BuildDependenciesVersions.retrofitCoroutinesVersion}"
    const val retrofitGsonConverterVersion =
        "com.squareup.retrofit2:converter-gson:${BuildDependenciesVersions.retrofitGsonConverterVersion}"
      const val retrofitAdapter =
        "com.squareup.retrofit2:adapter-rxjava3:${BuildDependenciesVersions.retrofitAdapter}"

    /* glide */
    const val glideVersion =
        "com.github.bumptech.glide:glide:${BuildDependenciesVersions.glideVersion}"
    const val glideCompilerVersion =
        "com.github.bumptech.glide:compiler:${BuildDependenciesVersions.glideCompilerVersion}"

    /* coroutines */
    const val coroutineCoreVersion =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${BuildDependenciesVersions.coroutinesVersion}"
    const val coroutinesVersion =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${BuildDependenciesVersions.coroutinesVersion}"

    /* hilt DI */
    const val hiltVersion =
        "com.google.dagger:hilt-android:${BuildDependenciesVersions.hiltVersion}"
    const val hiltKaptVersion =
        "com.google.dagger:hilt-compiler:${BuildDependenciesVersions.hiltVersion}"

    // When using Kotlin.
    const val kaptHiltCompiler = "androidx.hilt:hilt-compiler:1.0.0"

    /* parser */
    const val gsonVersion = "com.google.code.gson:gson:${BuildDependenciesVersions.gsonVersion}"

    const val okhttpVersion =
        "com.squareup.okhttp3:okhttp:${BuildDependenciesVersions.okhttpVersion}"
    const val okhttpInterceptorVersion =
        "com.squareup.okhttp3:logging-interceptor:${BuildDependenciesVersions.okhttpVersion}"

    /* Room */
    const val roomCompiler =
        "kapt androidx.room:room-compiler:${BuildDependenciesVersions.roomVersion}"
    const val roomVersion = "androidx.room:room-ktx:${BuildDependenciesVersions.roomVersion}"

    /* WorkManager */
    const val workManagerVersion =
        "androidx.work:work-runtime-ktx:${BuildDependenciesVersions.workVersion}"
    const val hiltWorkVersion =
        "androidx.hilt:hilt-work:${BuildDependenciesVersions.hiltWorkVersion}"

    /* Timber */
    const val timber = "com.jakewharton.timber:timber:${BuildDependenciesVersions.timber}"
    const val jakewhartonRxbinding4 =
        "com.jakewharton.rxbinding4:rxbinding:${BuildDependenciesVersions.jack4}"
    const val jakewhartonRxbinding =
        "com.jakewharton.rxbinding:rxbinding:${BuildDependenciesVersions.jack}"


    /* multiDexVersion */
    const val multiDexVersion = "com.android.support:multidex:${BuildDependenciesVersions.multiDex}"
    const val lottie = "com.airbnb.android:lottie:${BuildDependenciesVersions.lottieVersion}"

    /* multiDexVersion */
//    const val skeleton =
//        "com.github.usmanrana07:Skeleton:${BuildDependenciesVersions.skeletonVersion}"

    const val skeleton =
        "(com.faltenreich:skeletonlayout:${BuildDependenciesVersions.skeletonVersion})"

    /* multiDexVersion */
    const val shimmer =
        "com.facebook.shimmer:shimmer:${BuildDependenciesVersions.shimmerVersion}"
    const val RECYCLER_VIEW =
        "androidx.recyclerview:recyclerview:${BuildDependenciesVersions.recyclerView}"

}