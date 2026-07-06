# The Breed Explorer App

## Overview

An Android application that allows users to browse and explore dog breeds with a responsive master-detail UI.
The app fetches breed data from a remote API and displays a gallery of dog breed.
Also the user can add dog breeds to its favorites (stored only in memory) and user can also filter dog breeds by name for easier search.
For better UX app supports different states such as Loading, Empty, Error.

## Architecture

**MVVM Pattern + Clean architecture** with Dependency Injection:

- **Presentation Layer**: Jetpack Compose for declarative UI
- **ViewModel Layer**: Manages UI state and business logic
- **Data Layer**: Repository pattern with API integration
- **Dependency Injection**: Koin for lightweight service localization

## Technologies & Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Navigation**: Compose Navigation
- **Networking**: Ktor Client with Kotlin Serialization
- **Image Loading**: Coil
- **Dependency Injection**: Koin
- **Minimum SDK**: Android 24 (API Level 24)
- **Target SDK**: Android 37

## Note:

Current https://dog.ceo/dog-api/documentation/ does not work and the API delievers HTTP code 520,
so I made a workaround with mocked data and commented the code for fetching remote data.
Unfortunately I could not test and validate the finished app because of mentioned problem.

Also I connected the GitHub repository with Bitrise CI/CD. To test the app without remote data you can download the
app [here](https://app.bitrise.io/app/ef9910a8-5dcf-403c-84c8-ed6ac02c2380/installable-artifacts/c936d9a05cdb2e4f/public-install-page/69cdd8f7d60781c5a62b5c7f370031ff).
The app from Bitrise does not include latest changes, but it provides the expected functionality except the remote data from non-working API because I ran out of free credits.
Full commit list could be seen [here](https://github.com/martinzagorscak/The-Breed-Explorer-App/commits/main/).
To test the app as a whole - clone the project and build the apk on your machine.
