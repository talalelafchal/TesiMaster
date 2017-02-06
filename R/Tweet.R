library(plotly)

setwd("/Users/Talal/Tesi/TweetParser")
tweetdata <- read.csv("tweets.csv")
retweetdataWithAbsent <-subset(tweetdata,retweetId>=0)
retweetdataWithoutAbsent  <-subset(tweetdata,retweetId>0)
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
add_histogram(x = retweetdataWithAbsent$hour ,type = "histogram", name = "retweet Absent") %>%
add_histogram(x = retweetdataWithoutAbsent$hour ,type = "histogram", name = "retweet") %>%  
layout(xaxis = a, yaxis = b,barmode = "overlay",title="2013-05-14")

c <- list(

  exponentformat = "none"
)
d <- list(
  tick0 = 0
)
#plot_ly(retweetdataWithoutAbsent, x = retweetdataWithoutAbsent$tweetId,type = 'histogram')%>% 
#layout(xaxis=c,yaxis=d)
l = retweetdataWithoutAbsent$retweetId
f = as.character(l)
l = table(l)

e <- as.data.frame(s)
g <- subset(e,Freq>500)
s <- g[order(g$Freq),] 

plot_ly(s, y = s$Freq,x = as.character(s$l) ,type="scatter" )

