# TMDB Android App Compose
An Android app built using Jetpack Compose that consumes TMDB API to display the trending movies, search movies, and movie details.

## Prerequisites
Create the `local.properties` file in the project's root folder. This file contains your API key and API base endpoint. Example:
```
API_KEY="47aa75b56464da7a186b813a50035xxx"
BASE_URL="https://api.themoviedb.org/3/"
```
## Screenshots
<img src="https://github.com/pduy99/TrendingMovies/blob/main/Screenshot_2023-09-26-14-18-48-62_6131d9d8005071835134608875169905.jpg?raw=true" width="250" /> <img src="https://github.com/pduy99/TrendingMovies/blob/main/Screenshot_2023-09-26-14-19-08-00_6131d9d8005071835134608875169905.jpg?raw=true" width="250" /> <img src="https://github.com/pduy99/TrendingMovies/blob/main/Screenshot_2023-09-26-14-21-38-90_6131d9d8005071835134608875169905.jpg?raw=true" width="250" />

## Tech Stack 🛠
- [Jetpack Compose](https://developer.android.com/jetpack/compose/) - Jetpack Compose is Android’s modern toolkit for building native UI. It simplifies and accelerates UI development on Android. Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs.
- [Kotlin](https://kotlinlang.org/) - Officially supported programming language for Android development.
- [MVVM Architecture](https://developer.android.com/topic/architecture) - A software architecture that removes the tight coupling between components. Most importantly, in this architecture, the children don't have the direct reference to the parent, they only have the reference by observables.
- [Hilt](https://dagger.dev/hilt/) - Hilt provides a standard way to incorporate Dagger dependency injection into an Android application.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more.
- [RoomDB](https://developer.android.com/training/data-storage/room) - For local storage, caching the query data.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
- [Flows](https://developer.android.com/kotlin/flow) - Flows are built on top of coroutines and can provide multiple values. A flow is conceptually a stream of data that can be computed asynchronously.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
- [Hilt](https://dagger.dev/hilt/) - Dependency Injection Framework
- [Material3 Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Retrofit](https://github.com/square/retrofit) - A type-safe HTTP client for Android and Java.
- [OkHttp Logging Interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - Logs HTTP request and response data.

SDK levels supported
--------------------
- Minimum SDK 28
- Target SDK 33
