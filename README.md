Todo:

backend

- use rtmpsclient instead of kassadin
- figure out if there is memory leak
- create more accounts

frontend

Features
    - Dates:
        - make dates show X min ago, X days ago like lolking
            - fuzzy time, jquery/javascript, if it works, remove the java code I wrote to try and do this...
        - show date when you hover over it, make this date use timezone of client's browser

    - Steamer avatars
    - Streamer popup with info?
    - Watch Video needs to go to game page
    - page numbers should never move, so it's easy to scroll through pages over and over
    - clicking on random skin takes you to that random game
    - focus checkboxes when you press tab in search table
Bugs
    - zooming in and out of browser (ctrl scroll or ctrl +) messes up page
    - reshaping browser causes search inputs like "Streamer Name" to wrap, causing search table to expand vertically
        - (putting white-space:no-wrap fixes problem, but introduces new one where table will wrap)
    - clip table cells should never change sizes, which happens when you keep clicking sort by
