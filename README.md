# Vehicle Dashboard

Vehicle Dashboard is an Android application built using Kotlin and Jetpack Compose.

The app displays a list of electric vehicles and allows users to view detailed information for each vehicle.

## Features

### Vehicle List
- Vehicle name
- Model
- Battery percentage
- Estimated range
- Online / Offline status
- Vehicle image

### Vehicle Details
- Vehicle name and model
- Battery percentage
- Estimated range
- Current speed
- Odometer
- Connectivity status
- Last updated time
- Refresh option

## Tech Stack

- Kotlin
- Jetpack Compose
- Coroutines
- Flow / StateFlow
- ViewModel
- Navigation Compose
- JUnit

## Architecture

The application follows a layered architecture:

### Presentation
- Compose UI
- ViewModels
- UI state
- Navigation

### Domain
- Vehicle model
- Repository interface
- Use cases

### Data
- Mock data source
- Repository implementation

Data flow:

`Data Source -> Repository -> Use Case -> ViewModel -> Compose UI`

## Data Source

The application uses a mock data source to simulate vehicle data.

A small delay is added to simulate a network request.

The repository abstraction makes it possible to replace the mock implementation with a Retrofit REST API later.

## How to Run

1. Clone the repository.
2. Open the project in Android Studio.
3. Allow Gradle sync to complete.
4. Start an Android emulator or connect a physical Android device.
5. Run the `app` configuration.

## Run Unit Tests

On Windows:

.\gradlew.bat test