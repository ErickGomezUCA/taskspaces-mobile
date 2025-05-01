# TaskSpaces: Mobile front-end

## General information
- **Namespace:** com.ucapdm2025.taskspaces
- **Target SDK:** 35
- **Minimum SDK:** 21
- **Target JVM:** 11

## Package structure
com.ucapdm2025.taskspaces ->
- **data**
  - **model:** Include classes and object that could help visualizing the data from API, local database and other sources
  - **repository:** Repository to manage the data of the app. Tells the way of how to get the data
- **ui**
  - **components:** UI Composables, defined in Figma
  - **layout:** Custom UI composables to reuse in the app. Like custom scaffold, custom navigation bar, etc.
  - **navigation:** Serialized object and data classes to navigate between screens (use safe navigation only)
  - **screens:** Main screen of the app to show the corresponding sections of the app
  - **theme:** Colors and theme
- **utils:** Other functions or tools to perform a specific action. Like parsing, formatting, etc.
- **viewmodel:** Contains the view model pattern, to manage the UI-related data.

## Branches standard
1. Follow git flow structure and initialize new branches from the same tool
2. All new branches should only come from `develop` branch, avoid from other branches like `main`.
3. Features branches should contain the Item ID from Jira (for example: `feature/TS-91`)

## Commits standard
1. Should be written in "active voice" and present tense (for example, `Add workspace screen`). Avoid writing in past tense, future tense and their variants, and avoid including the noun (for example, `I created the workspace screen`)
2. All commits have to be written in English.

## Other standards
1. All piece of code, comments or any other kind of text have to be written in English, avoid using Spanish (except string resources or any other way to change the language of the app)
2. Reformat all files before adding them in a new commit
3. Remove unused imports
4. If there is something that was not completed yet, but another member should complete it, add a brief description as a TODO (for example, `// TODO: Finish implementing navigation to Project screen`)