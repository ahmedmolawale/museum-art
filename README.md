
# Museum

Hi 👋🏼👋🏼👋🏼
Thanks for checking out my project. For the rest of this document, I will be explaining the
reasons for the technical decisions I made for this case study, the problems I faced, and what
I learnt from them.


## Table of Contents
- [Introduction](#introduction)
- [Prerequisite](#prerequisite)
- [Architecture](#architecture)
- [Libraries](#libraries)
- [Testing](#testing)
- [Extras](#Extras)
- [Demo](#demo)


## Introduction
An Android application to showcase museum art collection. The application allows users to search for 
various art collections based on their search query. The application consumes data from the [Metropolitan Museum of Art API](https://metmuseum.github.io/)


## Prerequisite
- Minimum Api Level : 21
- compileSdkVersion : 33
- Build System : [Gradle](https://gradle.org/)


## Architecture
The application follows clean architecture because of the benefits it brings to software which includes scalability, maintainability and testability.
It enforces separation of concerns and dependency inversion, where higher and lower level layers all depend on abstractions.
In the project, the layers are separated into different gradle modules namely:

- core:domain
- core:data
- core:common
- core:remote

These modules are all Kotlin modules. The reason being that the low level layers need to be independent of the Android framework.
One of the key points of clean architecture is that low level layers should be platform agnostic. As a result, the domain and data layers can be plugged into a kotlin multiplatform project for example, and it will run just fine because we don't depend on the android framework.
The remote layer is an implementation details that can be provided in any form (Firebase, GraphQl server, REST, GRPC, etc) as long as it conforms to the business rules / contracts defined in the data layer which in turn also conforms to contracts defined in domain layer.

The project has an app module that essentially serves as the presentation layer. Right now, it currently has the `arts` and `artdetails` features that holds the UI code and presents data to the users.

For dependency injection and asynchronous programming, the project uses Dagger Hilt and Coroutines with Flow. Dagger Hilt is a fine abstraction over the vanilla dagger boilerplate, and is easy to setup.
Coroutines and Flow brings kotlin's expressibility and conciseness to asynchronous programming, along with a fine suite of operators that make it a robust solution.


#### core:domain
The domain layer contains the app business logic. It defines contracts for data operations and domain models to be used in the app. All other layers have their own representation of these domain models, and Mapper classes (or adapters) are used to transform the domain models to each layer's model representation.
Writing mappers and models can take a lot of effort and result in boilerplate, but they make the codebase much more maintainable and robust by separating concerns.


#### core:data
The data layer implements the contract for providing data defined in the domain layer,
and it in turn provides a contract that will be used to fetch data from the remote data source.
If the project in the future has requirement for locally cached data, we can also have a separate module called cache. This must also conform to the contract defined in the data layer.
The project has one data source right now - `Remote`. Remote relies on Retrofit library to fetch data from the [Metropolitan Museum of Art API](https://metmuseum.github.io/).


#### Presentation
I used the MVVM pattern for the presentation layer. The Model essentially exposes
the various states the view can be in. The ViewModel handles the UI logic and provides
data via Kotlin flow. The ViewModel talks to the domain layer via the repository defined in the domain layer. The reason for using the `Jetpack Viewmodel` is that it survives configuration changes,
and thus ensures that the view state is persisted across screen rotation.


## Libraries

Libraries used in the application are:


- [Jetpack Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way
   and act as a channel between domain and UI.
- [Retrofit](https://square.github.io/retrofit/) - type safe http client and supports coroutines out of the box.
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines. I used this for asynchronous programming in order
  to obtain data from the network.
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - web server for testing HTTP clients ,verify requests and responses on the foursquare API with the retrofit client.
- [Material Design](https://material.io/develop/android/docs/getting-started/) - build awesome beautiful UIs.🔥🔥
- [JUnit](https://junit.org/junit4/) - This was used for unit testing the various layers.
- [Truth](https://truth.dev/) - Assertions Library, provides readability as far as assertions are concerned.
- [Hilt](https://dagger.dev/hilt/) - Dependency injection plays a central role in the architectural pattern used.
- [Ktlint](https://github.com/pinterest/ktlint) - A Kotlin linter with built-in formatter.
- [Coil](https://github.com/coil-kt/coil) - Image loading for Android backed by Kotlin Coroutines.


## Testing

Testing is done with Junit4 testing framework, and with Google Truth for making assertions. The test uses fake objects for all tests, making it easier to verify interactions between objects and their dependencies, and simulate the behavior of the real objects.
Each layer has its own tests. The remote layer makes use of Mockwebserver to test the api requests and verify that mock Json responses provided in the test resource folder are returned.
The presentation layer is also unit-tested to ensure that the viewmodel renders the correct view states.

The task can do with more UI tests that verify that the view state is rendered as expected. However, the extensive unit test coverage ensures that the app works as expected.


## Extras

The project uses ktlint to enforce proper code style.

## Demo

Find below screenshots of the application

| <img src="images/image_1.png" width=200/> | <img src="images/image_2.png" width=200/> |
|:-----------------------------------------:|:-----------------------------------------:|
