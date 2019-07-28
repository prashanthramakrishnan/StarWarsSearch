StarWarsSearchApp
=======

**Developed by:[Prashanth Ramakrishnan](prashanth_r03@yahoo.co.in)**
Powered by [https://swapi.co](url)

**Features**
- Language used is Kotlin
- Fetches the Star Wars character details via the SWAPI API and shows the list in a recycler view.
- The minimum number of characters for search is 3, this can be altered via the project level gradle file, if you want to change it.
- There is no storage in the application, everything is live via the network. The timeout set is 5 seconds for all network requests
- Includes unit tests for presenters and instrumentation tests for the application flow.

Refer [here](https://gist.github.com/jemshit/767ab25a9670eb0083bafa65f8d786bb) for proguard rules.

**Note**
- There is no DB in this application, data is shown as is from the API calls!


**Explanation or so to say "THOUGHT PROCESS"**

- Kotlin is a fairly new language to me/us and I did face quite some hurdles, but nevertheless I did learn a lot more on this below
- Follows or tries to stick to 😉 MVP pattern with usages of Kotlin-Dagger-RxJava2-Retrofit
- I decided to use Dagger for dependency injection, Retrofit, OkHttp for network calls and RxJava2 for reducing boiler plate code
- The app is fairly simple.
    - When the app is launched, you will see a search EditText at the top, I use RxTextView here to emit EditText changes.
    - I have limited the minimum character length for the API hit to be 3, if you ask why? I would say I like the number 3, and reduces my
    overhead on pagination! (😉)
    - When the search is carried out it hits the SWAPI [people search API](https://swapi.co/documentation#search) to fetch the character details and shows it in a RecyclerView
    - Network timeout, errors are generically handled via the presenter (StarWarsCharacterSearchPresenter.kt) for character search
    - When you tap on any of the character details the app goes to a new screen where further more character information is shown as per the requirements
    - Now comes the tricky part, I have created 3 presenters based on the business requirement and each presenter queries for corresponding business logic.
    2 of these presenters (StarWarsSpeciesPresenter.kt & StarWarsHomeWorldPresenter.kt) are fairly simple which just makes a network call and presents the results to the view, however there is some interesting stuff with
    the 3rd presenter(StarWarsFilmDetailsPresenter) uses some cool RxJava2 use case, where an array of urls are hit one by one by using the Observable.zip API. The combined result 
    of this is passed onto the view as a list of film details.
- Unit tests for the presenters/models are added by using Mockito. I did face a challenge as Mockito.any() on Kotlin returns null due to the
explicit safe guard present in Kotlin. It is safe to say that Mockito does not fit well together with Kotlin 🤐. You can find the hack used in StarWarsCharacterSearchPresenterTest.kt from line number 41- 46; the
test case which circumvents this. There is also a nice blog by [Elye](https://medium.com/@elye.project/befriending-kotlin-and-mockito-1c2e7b0ef791) which helped me in figuring this out.
- Instrumentation test is done using Robotium, fairly simple I have tested the happy-flow. You can run it from the IDE or by the connectedAndroidTest
gradle command
- You can find that I have a @VisibleForTesting anootation for constructors in the presenters, I decided to use for ease of development, however in case 
if this has to be removed, an explicit @Inject in the primary constructors would be needed rather than the conventional @Provides method. I decided to use the
former to reduce my efforts, but I am open for a constructive dialogue/debate or to change this implementation!
- And, yes if you get a chance to see my Github code where I did submit a few more exercises like these, you would find that I stick to a similar
way of using dependency injection with Dagger as in the current application. This is because of my usage of it since early 2015 (I am again open for
a discussion/debate 😃).
- As like any code 🐞 do exist which are not visible to me, please do tell me your feedback! 

**Open source libaries used**
- **[Dagger2](https://github.com/google/dagger)**
- **[RxJava2](https://github.com/ReactiveX/RxJava)**
- **[RxAndroid](https://github.com/ReactiveX/RxAndroid)**
- **[Retrofit2](https://github.com/square/retrofit)**
- **[OkHttp3](https://github.com/square/okhttp)**
- **[Gson](https://github.com/google/gson)**
- **[Timber](https://github.com/JakeWharton/timber)**
- **[ButterKnife](https://github.com/JakeWharton/butterknife)**
- **[Robotium](https://github.com/RobotiumTech/robotium)**
- **[MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)**
- **[Commons-io](https://commons.apache.org/proper/commons-io/)**

### License

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.