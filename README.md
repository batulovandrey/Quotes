## Quotes

App gets random quotes. It is possible to choose one of the category - famous or movies. Also you can choose number of quotes obtained 1-10. User is supposed to pull to refresh to update list of quotes. Also you can save quotes to favorites. They are going to be saved locally and can be read offline. 

![main](/art/main.gif)

Except the core app you can use android widget. It takes random quote if there is connection and updates in every 15 min. If no connection, it gets a quote from local database named favorites.

![widget](/art/widget.gif)

## Libraries used
| Name            | Description                                                 |  
| ----            | ------------  
| [Retrofit2](http://square.github.io/retrofit/) |	A type-safe REST client for Android which intelligently maps an API into a client interface using annotations.
| [Realm](https://realm.io/) | Realm is a mobile database that runs directly inside phones, tablets or wearables.
| [Dagger 2](https://github.com/google/dagger/) | A fast dependency injector for managing objects.
| [Jackson](https://github.com/FasterXML/jackson-core) | Library to parse data in JSON format.
