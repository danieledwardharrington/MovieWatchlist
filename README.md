# Overview

The purpose of this app is to have a simple, clean app with the specific purpose of tracking movies to watch. No more
using the general notes app, which doesn't have any detailed information about the films in question. Since there's no 
publicly available IMDb API, I'm leveraging the OMDb API for this app.

# FAQ

_**Why is the order of the search results a bit wonky?**_

This is the search functionality of the API. My understanding is that the API sorts its results based on exact matches of the query,
 which can result in some oddities in the way the results are ordered.
 
_**Why does the list of search results have less information than on the watchlist/history?**_

Again, this is due to the API. When searching, the movies that are given to the app have very limited information so on the search page, I'm 
only showing the information that's given to me. In the watchlist/history screens, I'm able to get more detailed information for those specific 
films and can thus display more information on the list itself.

_**Why are you using two different APIs for the movies?**_

Both APIs have something different to offer and I wanted both. My understanding is that OMDb pulls a lot of its information directly from
IMDb, which is ideal (TMDb isn't an API to get stuff from IMDb, it's its own entity designed to be what IMDb was prior to being acquired by Amazon).
However, I liked the idea of a trending tab for the app but OMDb doesn't offer this. TMDb has this feature so I leveraged their API for this
(which is why sometimes the poster is different in the trending page than in the details dialog). Ideally, I'd just use IMDb's API since I assume
it's the most robust but it's not publicly available.

# Road Map
For now, the only major feature I'm considering doing is implementing account creation in order to store the user's watchlist/history remotely
so that if a user gets a new phone or uses the app on multiple devices, they can have their same information on each device. I also need to implement search
functionality in the watchlist and history at some point, as well as sorting options.

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
* TMDb API

The idea here was to really leverage a lot of very modern Android technologies/libraries/practices. I'm going with an MVVM design pattern. As far as my chosen film API, OMDb, it seems 
to pull directly from IMDb somehow. I'm using OMDb for the bulk of things and only using TMDb's API to get the 20 trending movies for the week.