
# Naming Conventions

*Note: This document outlines the current naming conventions for the project. New rules may be added in the future.*

## Java Naming Conventions

| Category   | Convention                                                                                     | Example(s)                                                                                                       |
|------------|------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| Classes    | Use `PascalCase` for class names.                                                              | `MyClass`                                                                                                        |
|            | Use nouns or noun phrases to name a class.                                                     | `CarFactory`, `StudentController`, `AddressValidator`                                                            |
|            | Suffix `Activity` classes with `Activity`.                                                     | `com.hit.playpal.MainActivity`, `SettingsActivity`, `AuthActivity`                                                               |
|            | Suffix `Fragment` classes with `Fragment`.                                                     | `LoginFragment`, `SignUpFragment`, `ProfileFragment`                                                             |
|            | Suffix `Adapter` classes with `Adapter`.                                                       | `UserAdapter`, `UserListAdapter`, `UserDetailsAdapter`                                                           |
|            | Suffix `Dialog` classes with `Dialog`.                                                         | `FeedbackDialog`, `ConfirmationDialog`, `UserDetailsDialog`                                                      |
| Variables  | Prefix member variables with `m` followed by `PascalCase` for non-public instance fields.      | `mPackagePrivate`, `mPrivate`, `mProtected`                                                                      |
|            | Prefix static fields with `s` followed by `PascalCase`.                                        | `sSomeStaticField`                                                                                               |
|            | Use lowercase for public instance fields (should be avoided if possible).                      | `publicField`                                                                                                    |
|            | Use `ALL_CAPS_WITH_UNDERSCORES` for constant names (local, static, public, private, etc.).     | `SOME_CONSTANT`, `public static final int SOME_CONSTANT = 0;`                                                    |
|            | Use `camelCase` for regular local variables.                                                   | `someLocalVariable`                                                                                              |
| Methods    | Use `camelCase` for method names.                                                              | `myMethod`                                                                                                       |
|            | Use descriptive verb names that convey the purpose of the method (even if they come out long). | `calculateTotalPriceOfItemsInCart`, `showConfirmationDialog`, `validateEmailAddress`                             |
|            | Use `get` and `set` prefixes for methods that act as getters and setters.                      | `getSomeField`, `setSomeField`                                                                                   |
|            | Use `is` prefix for methods that return a boolean value.                                       | `isUserLoggedIn`, `isEmailValid`                                                                                 |
|            | Prefix all parameter names with `i` followed by `PascalCase`.                                  | `void doSomething(int iSomeParameter)`                                                                           |
| Packages   | Use lowercase for package names.                                                               |                                                                                                                  |
|            | Use a reverse domain name convention for package naming.                                       | `com.example.myapp`, `com.example.myapp.ui`, `com.example.myapp.ui.activities`, `com.example.myapp.ui.fragments` |
| Enums      | Use `PascalCase` for enum types.                                                               | `DayOfWeek`                                                                                                      |
|            | Use `ALL_CAPS_WITH_UNDERSCORES` for enum constants.                                            | ```java                                                                                                          |
|            |                                                                                                | public enum DayOfWeek {                                                                                          |
|            |                                                                                                | SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY                                                   |
|            |                                                                                                | }                                                                                                                |
| Interfaces | Use `PascalCase` prefixed with `I` for interface names.                                        | `IMyInterface`                                                                                                   |
|            | Use nouns or noun phrases to name an interface.                                                | `IUserRepository`, `IUserDetailsView`, `IUserDetailsPresenter`                                                   |
|            | Use adjectives to name an interface if it is an adjective.                                     | `IParcelable`, `ISerializable`, `IValidatable`                                                                   |
|            | Use `camelCase` for method names in interfaces.                                                | `void myMethod()`, `int calculateTotalPrice()`                                                                   |

## XML Naming Conventions

| Category     | Convention                                                                | Example(s)                                                                                                                                                                                  |
|--------------|---------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Layout Files | Use `snake_case` for layout file names.                                   | `layout_1.xml`, `layout_2.xml`, `layout_3.xml`                                                                                                                                              |
|              | Prefix layout file names with the name of the class that uses the layout. | `activity_main.xml` for `com.hit.playpal.MainActivity.java`, `fragment_login.xml` for `LoginFragment.java`, `item_user.xml` for `UserAdapter.java`, `dialog_confirmation.xml` for `ConfirmationDialog.java` |
| IDs          | Use `snake_case` for IDs.                                                 | `my_button`, `submit_button`, `user_profile_image`                                                                                                                                          |
|              | Prefix IDs with the type of the view.                                     | `button_submit`, `imageview_profile`, `textview_username`                                                                                                                                   |
| Strings      | Use `snake_case` for string names.                                        | `app_name`, `login_error_message`, `welcome_message`                                                                                                                                        |
|              | Prefix string names with the type of text.                                | `title_app`, `message_welcome`, `error_login`                                                                                                                                               |
| Colors       | Use `snake_case` for color names.                                         | `color_primary`, `color_accent`, `color_text_primary`                                                                                                                                       |
|              | Prefix color names with `color`.                                          | `color_primary_dark`, `color_text_secondary`, `color_background`                                                                                                                            |
| Drawables    | Use `snake_case` for drawable names.                                      | `ic_launcher`, `bg_button`, `selector_item`                                                                                                                                                 |
|              | Prefix drawable names with the type of drawable.                          | `selector_button`, `shape_oval`, `ic_menu`                                                                                                                                                  |
| Styles       | Use `PascalCase` for style names.                                         | `MyStyle`, `AppTheme`, `Widget.MyButton`                                                                                                                                                    |
|              | Use nouns or noun phrases to name a style.                                | `ButtonStyle`, `TextAppearance.MyApp.Title`, `Widget.MyApp.Button.Colored`                                                                                                                  |