# Jetpack Compose App Template

A pragmatic Jetpack Compose app template to build lean apps with. It contains everything you need to get you up and running build Android apps using Jetpack Compose.

This template is not meant for educational purposes with best practices in mind. Instead it is meant to be a pragmatic template that you can use to build your apps with.

## Overview

### Dependency Injection

There is no dependency injection framework used. Instead you will need to create your dependencies manually. Do not worry it is much simpler and faster than you think.

How to inject a ViewModel:

Create your factory function (see `DependencyInjection.kt`):

```kotlin
fun homeScreenViewModel(): HomeScreenViewModel {
    return HomeScreenViewModel(photosRepository())
}
```

use it in your screen level `@Composable` like this:

```kotlin
val homeViewModel = viewModel { homeScreenViewModel() }
```

### View Models

The template comes with an opinionated subclass of AndroidX' ViewModel called `AbstractViewModel`.

It setups the boilerplate you need to handle states, events and user actions in a strongly typed fashion. See `HomeScreenViewModel.kt`

Here is the signature:
```kotlin
abstract class AbstractViewModel<State : Any, Event : Any, UserAction : Any>(
    startWith: State,
)
```

you can use it like this:

```kotlin
class HomeScreenViewModel(
    private val photosRepository: PhotosRepository,
) : AbstractViewModel<HomeScreenState, Nothing, Unit>(
    startWith = HomeScreenState.Loading
)
```

to bootstrap your `ViewModel` and start emitting states. See the `PhotoDetailsViewModel.kt` for a more complex example. 

### Navigation

The template uses [`Compose Destinations`](https://github.com/raamcosta/compose-destinations/) to navigate across screens.

This specific library is used as it provides a simple and type-safe way to navigate across screens, and saves you so much time from writing boilerplate code.

See the original repo for more information.

### Permissions

Permissions are handled for you automatically. Instead of poluting your app with permissions checks, the template guards the app's content with a permission check:
```kotlin
AppTheme {
    PermissionRequired(
        onDenyClick = { finish() },
        guardedContent = { /* Your app's content here */ }
    )
}
```

See `Permissions.kt` for more info.


## Bring your Jetpack Compose apps to life ultra-fast

With professionally crafted components, screens and tools at [Composables.com](https://www.composables.com).