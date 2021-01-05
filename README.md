# Overview

The purpose of this app is to have a simple, clean app with the specific purpose of tracking movies to watch. No more
using the general notes app, which doesn't have any detailed information about the films in question. Since there's no 
publicly available IMDb API, I'm leveraging the OMDb API for this app.

# FAQ

_**Why is the order of the search results a bit wonky?**_

This is the search functionality of the API. My understanding is that the API sorts its results based on exact matches of the query,
 which can result in some oddities in the way the results are ordered.
 
_**Why does the list of search results have less information than on the watchlist/history?**_

Again, this due to the API. When searching, the movies that are given to the app have very limited information so on the search page, I'm 
only showing the information that's given to me. In the watchlist/history screens, I'm able to get more detailed information for those specific 
films and can thus display more information on the list itself.

# Road Map
For now, the only real feature I'm considering doing is implementing account creation in order to store the user's watchlist/history remotely 
so that if a user gets a new phone or uses the app on multiple devices, they can have their same information on each device.

# For Developers

## Visual Design Notes

The idea here was to have everything be as simple and clean as possible with a very focused function. I'm also trying to keep a lot of the core 
functionality, mostly search, near the bottom of the screen since so many phones have monolithic screen sizes these days.

## Library/Technology Notes

The main libraries I used are as follows:
* Glide
* Gson
* Navigation Component
* OMDb API
* Paging Library V3
* Retrofit
* Room
* RxJava3
* sdp

The idea here was to really leverage a lot of very modern Android technologies/libraries/practices. I'm going with an MVVM design pattern. As far as my chosen film API, OMDb, it seems 
to have the most robust library and if my research is correct, it seems to pull directly from IMDb somehow. A good alternative could have been TMDb, which seems a little more feature rich than 
OMDb. A consideration in the future might be to leverage them both in order to get access to more functionality. OMDb, which had significantly slowed down development, has now been picked back up 
and will hopefully have more of the features that TMDb has.