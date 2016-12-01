library(plotly)

setwd("/Users/Talal/Tesi/TweetParser")
tweetdata <- read.csv("tweets.csv")
retweetdata <- read.csv("retweets.csv")
a <- list(
  title = "Day Time",
  autotick = FALSE,
  ticks = "outside",
  tick0 = 0,
  dtick = 1,
  ticklen = 5,
  tickwidth = 2,
  tickcolor = toRGB("blue")
)

b<- list(
  title="# Tweets"
)

plot_ly(alpha = 0.6)%>%
add_histogram(x = tweetdata$hour ,type = "histogram", name = "tweet") %>%
add_histogram(x = retweetdata$hour ,type = "histogram", name = "retweet") %>%
layout(xaxis = a, yaxis = b,barmode = "overlay",title="2013-05-14")

