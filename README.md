StarWarsSearchApp
=======

**Developed by:[Prashanth Ramakrishnan](prashanth_r03@yahoo.co.in)**
Powered by [https://swapi.co](url)

**Features**
- Language used is Kotlin
- Fetches the Star Wars character details via the SWAPI API and shows the list in a recycler view.
- The minimum number of characters for search is 3, this can be altered via the project level gradle file, if you want to change it.
- There is no storage in the application, everything is live via the network. The timeout set is 5 seconds for all network requests
- Includes unit tests for presenters and instrumentation tests for the whole application flow.

Refer [here](https://gist.github.com/jemshit/767ab25a9670eb0083bafa65f8d786bb) for proguard rules.

**Note**
- There is no DB in this application, data is shown as is from the API calls!

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