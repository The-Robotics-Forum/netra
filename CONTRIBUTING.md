# Contribution Guidelines

All contributions are welcome. Please follow the guidelines given below to get started.

- To file an issue or a feature request, use the [GitHub Issue Tracker](https://github.com/The-Robotics-Forum/workshop-android/issues) and describe briefly what you are expecting.
- To raise a Pull Request:
    1. Fork the repository
    2. Create a branch. Preferable name it the same as the issue you want to solve. Eg name `issue-712`.
    3. Commit your changes to the new branch you created in your forked repository.
    4. Raise a Pull Request to the `develop` branch of this repository.

## Before you raise a PR
**Make sure to follow these guidelines**

1. Run `gradlew check` or `./gradlew check` locally before pushing any commits or creating a Pull Request.
    - If `gradlew check` is failing locally, your Pull Request will also fail and won't be able to merge.
2. If you are taking up any issue from the issue tracker, please name your commits in this form. This will automatically link the Issue to your commit.
    - `Fix #{Issue Number}: Whatever change you did`
    - Example commit message: `Fix #9: Added a new function`
3. Give "easy to understand" names to the branches you create in your forked repository
4. While creating a Pull Request, explain in brief what changes you made.
