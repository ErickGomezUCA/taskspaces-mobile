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
  - **components:** UI Composables, or any other reusable component defined in Figma.
    - If a composable can be reused in any other screen, save it in `general` package. But if that composable is used only in a specific screen, save it in package with the name of the screen (for example, `workspaces`, `projects`, etc...)
  - **layout:** Custom UI composables that describes the structure of a page. Like custom scaffold, custom navigation bar, etc.
  - **navigation:** Serialized object and data classes to navigate between screens (use safe navigation only)
  - **screens:** Main screen of the app to show the corresponding sections of the app
  - **theme:** Colors and theme
- **utils:** Other functions or tools to perform a specific action. Like parsing, formatting, etc.
- **viewmodel:** Contains the view model pattern, to manage the UI-related data.

## Branches standard
1. Follow git flow structure and initialize new branches from the same tool
2. All new branches should only come from `develop` branch, avoid from other branches like `main`.
3. In case of not having git flow set up yet, you can create and publish a branch by just adding the prefix in it (`feature/...`, `hotfix/...`)
4. Features branches should contain the Item ID from Jira (for example: `feature/TS-91`)

## Commits standard
1. Should be written in "active voice" and present tense (for example, `Add workspace screen`). Avoid writing in past tense, future tense and their variants, and avoid including the noun (for example, `I created the workspace screen`)
2. All commits have to be written in English.
3. The commits made in a branch should follow the scope of the issue established from Jira, in case of having 
4. All commits should always follow the context of the issue of the branch, do not include changes that is not part of the branch (for example, do not make changes to tasks if the Jira issue if related with user settings)
5. If there is something that was not completed yet, or there is a change to make but it might not be part of the context of the branch (like the previous given example), add a brief description as a TODO (for example, `// TODO: Finish implementing navigation to Project screen`)
6. Before committing, consider to:
    - Reformat all files before adding them in a new commit
    - Remove unused imports

## Pull requests standard
1. Create a new pull request in Github, and assign the base branch to `develop`. Avoid selecting `main` except when a release has been completely approved.
2. All pull request titles should include the Jira Issue ID and the name of the changes made, or include the name of the issue, (for example: TS-91 Add project screen to main navigation).
3. Any pull request should contain a description and it will have a brief explanation of the changes made.
4. Assign a reviewer when the pull request is ready.
5. You can push new commits to a already opened pull request, Github will update the content of the pull request. But if major changes has been made in the current branch, update the description of the pull request.
6. When all changes has been approved by the assigned reviewer, a merge can be done into the `develop` branch. When this happens, you can safely delete the branch, either locally or remotely.

## Other standards
1. All pieces of code, comments or any other kind of text have to be written in English, avoid using Spanish (except string resources or any other way to change the language of the app)
2. Any new composable, class, or helper function created should contain a documentation comment above of them in the format of KDoc. This with the objective to speed up the process of documenting the codebase. See more about KDoc here: https://kotlinlang.org/docs/kotlin-doc.html
