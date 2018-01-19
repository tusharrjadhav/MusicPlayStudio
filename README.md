# MusicPlayStudio


** Application Feature implement: **

- Use of Web API to fetch songs details.
- Visually interactive design to list details.
- Implement paging to display the results properly.
- Implement a live stream songs
- Implement a feature to add songs to their playlist which users like.
- We expect a beautiful, robust, feature-rich application UI and production level application code. Using MVVM design pattern
- Custom design, font, and icons to make app more user ­friendly.
- Maintain Past activity history by user like streaming and download with song and metadata and store everything in local database, e.g Sqlite etc. Provide another tab on home screen to view activity history.
Application is tested on Nexus 6 Android OS 7.1.1

** In Application following library are used: **

- WCViewPagerIndicators: For View pager with page index
- Espresso: For Android UI testing
- Mockito: For Java unit testing
- Android Support Library: For Recycler view, Constraint layout, Appcompat
- Room Persistance Library: For Sqlite database
- Lifecycle component Library: For LiveData API
- Retrofit Library: For network operation
- GSON Library: For parsing Json response
- Glide: For downloading images 
- Java 8: For lambda function

** Design pattern: Model View Presenter (MVVM) design pattern is been followed. **

- Audio.java  - model
- AudioViewModel.java - ViewModel

** Test Driven Development (TDD) & Unit testing: **

- AudioViewModelTest.java

** Instrumental testing: **
-	AudioDaoTest.java

** UI Automation testing using Espresso: **
- PlayListHomeActivity.java
- UI Automation test cover below scenario 
  - Navigating to Favorite list
  - Navigating to History list
